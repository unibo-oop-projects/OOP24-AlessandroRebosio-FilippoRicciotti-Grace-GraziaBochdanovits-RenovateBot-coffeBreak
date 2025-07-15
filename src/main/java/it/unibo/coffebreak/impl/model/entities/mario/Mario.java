package it.unibo.coffebreak.impl.model.entities.mario;

import java.util.Objects;
import java.util.Optional;
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
import it.unibo.coffebreak.api.model.physics.Physics;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.common.Vector;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;
import it.unibo.coffebreak.impl.model.entities.mario.lives.GameLivesManager;
import it.unibo.coffebreak.impl.model.entities.mario.score.GameScore;
import it.unibo.coffebreak.impl.model.entities.mario.states.normal.NormalState;
import it.unibo.coffebreak.impl.model.entities.mario.states.withhammer.WithHammerState;
import it.unibo.coffebreak.impl.model.entities.npc.donkeykong.DonkeyKong;
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
 * @see PhysicsEntity
 * @author Grazia Bochdanovits de Kavna
 */
public class Mario extends AbstractEntity implements MainCharacter, PhysicsEntity {

    private static final float MAX_FALLING_SPEED = 150f;

    private final LivesManager livesManager = new GameLivesManager();
    private final Physics physics = new GamePhysics();
    private final Score score = new GameScore();

    private Optional<CharacterState> currentState = Optional.empty();

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
        this.currentState.ifPresent(state -> state.update(this, deltaTime));
    }

    /**
     * Changes Mario's current state, properly handling transitions.
     *
     * @param stateSupplier the state to transition to (cannot be null)
     * @throws NullPointerException if newState is null
     */
    @Override
    public final void changeState(final Supplier<CharacterState> stateSupplier) {
        this.currentState.ifPresent(state -> state.onExit(this));
        this.currentState = Optional.of(Objects.requireNonNull(stateSupplier.get(), "NewState cannot be null"));
        this.currentState.ifPresent(state -> state.onEnter(this));
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
        this.handleHorizontalMovement(false, this.physics.moveLeft());
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
        this.handleHorizontalMovement(true, this.physics.moveRight());
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
        this.handleClimbingMovement(this.physics.moveUp());
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
        this.handleClimbingMovement(this.physics.moveDown());
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
        if (this.canJump()) {
            this.isJumping = true;
            this.updateVelocity(this.physics.jump(), true);
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
            case final DonkeyKong donkey -> this.loseLife();
            default -> {
            }
        }
        this.currentState.ifPresent(state -> state.handleCollision(this, other));
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
        return this.currentState.orElse(new NormalState());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScoreValue() {
        return this.score.getScore();
    }

    /**
     * Makes Mario lose one life.
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
        return !this.livesManager.isAlive();
    }

    /**
     * @return the current number of lives remaining
     */
    @Override
    public int getLives() {
        return this.livesManager.getLives();
    }

    /**
     * {@inheritDoc}
     * Resets the number of lives back to the initial value defined.
     */
    @Override
    public void resetLives() {
        this.livesManager.resetLives();
        this.score.reset();
    }

    /**
     * {@inheritDoc}
     * Resets the behaviors back to the initial value defined.
     */
    @Override
    public void resetBehaviour() {
        this.isClimbing = false;
        this.isJumping = false;
        this.isFacingRight = true;
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
     * <p>
     * Returns true if Mario's current state has destroyed an enemy.
     * Delegates to the current {@link CharacterState} if present, otherwise returns
     * false.
     * </p>
     *
     * @return true if an enemy was destroyed in the current state, false otherwise
     */
    @Override
    public boolean didDesoyedEnemy() {
        return this.currentState.map(CharacterState::didDesoyedEnemy).orElse(false);
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
        this.isClimbing = false;
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

    /**
     * Common method to update velocity based on physics movement.
     * 
     * @param movementVector the movement vector from physics
     * @param preserveX      whether to preserve the X component of current velocity
     */
    private void updateVelocity(final Vector movementVector, final boolean preserveX) {
        final Vector currentVelocity = this.getVelocity();
        final Vector newVelocity = preserveX
                ? new Vector(currentVelocity.x(), movementVector.y())
                : new Vector(movementVector.x(), currentVelocity.y());
        this.setVelocity(newVelocity);
    }

    /**
     * Handles horizontal movement if not climbing.
     * 
     * @param facingRight    the direction Mario should face
     * @param movementVector the movement vector from physics
     */
    private void handleHorizontalMovement(final boolean facingRight, final Vector movementVector) {
        if (!this.isClimbing) {
            this.isFacingRight = facingRight;
            this.updateVelocity(movementVector, false);
        }
    }

    /**
     * Handles vertical climbing movement.
     * 
     * @param movementVector the movement vector from physics
     */
    private void handleClimbingMovement(final Vector movementVector) {
        if (this.currentState.map(CharacterState::canClimb).orElse(false)) {
            this.isClimbing = true;
            this.updateVelocity(movementVector, true);
        }
    }

    /**
     * Checks if Mario can jump.
     * 
     * @return true if Mario can jump
     */
    private boolean canJump() {
        return this.onPlatform && !this.isJumping && this.currentState.map(CharacterState::canJump).orElse(false);
    }
}
