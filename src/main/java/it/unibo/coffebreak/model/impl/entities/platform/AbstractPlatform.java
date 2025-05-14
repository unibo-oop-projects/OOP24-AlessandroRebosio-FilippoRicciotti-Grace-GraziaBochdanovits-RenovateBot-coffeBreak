package it.unibo.coffebreak.model.impl.entities.platform;

import java.util.Objects;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.enemy.Enemy;
import it.unibo.coffebreak.model.api.entities.platform.Platform;
import it.unibo.coffebreak.model.impl.common.Dimension2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.AbstractEntity;
import it.unibo.coffebreak.model.impl.entities.mario.Mario;

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

    private final Slope slope;

    /**
     * Constructs a new Platform with specified position, dimensions and slope.
     * 
     * @param position the 2D position of the platform (cannot be null)
     * @param dimension the 2D dimensions of the platform (cannot be null)
     * @param slope the slope orientation of the platform (cannot be null)
     */
    public AbstractPlatform(final Position2D position, final Dimension2D dimension, final Slope slope) {
        super(position, dimension);
        this.slope = Objects.requireNonNull(slope);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Slope getSlope() {
        return this.slope;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSupporting(final Entity entity) {
        return entity instanceof Mario || entity instanceof Enemy;
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
        // Intentionally left blank
    }

    @Override
    public abstract boolean canBreak();

}
