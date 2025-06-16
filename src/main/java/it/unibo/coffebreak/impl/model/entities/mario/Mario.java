package it.unibo.coffebreak.impl.model.entities.mario;

import java.util.Objects;
import java.util.function.Supplier;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.Movable;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.character.lives.LivesManager;
import it.unibo.coffebreak.api.model.entities.character.score.Score;
import it.unibo.coffebreak.api.model.entities.character.states.CharacterState;
import it.unibo.coffebreak.api.model.entities.collectible.Collectible;
import it.unibo.coffebreak.api.model.entities.npc.Princess;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.api.model.physics.Physics;
import it.unibo.coffebreak.impl.common.Dimension;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.common.Vector;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;
import it.unibo.coffebreak.impl.model.entities.mario.lives.GameLivesManager;
import it.unibo.coffebreak.impl.model.entities.mario.score.GameScore;
import it.unibo.coffebreak.impl.model.entities.mario.states.normal.NormalState;
import it.unibo.coffebreak.impl.model.entities.mario.states.withhammer.WithHammerState;
import it.unibo.coffebreak.impl.model.physics.GamePhysics;

/**
 * Represents the main player character (Mario) in the game.
 * This class implements the Character interface and extends GameEntity,
 * managing Mario's state, physics, and interactions with the game world.
 *
 * <p>
 * Key features:
 * <ul>
 * <li>State management using {@link CharacterState} pattern</li>
 * <li>Physics-controlled movement and collisions</li>
 * <li>Item collection and power-up handling</li>
 * <li>Life and score management</li>
 * </ul>
 *
 * <p>
 * States supported:
 * <ul>
 * <li>{@link NormalState} - Default ground movement</li>
 * <li>{@link WithHammerState} - Hammer power-up mode</li>
 * </ul>
 * 
 * @see AbstractEntity
 * @see MainCharacter
 * @author Grazia Bochdanovits de Kavna
 */
public class Mario extends AbstractEntity implements MainCharacter, Movable {

    private final LivesManager livesManager;
    private final Physics physics;
    private final Score score;

    private CharacterState currentState;

    private Command moveDirection;
    private boolean onPlatform;
    private boolean isFacingRight;
    private boolean isJumping;

    /**
     * Creates a new Mario instance.
     *
     * @param position  the initial position of Mario
     * @param dimension the 2D dimension of the Mario (cannot be null)
     */
    public Mario(final Position position, final Dimension dimension) {
        super(position, dimension);

        this.livesManager = new GameLivesManager();
        this.physics = new GamePhysics();
        this.score = new GameScore();

        this.moveDirection = Command.NONE;
        this.isFacingRight = true;

        this.changeState(NormalState::new);
    }

    /**
     * Moves Mario according to his current command, physic and state.
     * This method should be called every frame to update Mario's position.
     * 
     * @param deltaTime the time elapsed since the last frame (in seconds)
     */
    @Override
    public void update(final float deltaTime) {
        float vx = 0f;
        float vy = physics.gravity(deltaTime).y();

        switch (moveDirection) {
            case MOVE_RIGHT -> {
                vx = physics.moveRight(deltaTime).x();
                this.isFacingRight = true;
            }
            case MOVE_LEFT -> {
                vx = physics.moveLeft(deltaTime).x();
                this.isFacingRight = false;
            }
            default -> vx = 0f;
        }

        switch (moveDirection) {
            case MOVE_UP -> {
                if (currentState.canClimb()) {
                    startClimbing();
                    vy = physics.moveUp(deltaTime).y();
                }
            }
            case MOVE_DOWN -> {
                if (currentState.canClimb()) {
                    startClimbing();
                    vy = physics.moveDown(deltaTime).y();
                }
                if (!onPlatform) {
                    vy = physics.moveDown(deltaTime).y();
                }
            }
            default -> {
                if (this.onPlatform) {
                    vy = 0f;
                }
            }
        }

        final Vector velocity = new Vector(vx, vy);
        super.setPosition(super.getPosition().sum(velocity));
        super.setVelocity(velocity);

        this.currentState.update(this, deltaTime);

        this.onPlatform = false;
    }

    /**
     * Changes Mario's current state, properly handling transitions.
     *
     * @param stateSupplier the state to transition to (cannot be null)
     * @throws NullPointerException if newState is null
     */
    @Override
    public final void changeState(final Supplier<CharacterState> stateSupplier) {
        if (this.currentState != null) {
            currentState.onExit(this);
        }
        this.currentState = Objects.requireNonNull(stateSupplier.get(), "NewState cannot be null");
        currentState.onEnter(this);
    }

    /**
     * Handles collision with another entity by delegating to current state.
     *
     * @param other the entity colliding with Mario
     */
    @Override
    public void onCollision(final Entity other) {
        Objects.requireNonNull(other, "Colliding entity cannot be null");
        switch (other) {
            case final Collectible collectible -> collectible.collect(this);
            case final Princess princess -> princess.rescue();
            case final Platform platform -> {
                platform.destroy();
                this.isJumping = false;
                if (!currentState.isClimbing()) {
                    handleStandardPlatformCollision(platform);
                }
            }
            case final Tank tank -> {
                this.setVelocity(new Vector(0, 0));
                this.moveDirection = Command.NONE;
            }
            default -> {
            }
        }

        this.currentState.handleCollision(this, other);
    }

    private void handleStandardPlatformCollision(final Platform platform) {
        enum CollisionDirection { TOP, BOTTOM, LEFT, RIGHT }

        final float marioBottom = getPosition().y() + getDimension().height();
        final float marioTop = getPosition().y();
        final float marioLeft = getPosition().x();
        final float marioRight = marioLeft + getDimension().width();

        final float platformTop = platform.getPosition().y();
        final float platformBottom = platformTop + platform.getDimension().height();
        final float platformLeft = platform.getPosition().x();
        final float platformRight = platformLeft + platform.getDimension().width();

        final float topOverlap = marioBottom - platformTop;
        final float bottomOverlap = platformBottom - marioTop;
        final float leftOverlap = marioRight - platformLeft;
        final float rightOverlap = platformRight - marioLeft;

        if (topOverlap > 0 && bottomOverlap > 0 && leftOverlap > 0 && rightOverlap > 0) {
            CollisionDirection direction = CollisionDirection.TOP;
            float minOverlap = topOverlap;

            if (bottomOverlap < minOverlap) {
                minOverlap = bottomOverlap;
                direction = CollisionDirection.BOTTOM;
            }
            if (leftOverlap < minOverlap) {
                minOverlap = leftOverlap;
                direction = CollisionDirection.LEFT;
            }
            if (rightOverlap < minOverlap) {
                direction = CollisionDirection.RIGHT;
            }

            switch (direction) {
                case TOP -> {
                    if (getVelocity().y() >= 0) {
                        this.onPlatform = true;
                        setVelocity(new Vector(getVelocity().x(), 0));
                        setPosition(new Position(getPosition().x(), platformTop - getDimension().height()));
                    }
                }
                case BOTTOM -> {
                    if (getVelocity().y() < 0) {
                        this.onPlatform = false;
                        setVelocity(new Vector(getVelocity().x(), 0));
                        setPosition(new Position(getPosition().x(), platformBottom));
                    }
                }
                case LEFT -> {
                    if (getVelocity().x() > 0) {
                        this.onPlatform = false;
                        setVelocity(new Vector(0, getVelocity().y()));
                        setPosition(new Position(platformLeft - getDimension().width(), getPosition().y()));
                    }
                }
                case RIGHT -> {
                    if (getVelocity().x() < 0) {
                        this.onPlatform = false;
                        setVelocity(new Vector(0, getVelocity().y()));
                        setPosition(new Position(platformRight, getPosition().y()));
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDirection(final Command command) {
        this.moveDirection = command != null ? command : Command.NONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void earnPoints(final int amount) {
        this.score.increase(amount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startClimbing() {
        this.currentState.startClimb();
        this.onPlatform = false;
        this.isJumping = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopClimbing() {
        this.currentState.stopClimb();
        this.onPlatform = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterState getCurrentState() {
        return this.currentState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScoreValue() {
        return this.score.getScore();
    }

    /**
     * Makes Mario lose one life and handles death/respawn.
     */
    @Override
    public void loseLife() {
        this.livesManager.loseLife();
    }

    /**
     * @return true if Mario has zero remaining lives
     */
    @Override
    public boolean isGameOver() {
        return !livesManager.isAlive();
    }

    /**
     * @return the current number of lives remaining
     */
    @Override
    public int getLives() {
        return livesManager.getLives();
    }

    /**
     * @return true if Mario is facing right, false if facing left
     */
    @Override
    public boolean isFacingRight() {
        return this.isFacingRight;
    }

    /**
     * @return true if Mario is jumping
     */
    @Override
    public boolean isJumping() {
        return this.isJumping;
    }
}
