package it.unibo.coffebreak.impl.model.entities.collectible.coin;

import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.collectible.AbstractCollectible;

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

    private static final int VALUE = 100;

    /**
     * Constructs a new Coin with the specified position, dimensions, and point
     * value.
     *
     * @param position the 2D position of the coin in the game world
     */
    public Coin(final Position position) {
        super(position, VALUE);
    }
}
