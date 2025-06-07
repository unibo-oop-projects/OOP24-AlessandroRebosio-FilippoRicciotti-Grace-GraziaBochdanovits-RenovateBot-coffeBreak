package it.unibo.coffebreak.impl.model.entities.structure.platform;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.impl.common.BoundingBox2D;
import it.unibo.coffebreak.impl.common.Position2D;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;

/**
 * Concrete implementation of a {@link Platform} entity in the game world.
 * <p>
 * This class represents a physical platform that can influence the movement
 * of entities standing on it through its slope property.
 * </p>
 * 
 * @see Platform
 * @see GameEntity
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public abstract class AbstractPlatform extends AbstractEntity implements Platform {
    /**
     * Constructs a new Platform with specified position, dimensions and slope.
     * 
     * @param position  the 2D position of the platform (cannot be null)
     * @param dimension the 2D dimensions of the platform (cannot be null)
     */
    public AbstractPlatform(final Position2D position, final BoundingBox2D dimension) {
        super(position, dimension);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Current implementation does nothing when a collision occurs.
     * This should be overridden to implement specific collision behavior.
     * </p>
     */
    @Override
    public void onCollision(final Entity other) {
        // Default empty implementation
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        // Default empty implementation
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBroken() {
        return false;
    }
}
