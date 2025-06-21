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
     * Determines whether characters (like Mario) can pass through this platform.
     * When true, characters can jump through the platform's bottom
     * surface without colliding.
     * 
     * @return true if the platform is passable,
     *         false if it should block movement
     */
    boolean canPassThrough();

    /**
     * Determines whether this platform should reverse the movement direction
     * of entities (like barrels) when they collide with it.
     * 
     * @return true if colliding entities should reverse direction,
     *         false if the collision should have no directional effect
     */
    boolean reversesDirection();
}
