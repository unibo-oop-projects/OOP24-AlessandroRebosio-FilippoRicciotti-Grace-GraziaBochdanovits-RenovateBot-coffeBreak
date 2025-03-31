package it.unibo.coffebreak.model.entity.api;

import it.unibo.coffebreak.model.utility.Dimension;
import it.unibo.coffebreak.model.utility.Position;

/**
 * Represents a bonus system that can be applied to a game.
 * Provides methods to get, set and calculate the bonus value.
 */
public interface Entity {

    /**
     * Retrieves the position of the entity.
     *
     * @return the current position of the entity.
     */
    Position getPosition();

    /**
     * Retrieves the dimensions of the entity.
     *
     * @return the current dimensions of the entity.
     */
    Dimension getDimension();

    /**
     * Updates the state of the entity based on the elapsed time.
     *
     * @param deltaTime the time elapsed since the
     *                  last update, in milliseconds.
     */
    void update(long deltaTime);
}
