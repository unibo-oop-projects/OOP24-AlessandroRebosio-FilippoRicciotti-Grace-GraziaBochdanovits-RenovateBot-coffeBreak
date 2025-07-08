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
}
