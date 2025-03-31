package it.unibo.coffebreak.model.entity.api;

import it.unibo.coffebreak.model.entity.impl.Position;
import it.unibo.coffebreak.model.entity.impl.Dimension;

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
    public Position getPosition();

    /**
    * Retrieves the dimensions of the entity.
    *
    * @return the current dimensions of the entity.
    */
    public Dimension getDimension();

    /**
    * Updates the state of the entity based on the elapsed time.
    *
    * @param deltaTime the time elapsed since the last update, in milliseconds.
    */
    public abstract void update(long deltaTime);
}
