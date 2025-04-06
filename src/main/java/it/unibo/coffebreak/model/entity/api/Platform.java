package it.unibo.coffebreak.model.entity.api;

/**
 * Represents a platform in the game.
 * A platform can have different properties such as the ability to break and its friction level.
 */
public interface Platform {
    /**
     * Determines if the platform can break.
     *
     * @return true if the platform can break, false otherwise.
     */
    boolean canBreak();

    /**
     * Gets the friction level of the platform.
     *
     * @return the friction level as a float.
     */
    float getFriction();
}
