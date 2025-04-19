package it.unibo.coffebreak.model.impl.entity.mario;

import it.unibo.coffebreak.model.impl.entity.MarioState;

/**
 * Represents Mario's default state without any special abilities or power-ups.
 * <p>
 * In this state:
 * <ul>
 *   <li>Mario can move and jump normally</li>
 *   <li>Mario can climb ladders</li>
 *   <li>Hammer use is not available</li>
 *   <li>No special abilities or power-ups are active</li>
 * </ul>
 * This is Mario's base state that he returns to after losing power-ups.
 */
public class NormalState extends AbstractMarioState {

    /**
     * Constructs a new NormalState for the specified Mario instance.
     *
     * @param mario the Mario instance entering normal state, cannot be null
     * @throws NullPointerException if {@code mario} is null
     */
    public NormalState(final Mario mario) {
        super(mario);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canClimb() {
        return true;
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
        return MarioState.NORMAL;
    }

}
