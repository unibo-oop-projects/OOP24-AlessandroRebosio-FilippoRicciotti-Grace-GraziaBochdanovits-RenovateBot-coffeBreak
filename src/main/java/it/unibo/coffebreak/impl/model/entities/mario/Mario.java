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
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.common.Vector;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;
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

    /**
     * Creates a new Mario instance.
     *
     * @param position     the initial position of Mario
     * @param score        the score of Mario
     * @param livesManager the lives manager for Mario
     */
    public Mario(final Position position, final Score score, final LivesManager livesManager) {
        super(position);
        super.setDimension(super.getDimension().mulHeight(2));

        this.livesManager = livesManager;
        this.physics = new GamePhysics();
        this.score = score;

        this.moveDirection = Command.NONE;
        this.isFacingRight = true;

        changeState(NormalState::new);
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
                    vy = physics.moveUp(deltaTime).y();
                }
            }
            case MOVE_DOWN -> {
                if (currentState.canClimb()) {
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
            case final Collectible collectible -> {
                collectible.collect(this);
            }
            case final Princess princess -> {
                princess.rescue();
            }
            case final Platform platform -> {
                platform.destroy();
                handlePlatformCollision(platform);
            }
            case final Tank tank -> {
                this.setVelocity(new Vector(0, 0));
                this.moveDirection = Command.NONE;
            }
            default -> { }
        }

        this.currentState.handleCollision(this, other);
    }

    private void handlePlatformCollision(final Platform platform) {

        //controlla se mario sta arrampicando e in quel caso gli
        //gli è permesso passare attraverso la piattforma
        if (currentState.isClimbing()) {

            final float platformTop = platform.getPosition().y();
            final float targetY = platformTop - getDimension().height();

            if (getPosition().y() + getDimension().height() >= platformTop - 1f) {
                setPosition(new Position(getPosition().x(), targetY));
                this.onPlatform = true;
                currentState.stopClimbing();
            }
            return;
        }

        handleStandardPlatformCollision(platform);
    }
 
    //TODO: funziona ma non mi piace, forse non va nemmeno in questa classe 
    private void handleStandardPlatformCollision(final Platform platform) {
        final float marioBottom = getPosition().y() + getDimension().height();
        final float marioTop = getPosition().y();
        final float marioLeft = getPosition().x();
        final float marioRight = marioLeft + getDimension().width();

        final float platformTop = platform.getPosition().y();
        final float platformBottom = platformTop + platform.getDimension().height();
        final float platformLeft = platform.getPosition().x();
        final float platformRight = platformLeft + platform.getDimension().width();

        final float[] overlaps = {
            marioBottom - platformTop,    // TOP
            platformBottom - marioTop,    // BOTTOM 
            marioRight - platformLeft,    // LEFT
            platformRight - marioLeft,     // RIGHT
        };

        if (overlaps[0] > 0 && overlaps[1] > 0 && overlaps[2] > 0 && overlaps[3] > 0) {
            int minOverlapIndex = 0;
            for (int i = 1; i < overlaps.length; i++) {
                if (overlaps[i] < overlaps[minOverlapIndex]) {
                    minOverlapIndex = i;
                }
            }

            switch (minOverlapIndex) {
                case 0 -> {
                    if (getVelocity().y() >= 0) {
                        this.onPlatform = true;
                        setPosition(new Position(getPosition().x(), platformTop - getDimension().height()));
                        setVelocity(new Vector(getVelocity().x(), 0));
                    }
                }
                case 1 -> {
                    if (getVelocity().y() < 0) {
                        setVelocity(new Vector(getVelocity().x(), 0));
                        setPosition(new Position(getPosition().x(), platformBottom));
                    }
                }
                case 2 -> {
                    if (getVelocity().x() > 0) {
                        setVelocity(new Vector(0, getVelocity().y()));
                        setPosition(new Position(platformLeft - getDimension().width(), getPosition().y()));
                    }
                }
                case 3 -> {
                    if (getVelocity().x() < 0) {
                        setVelocity(new Vector(0, getVelocity().y()));
                        setPosition(new Position(platformRight, getPosition().y()));
                    }
                }
                default -> { }
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
}
