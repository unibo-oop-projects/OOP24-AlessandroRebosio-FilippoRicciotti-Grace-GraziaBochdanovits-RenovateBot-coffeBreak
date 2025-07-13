package it.unibo.coffebreak.api.model.entities.structure;

import it.unibo.coffebreak.api.model.entities.Entity;

/**
 * Represents a platform where entities can stand or move on.
 * Platforms may have different slopes affecting movement.
 * 
 * @author Alessandro Rebosio
 */
public interface Platform extends Entity {

    /**
     * Enum representing the possible sides where a collision can occur between
     * entities.
     */
    enum CollisionSide {
        /**
         * Collision with the top side of the platform.
         */
        TOP,
        /**
         * Collision with the bottom side of the platform.
         */
        BOTTOM,
        /**
         * Collision with the left side of the platform.
         */
        LEFT,
        /**
         * Collision with the right side of the platform.
         */
        RIGHT,
        /**
         * No collision or undetermined collision side.
         */
        NONE
    }

    /**
     * Destroys or breaks the platform, changing its state.
     */
    void destroy();

    /**
     * Determines whether it is possible to move down from the current platform.
     *
     * @return {@code true} if moving down is allowed; {@code false} otherwise.
     */
    boolean canGoDown();

    /**
     * Checks if the platform is in a broken/destroyed state.
     * 
     * @return true if the platform has been destroyed,
     *         false if it's still intact and functional
     */
    boolean isBroken();

    /**
     * Determines the side of this platform on which the specified entity
     * is colliding.
     * 
     * @param other the entity to check collision side against
     * @return the {@link CollisionSide} indicating the side of this platform
     *         involved in the collision with the other entity
     */
    CollisionSide getCollisionSide(Entity other);
}
