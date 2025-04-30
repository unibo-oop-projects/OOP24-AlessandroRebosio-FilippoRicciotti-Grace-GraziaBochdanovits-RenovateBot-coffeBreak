package it.unibo.coffebreak.model.impl.entity.ladder;

import it.unibo.coffebreak.model.api.entity.ladder.Climbable;

/**
 * A decorator that enhances a ladder's climbing speed, making it faster than standard ladders.
 * <p>
 * This decorator overrides the {@link #getClimbSpeed()} method to provide a higher fixed speed value,
 * while preserving other properties of the wrapped ladder (like climbability). 
 * Useful for creating special "boosted" ladder variants in the game.
 * </p>
 *
 * @see LadderDecorator
 * @see BasicLadder
 * @see Climbable
 */
public class FastLadder extends LadderDecorator {

    /**
     * The enhanced climbing speed provided by this decorator.
     * This value is double the default speed of {@link BasicLadder#DEFAULT_SPEED}.
     */
    private static final float SPEED = 4.0f;

    /**
     * Creates a new fast ladder decorator wrapping the specified base ladder.
     *
     * @param baseLadder the ladder to be enhanced (cannot be {@code null})
     * @throws IllegalArgumentException if {@code baseLadder} is {@code null}
     */
    public FastLadder(final Climbable baseLadder) {
        super(baseLadder);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns the enhanced climbing speed of {@value #SPEED}, overriding the base ladder's speed.
     * This speed is fixed regardless of the wrapped ladder's original speed.
     * </p>
     */
    @Override
    public float getClimbSpeed() {
        return SPEED;
    }
}
