package it.unibo.coffebreak.model.entity.impl;

import it.unibo.coffebreak.model.entity.api.Movable;
import it.unibo.coffebreak.model.utility.Position;
import it.unibo.coffebreak.model.utility.Vector2D;

/**
 * Implements the movement strategy for a fire enemy.
 */
public class FireMovementStrategy implements Movable {

    private Position enemyPosition;

    /**
     * Constructs a FireMovementStrategy for the specified enemy.
     *
     * @param enemy the enemy that will use this movement strategy
     */
    public FireMovementStrategy(final Enemy enemy) {
        this.enemyPosition = new Position(enemy.getPosition().x(), 
                                            enemy.getPosition().y());
    }

    /**
     * Moves the enemy in the specified direction.
     *
     * @param direction the direction in which to move the enemy
     */
    @Override
    public void move(final Vector2D direction) {
        this.enemyPosition = new Position(enemyPosition.x() + direction.getX(), 
                                    enemyPosition.y() + direction.getY());
    }
}
