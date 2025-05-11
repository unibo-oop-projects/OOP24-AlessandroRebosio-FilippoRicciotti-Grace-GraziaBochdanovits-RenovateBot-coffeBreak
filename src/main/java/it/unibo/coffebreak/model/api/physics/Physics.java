package it.unibo.coffebreak.model.api.physics;

import it.unibo.coffebreak.model.api.entities.Entity;

/**
 * Handles the physics and movement calculations for entities in the game world.
 * This includes gravity, collisions, and directional movement (e.g., jumping,
 * climbing, rolling barrels).
 * 
 * @author Alessandro Rebosio
 */
public interface Physics {

    /**
     * Represents the possible horizontal movement directions for entities.
     */
    enum Direction {
        /**
         * Movement towards the left side of the screen.
         */
        LEFT,
        /**
         * Movement towards the right side of the screen.
         */
        RIGHT,
        /**
         * No horizontal movement.
         */
        NONE
    }

    /**
     * Updates the position and velocity of an entity based on physics calculations.
     *
     * @param entity    the entity to update
     * @param deltaTime the time elapsed since the last update (in seconds)
     * @param direction the intended movement direction (or NONE for gravity-only
     *                  updates)
     */
    void updateMovement(Entity entity, float deltaTime, Direction direction);

}
