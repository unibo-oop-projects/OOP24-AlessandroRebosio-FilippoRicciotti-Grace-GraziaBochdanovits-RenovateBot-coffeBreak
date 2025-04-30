package it.unibo.coffebreak.model.impl.entity.mario;

import it.unibo.coffebreak.model.impl.entity.mario.state.DeadState;
import it.unibo.coffebreak.model.impl.entity.mario.state.NormalState;
import it.unibo.coffebreak.model.impl.entity.mario.state.WithHammerState;

/**
 * Enumeration of all possible Mario states, implementing the State design pattern.
 * <p>
 * Acts as both a type-safe state identifier and a factory for concrete state implementations.
 * Each enum constant corresponds to a specific game state with distinct behaviors.
 *
 * <p><strong>State Transitions:</strong>
 * <ul>
 *   <li><b>NORMAL</b>: Base state with standard movement</li>
 *   <li><b>WITH_HAMMER</b>: Temporary power-up state (auto-reverts after timeout)</li>
 *   <li><b>DEAD</b>: Terminal state requiring game reset</li>
 * </ul>
 * 
 * @see Mario
 * @see AbstractMarioState
 */
public enum MarioState {

   /**
     * Mario's default state without any power-ups.
     */
    NORMAL {
        @Override
        public AbstractMarioState createState(final Mario mario) {
            return new NormalState(mario);
        }

        @Override
        public boolean canTransitionTo(final MarioState newState) {
            return newState == WITH_HAMMER || newState == DEAD;
        }
    },

    /**
     * State when Mario has collected the hammer power-up.
     */
    WITH_HAMMER {
        @Override
        public AbstractMarioState createState(final Mario mario) {
            return new WithHammerState(mario);
        }

        @Override
        public boolean canTransitionTo(final MarioState newState) {
            return newState == NORMAL || newState == DEAD;
        }
    },

    /**
     * State when Mario has lost all lives.
     */
    DEAD {
        @Override
        public AbstractMarioState createState(final Mario mario) {
            return new DeadState(mario);
        }

        @Override
        public boolean canTransitionTo(final MarioState newState) {
            return newState == NORMAL;
        }
    };

    /**
     * Factory method for creating state-specific instances.
     *
     * @param mario the Mario instance to associate with the state
     * @return new state instance bound to the specified Mario
     * @throws NullPointerException if mario is null
     */
    public abstract AbstractMarioState createState(Mario mario);

    /**
     * Validates state transition legality.
     *
     * @param newState target state to verify
     * @return true if transition is permitted by game rules
     */
    public abstract boolean canTransitionTo(MarioState newState);
}
