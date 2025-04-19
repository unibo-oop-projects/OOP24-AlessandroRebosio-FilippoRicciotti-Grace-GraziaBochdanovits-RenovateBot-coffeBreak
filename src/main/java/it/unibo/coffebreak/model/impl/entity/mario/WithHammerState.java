package it.unibo.coffebreak.model.impl.entity.mario;

import java.util.Timer;
import java.util.TimerTask;

import it.unibo.coffebreak.model.impl.entity.MarioState;

/**
 * Represents Mario's state when he has acquired a hammer power-up.
 * <p>
 * In this state:
 * <ul>
 *   <li>Mario cannot climb ladders</li>
 *   <li>Can use the hammer to attack and destroy enemies</li>
 *   <li>The state has a limited duration (5 seconds by default)</li>
 *   <li>Automatically transitions back to normal state after duration expires</li>
 * </ul>
 * This state follows the State design pattern to encapsulate hammer-specific behavior.
 */
public class WithHammerState extends AbstractMarioState {

    /** 
     * The duration in milliseconds that Mario keeps the hammer (5 seconds).
     */
    private static final long HAMMER_DURATION = 5000;

    /** 
     * Timer instance used to track and manage the duration of the hammer state.
     * The timer schedules an automatic state transition when the duration expires.
     */
    private final Timer timer;

    /**
     * Creates a new WithHammerState for the specified Mario instance.
     * <p>
     * Initializes the state and starts a timer that will automatically
     * transition Mario back to normal state after {@link #HAMMER_DURATION}.
     *
     * @param mario the Mario instance acquiring the hammer, cannot be null
     * @throws NullPointerException if {@code mario} is null
     */
    public WithHammerState(final Mario mario) {
        super(mario);
        this.timer = new Timer("HammerStateTimer", true); // Daemon thread
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mario.changeState(MarioState.NORMAL);
            }
        }, HAMMER_DURATION);
    }

    /**
     * {@inheritDoc}
     * <p>
     * In hammer state, Mario cannot climb ladders. This implementation
     * intentionally does nothing when climb is attempted.
     *
     * @param mario the Mario instance attempting to climb
     * @param direction the intended direction of climbing
     */
    @Override
    public void climb(final Mario mario, final int direction) {
        // Intentionally empty - cannot climb while holding hammer
    }

    /**
     * {@inheritDoc}
     * <p>
     * Implements hammer attack functionality. When called, this should:
     * <ul>
     *   <li>Destroy nearby enemies</li>
     *   <li>Play hammer attack animation/sound</li>
     *   <li>Apply any other hammer-specific effects</li>
     * </ul>
     *
     * @param mario the Mario instance using the hammer
     */
    @Override
    public void useHammer(final Mario mario) {
        // to-do: Implement enemy destruction logic
    }

    /**
     * {@inheritDoc}
     * <p>
     * Cleans up resources when exiting this state:
     * <ul>
     *   <li>Cancels the automatic state transition timer</li>
     * </ul>
     *
     * @param mario the Mario instance exiting this state
     */
    @Override
    public void onStateExit(final Mario mario) {
        timer.cancel();
        timer.purge();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canClimb() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canUseHammer() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarioState getStateType() {
        return MarioState.WITH_HAMMER;
    }
}
