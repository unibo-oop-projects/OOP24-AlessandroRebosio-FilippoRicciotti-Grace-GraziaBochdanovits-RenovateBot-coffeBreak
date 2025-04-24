package it.unibo.coffebreak.model.impl.entity.enemy;

import it.unibo.coffebreak.model.impl.entity.EnemyType;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;

/**
 * Concrete implementation of an enemy representing a rolling barrel.
 * <p>
 * Barrels typically move in straight lines and bounce off obstacles.
 * The actual movement pattern is determined by the {@link EnemyType#BARREL} movement strategy.
 * </p>
 * 
 * @see Enemy
 * @see EnemyType#BARREL
 */
public class Barrel extends Enemy {

    /**
     * Constructs a Barrel enemy with the specified position, dimension and velocity.
     *
     * @param position the position of the barrel enemy
     * @param dimension the dimension of the barrel enemy
     * @param velocity the velocity of the barrel enemy
     */
    public Barrel(final Position position, final Dimension dimension, final Vector2D velocity) {
        super(position, dimension, EnemyType.BARREL, velocity);
    }
}
