package it.unibo.coffebreak.impl.model.entities.enemy.fire;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.Movable;
import it.unibo.coffebreak.api.model.entities.enemy.fire.Fire;
import it.unibo.coffebreak.impl.common.Dimension;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;
import it.unibo.coffebreak.impl.model.entities.enemy.AbstractEnemy;

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
     * @param position the initial position of the fire in 2D space
     * @param dimension the dimension of the fire in the game world
     */
    public GameFire(final Position position, final Dimension dimension) {
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
        // Default empty implementation
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final float deltaTime) {
        // TODO: fire movement
    }
}
