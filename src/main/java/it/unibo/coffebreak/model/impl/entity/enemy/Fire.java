package it.unibo.coffebreak.model.impl.entity.enemy;

import it.unibo.coffebreak.model.impl.util.Dimension2D;
import it.unibo.coffebreak.model.impl.util.Position2D;
import it.unibo.coffebreak.model.impl.util.Vector2D;

/**
 * Represents a fire-type enemy in the game.
 * <p>
 * This enemy typically implements homing or unpredictable movement behavior,
 * differentiating itself from other enemy types like barrels. The actual movement
 * pattern is determined by the {@link EnemyType#FIRE} movement strategy.
 * </p>
 * 
 * @see Enemy
 * @see EnemyType#FIRE
 */
public class Fire extends Enemy {

    /**
     * Constructs a Fire enemy with the specified position, dimension and velocity.
     *
     * @param position the position of the fire enemy
     * @param dimension the dimension of the fire enemy
     * @param velocity the velocity of the fire enemy
     */
    public Fire(final Position2D position, final Dimension2D dimension, final Vector2D velocity) {
        super(position, dimension, EnemyType.FIRE, velocity);
    }

}
