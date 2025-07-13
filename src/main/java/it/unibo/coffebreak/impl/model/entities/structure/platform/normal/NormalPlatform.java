package it.unibo.coffebreak.impl.model.entities.structure.platform.normal;

import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.structure.platform.AbstractPlatform;

/**
 * Implementation of a normal platform in the game world.
 * 
 * @see AbstractPlatform
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class NormalPlatform extends AbstractPlatform {

    /**
     * Constructs a new NormalPlatform with specified position, dimensions and
     * slope.
     * 
     * @param position  the 2D position of the platform (cannot be null)
     * @param dimension the 2D dimension of the platform (cannot be null)
     */
    public NormalPlatform(final Position position, final BoundigBox dimension) {
        super(position, dimension);
    }

    /**
     * Constructs a new Platform with specified position, dimensions and canGoDown property.
     * 
     * @param position   the 2D position of the platform (cannot be null)
     * @param dimension  the 2D dimension of the platform (cannot be null)
     * @param canGoDown  true if entities (e.g., Mario) can go down through this platform
     */
    public NormalPlatform(final Position position, final BoundigBox dimension, final boolean canGoDown) {
        super(position, dimension, canGoDown);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBroken() {
        return false;
    }
}
