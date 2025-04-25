package it.unibo.coffebreak.model.impl.entity.ladder;

import it.unibo.coffebreak.model.api.entity.ladder.Climbable;

/**
 * A decorator that makes a ladder non-climbable, representing a broken state.
 * <p>
 * This decorator overrides {@link #canClimb()} to always return {@code false},
 * while maintaining all other properties of the wrapped ladder unchanged.
 * </p>
 *
 * @see LadderDecorator
 * @see Climbable
 */
public class BrokenLadder extends LadderDecorator {

    /**
     * Creates a new broken ladder decorator wrapping the specified base ladder.
     *
     * @param baseLadder the ladder to be made non-climbable (cannot be {@code null})
     * @throws IllegalArgumentException if {@code baseLadder} is {@code null}
     */
    public BrokenLadder(final Climbable baseLadder) {
        super(baseLadder);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Always returns {@code false} as this ladder is broken and cannot be climbed.
     * </p>
     */
    @Override
    public boolean canClimb() {
        return false;
    }
}
