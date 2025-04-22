package it.unibo.coffebreak.model.impl.entity.mario;

import java.util.Objects;
import it.unibo.coffebreak.model.api.entity.MarioStateInterface;
import it.unibo.coffebreak.model.api.entity.Movable;
import it.unibo.coffebreak.model.api.entity.PlayableCharacter;
import it.unibo.coffebreak.model.api.entity.item.Collectible;
import it.unibo.coffebreak.model.impl.entity.GameEntity;
import it.unibo.coffebreak.model.impl.entity.LivesManager;
import it.unibo.coffebreak.model.impl.entity.MarioState;
import it.unibo.coffebreak.model.impl.entity.item.ItemType;
import it.unibo.coffebreak.model.impl.score.GameScoreManager;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;

/**
 * Represents the main player character in the game, implementing both
 * {@link PlayableCharacter} and {@link Movable} interfaces. Manages Mario's
 * state, movement, item collection and life system using the State pattern.
 * 
 * <p>
 * Lifecycle management:
 * <ul>
 * <li>Score persists through lives but resets on new game</li>
 * <li>Game state transitions are handled via {@link MarioStateInterface}</li>
 * <li>Position resets occur on life loss or game over</li>
 * </ul>
 * 
 * @see MarioStateInterface
 * @see GameEntity
 * @see NormalState
 * @see WithHammerState
 * @see DeadState
 */
public class Mario extends GameEntity implements PlayableCharacter, Movable {

    private final LivesManager livesManager;
    private final GameScoreManager scoreManager;
    private MarioStateInterface currentState;

    private final Position startPosition;
    private Vector2D velocity;
    private boolean isOnGround;

    /**
     * Constructs a new Mario instance with specified position, dimensions and score
     * manager.
     *
     * @param position the initial position of Mario in the game world (cannot be null)
     * @param dimension the physical dimensions of Mario (cannot be null)
     * @param scoreManager the score manager to handle point accumulation (cannot be null)
     * @param livesManager the lives manager to handle life lost (cannot be null)
     * @throws NullPointerException if any parameter is null
     */
    public Mario(final Position position, final Dimension dimension, 
                    final GameScoreManager scoreManager, final LivesManager livesManager) {
        super(Objects.requireNonNull(position), Objects.requireNonNull(dimension));
        this.startPosition = new Position(position.x(), position.y());
        this.velocity = new Vector2D(0, 0);
        this.livesManager = Objects.requireNonNull(livesManager);
        this.scoreManager = Objects.requireNonNull(scoreManager);
        this.currentState = new NormalState(this);
        this.isOnGround = true;
    }

    /**
     * Handles state transitions with validation.
     * @param newState the target state
     * @throws IllegalStateException for invalid transitions
     */
    private void changeState(final MarioStateInterface newState) {
        Objects.requireNonNull(newState);
        if (currentState.getClass() == newState.getClass()) {
            return;
        }
        if (!currentState.getStateType().canTransitionTo(newState.getStateType())) {
            throw new IllegalStateException("Invalid Transition");
        }
        currentState.onStateExit(this);
        this.currentState = newState;
        newState.onStateEnter(this);
    }

    /**
     * Changes Mario's state using the enum type.
     * @param newStateType the state type to transition to
     */
    public void changeState(final MarioState newStateType) {
        Objects.requireNonNull(newStateType, "State type cannot be null");
        changeState(newStateType.createState(this));
    }

    /**
     * Updates Mario's position and state based on the elapsed time and current physics.
     * <p>
     * Automatically handles landing detection when:
     * <ul>
     *   <li>Mario was airborne (!isOnGround)</li>
     *   <li>Vertical velocity becomes non-negative (falling or stationary)</li>
     * </ul>
     * In this case, reverts from {@link MarioStateFactory} to previous state if applicable.
     * 
     * @param deltaTime the time elapsed since the last update in milliseconds (ms).
     *        Used for frame-rate independent movement calculations.
     */
    @Override
    public void update(final long deltaTime) {
        final Vector2D displacement = new Vector2D(velocity.getX() * deltaTime / 1000.0f,
                                                    velocity.getY() * deltaTime / 1000.0f);
        setPosition(move(getPosition(), displacement));

        if (!isOnGround && getVelocity().getY() >= 0) {
            setOnGround(true);
        }
    }

    /**
     * Collects an item and applies its effects.
     * If the item is a hammer, transitions Mario to {@link WithHammerState}.
     * Updates the game score based on the item's value.
     *
     * @param item the collectible item to process (cannot be null)
     * @throws NullPointerException  if item is null
     * @throws IllegalStateException if Mario is dead
     * @see Collectible
     * @see ItemType
     */
    public void collectItem(final Collectible item) {
        Objects.requireNonNull(item, "Item cannot be null");
        if (!isAlive()) {
            throw new IllegalStateException("Cannot collect items when dead");
        }
        if (!item.isCollected()) {
            item.collect(this);
            scoreManager.earnPoints(item.getValue());
            if (item.getValue() == ItemType.HAMMER.getValue()) {
                changeState(MarioState.WITH_HAMMER);
            }
        }
    }

    /**
     * Moves Mario based on the current state and provided direction.
     *
     * @param currentPosition the current position before movement (cannot be null)
     * @param direction       the movement direction vector (cannot be null)
     * @return the new position after movement
     * @throws NullPointerException if either parameter is null
     */
    @Override
    public Position move(final Position currentPosition, final Vector2D direction) {
        return currentState.move(this, direction);
    }

    /**
     * Makes Mario jump if possible, delegating to the current state.
     * The actual jump behavior depends on the current state implementation.
     */
    @Override
    public void jump() {
        currentState.jump(this);
    }

    /**
     * Attempts to climb in the specified direction.
     * The actual climbing behavior depends on the current state implementation.
     *
     * @param direction the climbing direction (positive for up, negative for down)
     */
    public void climb(final int direction) {
        if (direction != 0) {
            currentState.climb(this, direction);
        }
    }

    /**
     * Attempts to use the hammer if available in current state.
     * The behavior depends on the current state implementation.
     */
    public void useHammer() {
        currentState.useHammer(this);
    }

    /**
     * Handles life loss and transitions to {@link DeadState}
     * if no lives remain.
     * Resets position but maintains score between lives.
     */
    @Override
    public void loseLife() {
        livesManager.loseLife();
        if (livesManager.isGameOver()) {
            handleGameOver();
        } else {
            resetToInitialState();
            changeState(MarioState.NORMAL);
        }
    }

    /**
     * Handles the game over state transition.
     * Called automatically when Mario loses his last life.
     */
    private void handleGameOver() {
        changeState(MarioState.DEAD);
        scoreManager.endGame("Player");
        resetToInitialState();
    }

    /**
     * Resets Mario's physical state to the starting conditions.
     */
    private void resetToInitialState() {
        setPosition(startPosition);
        setVelocity(new Vector2D(0, 0));
    }

    /**
     * Determines if Mario is in a playable state.
     * 
     * @return true if Mario can move/act, false if dead or game over
     */
    public boolean isAlive() {
        return currentState.getStateType() != MarioState.DEAD;
    }

    /**
     * Gets the current number of remaining lives.
     *
     * @return the number of remaining lives
     */
    public int getLives() {
        return livesManager.getLives();
    }

    /**
     * Gets the current state of Mario.
     *
     * @return MarioState
     */
    public MarioStateInterface getCurrentState() {
        return currentState;
    }

    /**
     * Gets the current state type as an enum value.
     * @return the current MarioState enum value
     */
    public MarioState getCurrentStateType() {
        return currentState.getStateType();
    }

    /**
     * Gets a copy of the current velocity vector.
     *
     * @return a new Vector2D instance with current velocity values
     */
    public Vector2D getVelocity() {
        return new Vector2D(velocity.getX(), velocity.getY());
    }

    /**
     * Sets Mario's velocity.
     * Creates a defensive copy of the input vector to prevent external
     * modification.
     *
     * @param velocity the new velocity vector (cannot be null)
     * @throws NullPointerException if velocity is null
     */
    public void setVelocity(final Vector2D velocity) {
        Objects.requireNonNull(velocity, "Velocity cannot be null");
        this.velocity = new Vector2D(velocity.getX(), velocity.getY());
    }

    /**
     * @return true if Mario in on the ground, false otherwise.
     */
    public boolean isOnGround() {
        return isOnGround;
    }

    /**
     * Sets Mario on the ground.
     * @param onGround
     */
    public void setOnGround(final boolean onGround) {
        this.isOnGround = onGround;
    }

    /**
     * Gets the lives manager instance.
     *
     * @return the lives manager
     */
    public LivesManager getLivesManager() {
        return livesManager;
    }

    /**
     * Gets the score manager instance.
     *
     * @return the score manager
     */
    public GameScoreManager getScoreManager() {
        return scoreManager;
    }
}
