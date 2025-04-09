package it.unibo.coffebreak.model.api.entity.item;

import it.unibo.coffebreak.model.entity.api.Entity;

/**
 * Represents a collectible item in the game world.
 * Collectible items can be gathered by the player and typically provide
 * benefits
 * such as points, power-ups, or other game effects.
 * 
 * @author Alessandro Rebosio
 */
public interface Collectible {

    /**
     * Checks if this collectible has been collected by the player.
     * 
     * @return {@code true} if the item has been collected, {@code false} otherwise
     */
    boolean isCollected();

    /**
     * Gets the value or points associated with this collectible.
     * The value may vary depending on the collectible's state or type.
     * 
     * @return the numeric value of this collectible
     */
    int getValue();

    /**
     * Performs the collection action when the player gathers this item.
     * 
     * @param collector the entity that collects the item, typically the player
     */
    void collect(Entity collector);

    /**
     * Resets the collectible to its initial uncollected state.
     * This method should prepare the collectible to be available for collection
     * again,
     * typically after a level reset or game restart.
     */
    void reset();
}
