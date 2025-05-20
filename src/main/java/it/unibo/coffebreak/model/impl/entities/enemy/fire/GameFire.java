package it.unibo.coffebreak.model.impl.entities.enemy.fire;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.Movable;
import it.unibo.coffebreak.model.api.entities.enemy.fire.Fire;
import it.unibo.coffebreak.model.impl.common.Dimension2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.AbstractEntity;
import it.unibo.coffebreak.model.impl.entities.enemy.AbstractEnemy;

/**
 * Represents a fire entity in the game, which is a specific type of enemy.
 * 
 * @see Fire
 * @see AbstractEntity
 * @author Grazia Bochdanovits de Kavna
 */
public class GameFire extends AbstractEnemy implements Fire, Movable {

    /**
     * Constructs a new GameFire with the specified position and dimensions.
     *
     * @param position  the initial position of the fire in 2D space
     * @param dimension the dimensions (width and height) of the fire
     */
    public GameFire(final Position2D position, final Dimension2D dimension) {
        super(position, dimension);
    }

    /**
     * Defines the behavior when this fire entity collides with another entity.
     * Currently, this method does not implement any specific collision behavior.
     *
     * @param other the entity that collided with this fire
     */
    @Override
    public void onCollision(final Entity other) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(final float deltaTime) {
        // TODO: Auto-generated method stub
    }
}
