package it.unibo.coffebreak.impl.model.entities.mario;

import java.util.Objects;
import java.util.function.Supplier;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.PhysicsEntity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.character.lives.LivesManager;
import it.unibo.coffebreak.api.model.entities.character.score.Score;
import it.unibo.coffebreak.api.model.entities.character.states.CharacterState;
import it.unibo.coffebreak.api.model.entities.collectible.Collectible;
import it.unibo.coffebreak.api.model.entities.npc.Princess;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.common.Vector;
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
public class Mario extends AbstractEntity implements MainCharacter, PhysicsEntity {

    private static final float MAX_FALLING_SPEED = 150f;
    private static final float SPEED = 20f;
    private static final float JUMP_FORCE = 40f;

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
     * This method now delegates physics and collision handling to the unified
     * PhysicsEngine, ensuring consistent behavior across all entities.
     * Only manages Mario-specific state updates.
     * </p>
     *
     * @param deltaTime the time elapsed since the last update, in seconds
     */
    @Override
    public void update(final float deltaTime) {
        this.currentState.update(this, deltaTime);
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
     * Applies leftward movement physics and sets his facing direction to left.
     * </p>
     */
    @Override
    public void moveLeft() {
        if (!this.isClimbing) {
            this.isFacingRight = false;
            final Vector currentVelocity = this.getVelocity();
            final Vector leftMovement = new Vector(-SPEED, 0f);
            this.setVelocity(new Vector(leftMovement.x(), currentVelocity.y()));
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Moves Mario to the right if he is not currently climbing.
     * Applies rightward movement physics and sets his facing direction to right.
     * </p>
     */
    @Override
    public void moveRight() {
        if (!this.isClimbing) {
            this.isFacingRight = true;
            final Vector currentVelocity = this.getVelocity();
            final Vector rightMovement = new Vector(SPEED, 0f);
            this.setVelocity(new Vector(rightMovement.x(), currentVelocity.y()));
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Moves Mario upward if his current state allows climbing.
     * Applies upward climbing physics and sets Mario's climbing state to true.
     * </p>
     */
    @Override
    public void moveUp() {
        if (this.currentState.canClimb()) {
            this.isClimbing = true;
            final Vector currentVelocity = this.getVelocity();
            final Vector upMovement = new Vector(0f, -SPEED);
            this.setVelocity(new Vector(currentVelocity.x(), upMovement.y()));
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Moves Mario downward if his current state allows climbing.
     * Applies downward climbing physics and sets Mario's climbing state to true.
     * </p>
     */
    @Override
    public void moveDown() {
        if (this.currentState.canClimb()) {
            this.isClimbing = true;
            final Vector currentVelocity = this.getVelocity();
            final Vector downMovement = new Vector(0f, SPEED);
            this.setVelocity(new Vector(currentVelocity.x(), downMovement.y()));
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
        if (this.onPlatform && !this.isJumping) {
            this.isJumping = true;
            final Vector currentVelocity = this.getVelocity();
            final Vector jumpVector = new Vector(0f, -JUMP_FORCE);
            this.setVelocity(new Vector(currentVelocity.x(), jumpVector.y()));
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
            case final Platform platform -> {
                this.onPlatformLand();
                platform.destroy();
            }
            case final Collectible collectible -> collectible.collect(this);
            case final Princess princess -> princess.rescue();
            default -> {
            }
        }
        this.currentState.handleCollision(this, other);
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

    /**
     * Called when Mario lands on a platform.
     * Sets platform state and resets jumping state.
     */
    @Override
    public void onPlatformLand() {
        this.onPlatform = true;
        this.isJumping = false;
    }

    /**
     * Called when Mario leaves a platform.
     * Resets platform state.
     */
    @Override
    public void onPlatformLeave() {
        this.onPlatform = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAffectedByGravity() {
        return !this.isClimbing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canStandOnPlatforms() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getMaxFallingSpeed() {
        return MAX_FALLING_SPEED; 
    }
}
