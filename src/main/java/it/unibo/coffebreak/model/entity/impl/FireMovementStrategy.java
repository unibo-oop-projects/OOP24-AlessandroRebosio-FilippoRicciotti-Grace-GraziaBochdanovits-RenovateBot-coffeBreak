package it.unibo.coffebreak.model.entity.impl;

import it.unibo.coffebreak.model.entity.api.Movable;
import it.unibo.coffebreak.model.utility.Position;
import it.unibo.coffebreak.model.utility.Vector2D;

/**
 * Implements the movement strategy for fire-type enemies.
 * Provides linear movement based on a direction vector, typically used
 * for fireballs or other projectile-like enemies.
 * 
 * @see Movable
 * @see Position
 * @see Vector2D
 */
public class FireMovementStrategy implements Movable {

    /**
     * Calculates the new position by applying directional movement.
     * 
     * @param currentPosition the starting position (must not be {@code null})
     * @param direction the movement vector containing direction and magnitude
     *        (must not be {@code null})
     * @return a new {@code Position} representing the destination after movement
     *         (never {@code null})
     * @throws NullPointerException if either parameter is {@code null}
     * 
     */
    @Override
    public Position move(final Position currentPosition, final Vector2D direction) {
        return new Position(currentPosition.x() + direction.getX(),
                            currentPosition.y() + direction.getY());
    }
}
