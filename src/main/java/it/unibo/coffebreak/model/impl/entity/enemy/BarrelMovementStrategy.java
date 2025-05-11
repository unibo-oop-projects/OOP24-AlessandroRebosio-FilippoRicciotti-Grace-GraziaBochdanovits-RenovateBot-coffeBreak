package it.unibo.coffebreak.model.impl.entity.enemy;

import it.unibo.coffebreak.model.api.entity.Movable;
import it.unibo.coffebreak.model.impl.util.Position2D;
import it.unibo.coffebreak.model.impl.util.Vector2D;

/**
 * Concrete implementation of {@link Movable} for barrel-type enemies.
 * Provides linear movement based on a direction vector, typically used
 * for rolling barrels in platformer games.
 * 
 * @see Movable
 * @see Position2D
 * @see Vector2D
 */
public class BarrelMovementStrategy implements Movable {

    /**
     * Calculates the new position by applying a directional movement vector
     * to the current position.
     * 
     * @param currentPosition the starting position (must not be {@code null})
     * @param direction the movement vector containing both direction and magnitude
     *        (must not be {@code null})
     * @return a new {@code Position} representing the destination after movement
     *         (never {@code null})
     * @throws NullPointerException if either parameter is {@code null}
     * 
     */
    @Override
    public Position2D move(final Position2D currentPosition, final Vector2D direction) {
        return new Position2D(currentPosition.x() + direction.getX(),
                            currentPosition.y() + direction.getY());
    }
}
