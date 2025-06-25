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

    private final boolean canPassThrough;
    private final boolean reversesDirection;

    /**
     * Constructs a new Platform with specified position, dimensions and slope.
     * 
     * @param position          the 2D position of the platform (cannot be null)
     * @param dimension         the 2D dimension of the platform (cannot be null)
     * @param canPassThrough    whether Mario can pass through this platform
     * @param reversesDirection whether enemy should reverse direction when hitting
     *                          this platform
     */
    public AbstractPlatform(final Position position, final BoundigBox dimension,
            final boolean canPassThrough, final boolean reversesDirection) {
        super(position, dimension);
        this.canPassThrough = canPassThrough;
        this.reversesDirection = reversesDirection;
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
     * @return true if Mario can pass through this platform (when is climbing)
     */
    @Override
    public boolean canPassThrough() {
        return this.canPassThrough;
    }

    /**
     * @return true if barrels should reverse direction when hitting this platform
     */
    @Override
    public boolean reversesDirection() {
        return this.reversesDirection;
    }
}
