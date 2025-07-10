package it.unibo.coffebreak.api.model.entities;

/**
 * Interface for entities that are managed by the physics engine.
 * <p>
 * This interface extends the base Entity interface to provide additional
 * functionality for entities that participate in physics simulation,
 * including gravity, movement, and collision response.
 * </p>
 * 
 * <h3>Physics-Enabled Entities:</h3>
 * <ul>
 * <li>Have velocity and can move</li>
 * <li>Are affected by gravity</li>
 * <li>Can interact with platforms</li>
 * <li>Participate in collision detection</li>
 * </ul>
 * 
 * @author Alessandro Rebosio
 */
public interface PhysicsEntity extends Entity {

    /** Default maximum falling speed in pixels per second. */
    float DEFAULT_MAX_FALLING_SPEED = 200f;

    /**
     * Called when this entity lands on a platform.
     * <p>
     * This method is invoked by the physics engine when the entity
     * collides with a platform from above, allowing the entity to
     * react appropriately (e.g., stop falling, enable jumping).
     * </p>
     */
    default void onPlatformLand() {
        // Default implementation - entities can override if needed
    }

    /**
     * Called when this entity leaves a platform.
     * <p>
     * This method is invoked by the physics engine when the entity
     * is no longer on a platform, allowing the entity to react
     * appropriately (e.g., start falling, disable jumping).
     * </p>
     */
    default void onPlatformLeave() {
        // Default implementation - entities can override if needed
    }

    /**
     * Determines if this entity should be affected by gravity.
     * <p>
     * Some entities (like climbing characters or flying objects)
     * may not be affected by gravity under certain conditions.
     * </p>
     * 
     * @return true if gravity should be applied, false otherwise
     */
    default boolean isAffectedByGravity() {
        return true;
    }

    /**
     * Determines if this entity can stand on platforms.
     * <p>
     * Some entities (like projectiles or decorative objects)
     * may pass through platforms without stopping.
     * </p>
     * 
     * @return true if the entity can stand on platforms, false otherwise
     */
    default boolean canStandOnPlatforms() {
        return true;
    }

    /**
     * Gets the maximum falling speed for this entity.
     * <p>
     * This method allows entities to have different terminal velocities,
     * preventing them from falling indefinitely fast.
     * </p>
     * 
     * @return the maximum falling speed in pixels per second
     */
    default float getMaxFallingSpeed() {
        return DEFAULT_MAX_FALLING_SPEED;
    }
}
