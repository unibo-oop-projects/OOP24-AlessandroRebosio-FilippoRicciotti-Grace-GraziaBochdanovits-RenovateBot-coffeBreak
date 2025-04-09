package it.unibo.coffebreak.model.entity.impl;

import it.unibo.coffebreak.model.utility.Dimension;
import it.unibo.coffebreak.model.utility.Position;

/**
 * Represents the most elementary type of platform in the game,
 * serving as a concrete implementation of {@link AbstractPlatform}.
 * By default, instances of this platform are non-breakable and have
 * a moderate friction coefficient of 0.5.
 */
public class BasicPlatform extends AbstractPlatform {

    private static final float DEFAULT_FRICTION = 0.5f;
    private final boolean breakable;

    /**
     * Constructs a BasicPlatform with the specified position and dimension.
     *
     * @param position the position of the platform
     * @param dimension the dimension of the platform
     */
    public BasicPlatform(final Position position, final Dimension dimension) {
        super(position, dimension);
        this.breakable = false;
    }

    /**
     * Determines if this platform can be broken during gameplay.
     * Basic platforms are always non-breakable by default.
     *
     * @return {@code false}
     */
    @Override
    public boolean canBreak() {
        return this.breakable;
    }

    /**
     * Gets the friction coefficient of this platform.
     * The returned value affects how entities interact with the platform
     *
     * @return the constant friction value of 0.5f
     */
    @Override
    public float getFriction() {
        return DEFAULT_FRICTION;
    }
}
