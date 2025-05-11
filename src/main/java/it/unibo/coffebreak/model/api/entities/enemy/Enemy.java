package it.unibo.coffebreak.model.api.entities.enemy;

import it.unibo.coffebreak.model.api.entities.Entity;

/**
 * Represents an enemy entity (e.g., barrels, fireballs) that can harm the
 * player.
 * 
 * @author Alessandro Rebosio
 */
public interface Enemy extends Entity {

    /**
     * Destroys this enemy (e.g., when hit by a hammer or when it falls off-screen).
     */
    void destroy();

    /**
     * @return true if this enemy has been destroyed, false otherwise
     */
    boolean isDestroyed();
}
