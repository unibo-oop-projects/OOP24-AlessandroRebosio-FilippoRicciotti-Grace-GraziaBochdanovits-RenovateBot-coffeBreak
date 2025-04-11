package it.unibo.coffebreak.model.api.entity.platform;

import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;

/**
 * Represents a platform in the game.
 * A platform can have different properties such as the ability to break and its friction level.
 */
public interface Platform {
   /**
     * Determines if the platform can break during gameplay.
     * @return {@code true} if the platform is breakable, {@code false} otherwise
     */
    boolean canBreak();

    /**
     * Gets the friction level of the platform.
     *
     * @return the friction level as a float.
     */
    float getFriction();

    /**
     * Gets the current position of this platform in the game world coordinates.
     *
     * @return the platform's position as a {@link Position} object
     * @see Position
     */
    Position getPlatformPosition();

    /**
     * Gets the dimensions of this platform.
     *
     * @return the platform's dimensions as a {@link Dimension} object
     * @see Dimension
     */
    Dimension getPlatformDimension();

}
