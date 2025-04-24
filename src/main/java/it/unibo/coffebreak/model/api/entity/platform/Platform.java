package it.unibo.coffebreak.model.api.entity.platform;

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

}
