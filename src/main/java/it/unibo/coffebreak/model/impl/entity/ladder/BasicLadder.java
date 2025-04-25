package it.unibo.coffebreak.model.impl.entity.ladder;

import it.unibo.coffebreak.model.api.entity.ladder.Climbable;
import it.unibo.coffebreak.model.impl.entity.GameEntity;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;

/**
 * A basic implementation of a climbable ladder in the game world.
 * This entity is always climbable by default and provides a constant climbing speed.
 * <p>
 * This class serves as the base implementation for more specialized ladder types
 * that might be decorated or extended.
 * </p>
 *
 * @see Climbable
 * @see GameEntity
 */
public class BasicLadder extends GameEntity implements Climbable {

    /**
     * The default climbing speed for this ladder, in arbitrary units.
     * A value of {@code 2.0f} represents a standard climbing speed.
     */
    private static final float DEFAULT_SPEED = 2.0f;

    /**
     * Flag indicating whether this ladder is currently climbable.
     * For {@code BasicLadder}, this is always {@code true} unless modified by subclasses or decorators.
     */
    private final boolean climbable;

    /**
     * Constructs a new BasicLadder with the specified position and dimensions.
     *
     * @param position the initial position of the ladder in the game world
     * @param dimension the physical dimensions of the ladder
     * @throws IllegalArgumentException if position or dimension are null (if applicable)
     */
    public BasicLadder(final Position position, final Dimension dimension) {
        super(position, dimension);
        this.climbable = true;
    }

    /**
     * Updates the ladder's state over time.
     * <p>
     * The basic implementation does nothing, as a simple ladder doesn't require state updates.
     * Subclasses may override this to implement time-dependent behavior.
     * </p>
     *
     * @param deltaTime the time elapsed since the last update, in milliseconds
     */
    @Override
    public void update(final long deltaTime) {
    }

    /**
     * {@inheritDoc}
     * <p>
     * For {@code BasicLadder}, this always returns {@code true} unless modified by subclasses.
     * </p>
     */
    @Override
    public boolean canClimb() {
        return climbable;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns the default climbing speed of {@value #DEFAULT_SPEED}.
     * </p>
     */
    @Override
    public float getClimbSpeed() {
        return DEFAULT_SPEED;
    }
}
