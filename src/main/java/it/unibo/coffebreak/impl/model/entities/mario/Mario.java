package it.unibo.coffebreak.impl.model.entities.mario;

import java.util.Objects;
import java.util.function.Supplier;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.character.lives.LivesManager;
import it.unibo.coffebreak.api.model.entities.character.score.Score;
import it.unibo.coffebreak.api.model.entities.character.states.CharacterState;
import it.unibo.coffebreak.api.model.entities.collectible.Collectible;
import it.unibo.coffebreak.api.model.entities.npc.Princess;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.api.model.physics.Physics;
import it.unibo.coffebreak.impl.common.BoundigBox;
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
public class Mario extends AbstractEntity implements MainCharacter {

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
    public Mario(final Position position, final BoundigBox dimension) {
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
        this.isJumping = false;
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

        if (currentState.isClimbing() && !currentState.canClimb()) {
            currentState.stopClimb();
        }
        switch (moveDirection) {
            case MOVE_UP, MOVE_DOWN -> {
                if (currentState.canClimb() && !currentState.isClimbing()) {
                    currentState.startClimb();
                }

                vy = (moveDirection == Command.MOVE_UP)
                        ? physics.moveUp(deltaTime).y()
                        : physics.moveDown(deltaTime).y();

                if (currentState.isClimbing()) {
                    this.onPlatform = false;
                } else if (moveDirection == Command.MOVE_DOWN && !onPlatform) {
                    vy = physics.moveDown(deltaTime).y();
                }
            }

            case JUMP -> {
                vy = physics.jump(deltaTime).y();
                if (onPlatform && !currentState.isClimbing()) {
                    vx = isFacingRight
                            ? physics.moveRight(deltaTime).x()
                            : physics.moveLeft(deltaTime).x();
                    this.onPlatform = false;
                    this.isJumping = true;
                }
            }

            default -> {
                if (onPlatform) {
                    vy = 0f;
                }
            }
        }

        final Vector velocity = new Vector(vx, vy);
        super.setPosition(super.getPosition().sum(velocity));
        super.setVelocity(velocity);

        this.currentState.update(this, deltaTime);

        if (!currentState.canClimb() || this.onPlatform && currentState.isClimbing()) {
            currentState.stopClimb();
        }
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
                switch (platform.getCollisionSide(this)) {
                    case TOP -> onPlatform = true;
                    case LEFT, RIGHT -> { } //TODO: slope
                    default -> { }
                }
                platform.destroy();
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
     * {@inheritDoc}
     * Resets the number of lives back to the initial value defined.
     */
    @Override
    public void resetLives() {
        this.livesManager.resetLives();
    }

    /**
     * @return true if Mario is facing right, false if facing left
     */
    @Override
    public boolean isFacingRight() {
        return this.isFacingRight;
    }

    /**
     * @return true if Mario is jumping, false otherwise
     */
    @Override
    public boolean isJumping() {
        return this.isJumping;
    }
}
