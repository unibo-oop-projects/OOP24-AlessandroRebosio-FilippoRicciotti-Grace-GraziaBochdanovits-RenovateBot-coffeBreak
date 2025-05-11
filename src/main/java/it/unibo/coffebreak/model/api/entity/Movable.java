package it.unibo.coffebreak.model.api.entity;

import it.unibo.coffebreak.model.impl.util.Position2D;
import it.unibo.coffebreak.model.impl.util.Vector2D;

/**
 * Defines the behavior for movable game entities. Implementations of this interface
 * calculate new positions based on movement vectors while maintaining immutability
 * of the position objects.
 * 
 * This interface follows the Strategy pattern, allowing different movement
 * algorithms to be applied to game entities dynamically.
 * 
 * @see Position2D
 * @see Vector2D
 */
public interface Movable {

    /**
     * Calculates the resulting position after applying a movement vector to the
     * current position. The implementation must not modify the original position.
     *
     * @param currentPosition the current position of the entity (must not be {@code null})
     * @param direction the movement vector containing direction and magnitude
     *        (must not be {@code null})
     * @return a new {@code Position} object representing the location after movement
     *         (never {@code null})
     * 
     */
    Position2D move(Position2D currentPosition, Vector2D direction);
}
