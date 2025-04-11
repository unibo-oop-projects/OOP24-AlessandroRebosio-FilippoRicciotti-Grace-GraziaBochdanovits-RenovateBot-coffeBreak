package it.unibo.coffebreak.model.impl.entity.enemy;

import it.unibo.coffebreak.model.impl.entity.EnemyType;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;

/**
 * Represents a barrel enemy in the game.
 */
public class Barrel extends Enemy {

    /**
     * Constructs a Barrel enemy with the specified position, dimension, state, and velocity.
     *
     * @param position the position of the barrel enemy
     * @param dimension the dimension of the barrel enemy
     * @param state the state of the barrel enemy
     * @param velocity the velocity of the barrel enemy
     */
    public Barrel(final Position position, final Dimension dimension, final EnemyType state, final Vector2D velocity) {
        super(position, dimension, state, velocity);
    }
}
