package it.unibo.coffebreak.impl.model.entities.structure.platform;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
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

    private final boolean canGoDown;

    /**
     * Constructs a new Platform with specified position, dimensions and slope.
     * 
     * @param position  the 2D position of the platform (cannot be null)
     * @param dimension the 2D dimension of the platform (cannot be null)
     */
    public AbstractPlatform(final Position position, final BoundigBox dimension) {
        super(position, dimension);
        this.canGoDown = false;
    }

    /**
     * Constructs a new Platform with specified position, dimensions and canGoDown property.
     * 
     * @param position   the 2D position of the platform (cannot be null)
     * @param dimension  the 2D dimension of the platform (cannot be null)
     * @param canGoDown  true if entities (e.g., Mario) can go down through this platform
     */
    public AbstractPlatform(final Position position, final BoundigBox dimension, final boolean canGoDown) {
        super(position, dimension);
        this.canGoDown = canGoDown;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Positions any colliding entity on top of this platform to prevent
     * intersection.
     * </p>
     */
    @Override
    public void onCollision(final Entity other) {
        other.setPosition(new Position(other.getPosition().x(),
                this.getPosition().y() - other.getDimension().height()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canGoDown() {
        return this.canGoDown;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        // Default empty implementation
    }
}
