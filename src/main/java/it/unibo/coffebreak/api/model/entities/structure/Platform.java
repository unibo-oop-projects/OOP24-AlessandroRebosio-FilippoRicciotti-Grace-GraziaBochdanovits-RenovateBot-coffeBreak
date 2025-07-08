package it.unibo.coffebreak.api.model.entities.structure;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.impl.model.entities.structure.platform.AbstractPlatform.CollisionSide;

/**
 * Represents a platform where entities can stand or move on.
 * Platforms may have different slopes affecting movement.
 * 
 * @author Alessandro Rebosio
 */
public interface Platform extends Entity {
    /**
     * Destroys or breaks the platform, changing its state.
     */
    void destroy();

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
