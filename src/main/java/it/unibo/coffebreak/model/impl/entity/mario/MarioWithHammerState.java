package it.unibo.coffebreak.model.impl.entity.mario;

import it.unibo.coffebreak.model.impl.entity.MarioState;

/**
 * Represents the state of Mario when he has acquired and is using a hammer.
 * In this state, Mario can attack with the hammer but cannot climb ladders.
 * The hammer state has a limited duration after which Mario returns to normal state.
 */
public class MarioWithHammerState extends AbstractMarioState {

    /** 
     * The duration in milliseconds that Mario keeps the hammer (5 seconds).
     */
    private static final long HAMMER_DURATION = 5000;

    /** 
     * The system time in milliseconds when this hammer state will expire.
     */
    private final long expirationTime;

    /**
     * Creates a new WithHammerState for testing purposes with a specific expiration time.
     *
     * @param mario the Mario instance acquiring the hammer, cannot be null
     * @param expirationTime the custom expiration time in milliseconds
     * @throws NullPointerException if {@code mario} is null
     */
    public MarioWithHammerState(final Mario mario, final long expirationTime) {
        super(mario);
        this.expirationTime = expirationTime;
    }

    /**
     * Creates a new WithHammerState for the specified Mario instance.
     * Initializes the state and sets the expiration time for when Mario
     * should return to normal state.
     *
     * @param mario the Mario instance acquiring the hammer, cannot be null
     * @throws NullPointerException if {@code mario} is null
     */
    public MarioWithHammerState(final Mario mario) {
        this(mario, System.currentTimeMillis() + HAMMER_DURATION);
    }

    /**
     * Checks if the hammer state has expired.
     *
     * @return true if the current time is past the expiration time, false otherwise
     */
    public boolean isExpired() {
        return System.currentTimeMillis() >= expirationTime;
    }

    /**
     * Updates the state of Mario, checking if the hammer duration has expired.
     * If the duration has expired, changes Mario's state back to normal.
     *
     * @param mario the Mario instance to update
     * @param deltaTime the time passed since the last update in milliseconds
     */
    @Override
    public void update(final Mario mario, final long deltaTime) {
        if (isExpired()) {
            mario.changeState(MarioState.NORMAL);
        }
    }

    /**
     * Attempts to make Mario climb in the specified direction.
     * In hammer state, Mario cannot climb ladders, so this method does nothing.
     *
     * @param mario the Mario instance attempting to climb
     * @param direction the intended direction of climbing (positive for up, negative for down)
     */
    @Override
    public void climb(final Mario mario, final int direction) {
        // Intentionally empty - cannot climb while holding hammer
    }

    /**
     * Performs a hammer attack, destroying nearby enemies and playing appropriate effects.
     * This method should be implemented to:
     * <ul>
     *   <li>Destroy nearby enemies within attack range</li>
     *   <li>Play hammer attack animation and sound effects</li>
     *   <li>Apply any other hammer-specific attack effects</li>
     * </ul>
     *
     * @param mario the Mario instance using the hammer
     */
    @Override
    public void useHammer(final Mario mario) {
        // to-do: Implement enemy destruction logic
    }

    /**
     * Cleans up resources when exiting this state.
     * Currently this method does nothing but can be overridden for cleanup logic.
     *
     * @param mario the Mario instance exiting this state
     * @param playerName the name of the player for score tracking purposes
     */
    @Override
    public void onStateExit(final Mario mario, final String playerName) {
        // Optional cleanup can be implemented here
    }

    /**
     * Checks if Mario can climb ladders in this state.
     *
     * @return false, as Mario cannot climb while holding a hammer
     */
    @Override
    public boolean canClimb() {
        return false;
    }

    /**
     * Checks if Mario can use the hammer in this state.
     *
     * @return true, as this is the state where Mario has and can use the hammer
     */
    @Override
    public boolean canUseHammer() {
        return true;
    }

    /**
     * Gets the type of this state.
     *
     * @return the MarioState.WITH_HAMMER enum value
     */
    @Override
    public MarioState getStateType() {
        return MarioState.WITH_HAMMER;
    }
}
