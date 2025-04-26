package it.unibo.coffebreak.model.impl.entity.ladder;

import it.unibo.coffebreak.model.api.entity.ladder.Climbable;

/**
 * Base decorator class for {@link Climbable} entities, following the Decorator pattern.
 * <p>
 * This class serves as a wrapper around an existing {@code Climbable} instance,
 * allowing dynamic modification of its behavior without altering the original object.
 * Subclasses should override specific methods to change climbing properties.
 * </p>
 *
 * @see Climbable
 * @see BasicLadder
 */
public class LadderDecorator implements Climbable {

    /**
     * The wrapped {@link Climbable} instance being decorated.
     * All operations are delegated to this instance unless overridden.
     */
    private final Climbable baseLadder;

    /**
     * Constructs a new decorator wrapping the specified {@link Climbable} instance.
     *
     * @param baseLadder the ladder instance to be decorated (cannot be {@code null})
     * @throws IllegalArgumentException if {@code baseLadder} is {@code null}
     */
    public LadderDecorator(final Climbable baseLadder) {
        this.baseLadder = baseLadder;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Delegates to the wrapped {@link Climbable} instance by default.
     * </p>
     */
    @Override
    public boolean canClimb() {
        return this.baseLadder.canClimb();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Delegates to the wrapped {@link Climbable} instance by default.
     * </p>
     */
    @Override
    public float getClimbSpeed() {
        return this.baseLadder.getClimbSpeed();
    }
}
