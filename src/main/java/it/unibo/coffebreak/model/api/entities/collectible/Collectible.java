package it.unibo.coffebreak.model.api.entities.collectible;

import it.unibo.coffebreak.model.api.entities.Entity;

/**
 * Represents a collectible item in the game world (e.g., Pauline's purse, hat,
 * hammer).
 * When collected, it may provide points or special abilities to the player.
 * 
 * @author Alessandro Rebosio
 */
public interface Collectible extends Entity {

    /**
     * Collects this item, triggering its effect (e.g., adding points).
     */
    void collect();

    /**
     * @return true if this item has been collected, false otherwise
     */
    boolean isCollected();

    /**
     * @return the point value awarded when this item is collected
     */
    int getPointsValue();
}
