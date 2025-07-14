package it.unibo.coffebreak.api.model.entities.enemy;

import it.unibo.coffebreak.api.model.entities.Entity;

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

    /**
     * Returns the score value awarded for destroying this enemy.
     *
     * @return the number of points awarded for killing this enemy
     */
    int killValue();
}
