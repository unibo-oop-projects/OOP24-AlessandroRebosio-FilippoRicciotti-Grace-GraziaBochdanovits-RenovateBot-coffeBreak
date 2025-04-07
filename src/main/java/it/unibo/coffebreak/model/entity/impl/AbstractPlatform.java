package it.unibo.coffebreak.model.entity.impl;

import it.unibo.coffebreak.model.entity.api.Platform;
import it.unibo.coffebreak.model.utility.Dimension;
import it.unibo.coffebreak.model.utility.Position;

/**
 * An abstract base implementation of the {@link Platform} interface that extends {@link GameEntity}.
 * This class serves as the foundation for all concrete platform implementations in the game,
 * providing common functionality and serving as the base class for the Decorator pattern.
 */
public abstract class AbstractPlatform extends GameEntity implements Platform {

    /**
     * Constructs a new platform with the specified position and dimensions.
     *
     * @param position the initial position of the platform in game coordinates
     * @param dimension the physical dimensions of the platform
     */
    public AbstractPlatform(final Position position, final Dimension dimension) {
        super(position, dimension);
    }

    /**
     * Gets the platform's position in game coordinates.
     * This method provides access to the position stored in the parent {@link GameEntity} class.
     *
     * @return the platform's current position
     * @see GameEntity#getPosition()
     */
    @Override
    public final Position getPlatformPosition() {
        return super.getPosition(); 
    }

    /**
     * Gets the platform's physical dimensions.
     * This method provides access to the dimensions stored in the parent {@link GameEntity} class.
     *
     * @return the platform's dimensions
     * @see GameEntity#getDimension()
     */
    @Override
    public final Dimension getPlatformDimension() {
        return super.getDimension(); 
    }

    /**
     * Updates the platform's state.
     * The base implementation does nothing. Subclasses should override this method
     * to implement platform-specific behavior if needed.
     *
     * @param deltaTime the time elapsed since the last update (in milliseconds)
     */
    @Override
    public void update(final long deltaTime) {
    }
}
