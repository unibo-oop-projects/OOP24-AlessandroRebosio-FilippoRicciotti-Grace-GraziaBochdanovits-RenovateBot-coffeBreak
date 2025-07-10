package it.unibo.coffebreak.api.model.physics;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.Model;

/**
 * Unified physics engine interface that coordinates physics calculations and
 * collision detection.
 * <p>
 * This interface provides a high-level abstraction for managing both physics
 * and collision
 * systems, allowing entities to interact with a single unified system rather
 * than
 * managing physics and collision separately.
 * </p>
 * 
 * <h3>Design Principles:</h3>
 * <ul>
 * <li><strong>Separation of Concerns:</strong> Physics and collision remain
 * separate internally</li>
 * <li><strong>Unified Interface:</strong> Entities interact with a single
 * system</li>
 * <li><strong>Consistent Behavior:</strong> All entities follow the same
 * physics/collision rules</li>
 * <li><strong>Extensibility:</strong> New physics or collision behaviors can be
 * added easily</li>
 * </ul>
 * 
 * @author Alessandro Rebosio
 */
public interface PhysicsEngine {

    /**
     * Updates an entity's physics for the current frame.
     * <p>
     * This method applies physics calculations (gravity, movement, etc.) to the
     * entity
     * and then performs collision detection and response. The order of operations
     * ensures consistent and predictable behavior across all entities.
     * </p>
     * 
     * @param entity    the entity to update
     * @param model     the game model containing all entities and world boundaries
     * @param deltaTime the time elapsed since the last update, in seconds
     * @throws IllegalArgumentException if any parameter is null or deltaTime is
     *                                  negative
     */
    void updateEntity(Entity entity, Model model, float deltaTime);

    /**
     * Applies gravity to an entity if it's not supported by a platform.
     * <p>
     * This method checks if the entity is on a platform and applies gravity
     * accordingly. The gravity force is determined by the physics configuration.
     * </p>
     * 
     * @param entity    the entity to apply gravity to
     * @param deltaTime the time elapsed since the last update, in seconds
     * @throws IllegalArgumentException if entity is null or deltaTime is negative
     */
    void applyGravity(Entity entity, float deltaTime);

    /**
     * Applies movement physics to an entity based on its current velocity.
     * <p>
     * This method updates the entity's position based on its velocity and the
     * elapsed time, implementing proper physics integration.
     * </p>
     * 
     * @param entity    the entity to move
     * @param deltaTime the time elapsed since the last update, in seconds
     * @throws IllegalArgumentException if entity is null or deltaTime is negative
     */
    void applyMovement(Entity entity, float deltaTime);

    /**
     * Performs collision detection and response for an entity against the world.
     * <p>
     * This method checks for collisions between the entity and world boundaries,
     * platforms, and other entities, then applies appropriate collision responses.
     * </p>
     * 
     * @param entity the entity to check collisions for
     * @param model  the game model containing all entities and world boundaries
     * @throws IllegalArgumentException if any parameter is null
     */
    void handleCollisions(Entity entity, Model model);

    /**
     * Checks if an entity is currently on a platform.
     * <p>
     * This method determines whether the entity is standing on a platform,
     * which affects gravity application and jump ability.
     * </p>
     * 
     * @param entity the entity to check
     * @param model  the game model containing all platforms
     * @return true if the entity is on a platform, false otherwise
     * @throws IllegalArgumentException if any parameter is null
     */
    boolean isOnPlatform(Entity entity, Model model);
}
