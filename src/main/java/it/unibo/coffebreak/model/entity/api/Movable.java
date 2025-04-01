package it.unibo.coffebreak.model.entity.api;

import it.unibo.coffebreak.model.utility.Vector2D;

/**
 * Represents an entity that can move in a specified direction.
 */
public interface Movable {

    /**
     * Moves the entity in the specified direction.
     *
     * @param direction the vector representing the direction and magnitude of the movement.
     */
    void move(Vector2D direction);
}
