package it.unibo.coffebreak.impl.model.entities.mario;

import java.util.Objects;
import java.util.function.Supplier;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.character.lives.LivesManager;
import it.unibo.coffebreak.api.model.entities.character.score.Score;
import it.unibo.coffebreak.api.model.entities.character.states.CharacterState;
import it.unibo.coffebreak.api.model.entities.collectible.Collectible;
import it.unibo.coffebreak.api.model.entities.npc.Princess;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;
import it.unibo.coffebreak.impl.model.entities.mario.lives.GameLivesManager;
import it.unibo.coffebreak.impl.model.entities.mario.score.GameScore;
import it.unibo.coffebreak.impl.model.entities.mario.states.normal.NormalState;
import it.unibo.coffebreak.impl.model.entities.mario.states.withhammer.WithHammerState;

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

    private final LivesManager livesManager = new GameLivesManager();
    private final Score score = new GameScore();

    private CharacterState currentState;

    private boolean onPlatform;
    private boolean isFacingRight;
    private boolean isJumping;
    private boolean isClimbing;

    /**
     * Creates a new Mario instance.
     *
     * @param position  the initial position of Mario
     * @param dimension the 2D dimension of the Mario (cannot be null)
     */
    public Mario(final Position position, final BoundigBox dimension) {
        super(position, dimension);

        this.isFacingRight = true;

        this.changeState(NormalState::new);
    }

    /**
     * Updates the state of the Mario entity for the current frame.
     * <p>
     * Applies gravity to Mario's vertical velocity unless he is on a platform,
     * in which case the vertical velocity is set to zero. Updates Mario's position
     * based on the calculated velocity and resets the platform state.
     * </p>
     *
     * @param deltaTime the time elapsed since the last update, in seconds
     */
    @Override
    public void update(final float deltaTime) {

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
     * {@inheritDoc}
     * <p>
     * Moves Mario to the left if he is not currently climbing.
     * Updates Mario's position and sets his facing direction to left.
     * </p>
     */
    @Override
    public void moveLeft() {
        if (!this.isClimbing) {
            this.isFacingRight = false;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Moves Mario to the right if he is not currently climbing.
     * Updates Mario's position and sets his facing direction to right.
     * </p>
     */
    @Override
    public void moveRight() {
        if (!this.isClimbing) {
            this.isFacingRight = true;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Moves Mario upward if his current state allows climbing.
     * Sets Mario's climbing state to true when moving up.
     * </p>
     */
    @Override
    public void moveUp() {
        if (this.currentState.canClimb()) {
            this.isClimbing = true;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Moves Mario downward if his current state allows climbing.
     * Sets Mario's climbing state to true when moving down.
     * </p>
     */
    @Override
    public void moveDown() {
        if (this.currentState.canClimb()) {
            this.isClimbing = true;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Makes Mario jump if he is currently on a platform. The jump applies
     * an upward velocity using the physics engine. If Mario is not on a
     * platform (e.g., already in the air), the jump action is ignored.
     * </p>
     */
    @Override
    public void jump() {
        if (this.onPlatform) {
            this.isJumping = true;
        }
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
            case final Platform platform -> this.handlePlatformCollision(platform);
            case final Collectible collectible -> collectible.collect(this);
            case final Princess princess -> princess.rescue();
            default -> {
            }
        }
        this.currentState.handleCollision(this, other);
    }

    private void handlePlatformCollision(final Platform platform) {
        switch (platform.getCollisionSide(this)) {
            case TOP -> {
                this.onPlatform = true;
                this.isJumping = false;
            }
            default -> {
                this.isClimbing = false;
            }
        }
        platform.destroy();
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

    /**
     * {@inheritDoc}
     * 
     * @return true if Mario is currently in a climbing state, false otherwise
     */
    @Override
    public boolean isClimbing() {
        return this.isClimbing;
    }
}
