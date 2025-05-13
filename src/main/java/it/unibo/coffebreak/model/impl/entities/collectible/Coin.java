package it.unibo.coffebreak.model.impl.entities.collectible;

import it.unibo.coffebreak.model.impl.common.Dimension2D;
import it.unibo.coffebreak.model.impl.common.Position2D;

/**
 * Represents a coin collectible item in the game. When collected by a player
 * character,
 * it provides a certain value of points to the player's score.
 * Extends {@link AbstractCollectible} to inherit basic collectible properties
 * and behavior.
 * 
 * @author Alessandro Rebosio
 */
public class Coin extends AbstractCollectible {

    /**
     * Constructs a new Coin with the specified position, dimensions, and point
     * value.
     *
     * @param position  the 2D position of the coin in the game world
     * @param dimension the 2D dimensions (size) of the coin
     * @param value     the point value this coin is worth when collected
     */
    public Coin(final Position2D position, final Dimension2D dimension, final int value) {
        super(position, dimension, value);
    }
}
