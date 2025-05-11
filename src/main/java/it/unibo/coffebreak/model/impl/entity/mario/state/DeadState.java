package it.unibo.coffebreak.model.impl.entity.mario.state;

import java.util.Objects;

import it.unibo.coffebreak.model.impl.entity.mario.AbstractMarioState;
import it.unibo.coffebreak.model.impl.entity.mario.Mario;
import it.unibo.coffebreak.model.impl.entity.mario.MarioState;
import it.unibo.coffebreak.model.impl.util.Position2D;
import it.unibo.coffebreak.model.impl.util.Vector2D;

/**
 * Represents Mario's state when he has died in the game.
 * <p>
 * In this state:
 * <ul>
 *   <li>All actions are disabled (movement, jumping, climbing, hammer use)</li>
 *   <li>Mario remains stationary at his death position</li>
 *   <li>Transitioning out of this state typically triggers game over logic</li>
 * </ul>
 * This state follows the State design pattern to encapsulate death behavior.
 */
public class DeadState extends AbstractMarioState {

    /**
     * Constructs a new DeadState for the specified Mario instance.
     *
     * @param mario the Mario instance that has died, cannot be null
     * @throws NullPointerException if {@code mario} is null
     */
    public DeadState(final Mario mario) {
        super(mario);
    }

    /**
     * {@inheritDoc}
     * <p>
     * In dead state, Mario cannot move. This implementation always returns
     * Mario's current position without modification.
     *
     * @return Mario's current position unchanged
     * @throws NullPointerException if either {@code mario} or {@code direction} is null
     */
    @Override
    public Position2D move(final Mario mario, final Vector2D direction) {
        Objects.requireNonNull(direction, "Direction cannot be null");
        return mario.getPosition();
    }

    /**
     * {@inheritDoc}
     * <p>
     * In dead state, climbing is not allowed. This implementation
     * intentionally does nothing when climb is attempted.
     *
     * @throws NullPointerException if {@code mario} is null
     */
    @Override
    public void climb(final Mario mario, final int direction) {
        // Intentionally empty - cannot climb while dead
    }

    /**
     * {@inheritDoc}
     * <p>
     * When exiting dead state, this implementation should:
     * <ul>
     *   <li>Trigger game over screen/sequence</li>
     *   <li>Handle score saving if needed</li>
     *   <li>Reset game state as appropriate</li>
     * </ul>
     *
     * @param mario the Mario instance exiting this state
     * @param playerName the name of the player for score tracking purposes
     * @throws NullPointerException if {@code mario} is null or if {@code playerName} is null
     */
    @Override
    public void onStateExit(final Mario mario, final String playerName) {
        Objects.requireNonNull(mario, "Mario instance cannot be null");
        Objects.requireNonNull(playerName, "Player name cannot be null");

        mario.getScoreManager().endGame(playerName);
        mario.resetToInitialState();
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
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MarioState getStateType() {
        return MarioState.DEAD;
    }
}
