package it.unibo.coffebreak.model.impl.entity.platform;

import it.unibo.coffebreak.model.api.entity.platform.Platform;
import it.unibo.coffebreak.model.impl.entity.GameEntity;
import it.unibo.coffebreak.model.impl.util.Dimension2D;
import it.unibo.coffebreak.model.impl.util.Position2D;

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
    public AbstractPlatform(final Position2D position, final Dimension2D dimension) {
        super(position, dimension);
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
