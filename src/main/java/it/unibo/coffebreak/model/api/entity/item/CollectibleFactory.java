package it.unibo.coffebreak.model.api.entity.item;

import it.unibo.coffebreak.model.impl.util.Dimension2D;
import it.unibo.coffebreak.model.impl.util.Position2D;

/**
 * Factory interface for creating collectible items with configurable position
 * and size.
 * Provides methods to create all types of collectibles defined in the game.
 * 
 * @author Alessandro Rebosio
 */
public interface CollectibleFactory {

    /**
     * Creates a hammer collectible at specified position and size.
     * The hammer grants barrel-breaking ability when collected.
     * 
     * @param position  the spawn position of the hammer
     * @param dimension the size of the hammer
     * @return a new hammer instance
     * @throws IllegalArgumentException if position or dimension is null
     */
    Collectible createHammer(Position2D position, Dimension2D dimension);

    /**
     * Creates a coin collectible at specified position and size.
     * Coins provide standard points when collected.
     * 
     * @param position  the spawn position of the coin
     * @param dimension the size of the coin
     * @return a new coin instance
     * @throws IllegalArgumentException if position or dimension is null
     */
    Collectible createCoin(Position2D position, Dimension2D dimension);

    /**
     * Creates a bag collectible at specified position and size.
     * Bags provide higher value points than coins.
     * 
     * @param position  the spawn position of the bag
     * @param dimension the size of the bag
     * @return a new bag instance
     * @throws IllegalArgumentException if position or dimension is null
     */
    Collectible createBag(Position2D position, Dimension2D dimension);

    /**
     * Creates a hat collectible at specified position and size.
     * Hats provide special bonuses when collected.
     * 
     * @param position  the spawn position of the hat
     * @param dimension the size of the hat
     * @return a new hat instance
     * @throws IllegalArgumentException if position or dimension is null
     */
    Collectible createHat(Position2D position, Dimension2D dimension);
}
