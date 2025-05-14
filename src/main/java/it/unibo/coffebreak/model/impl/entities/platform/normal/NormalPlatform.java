package it.unibo.coffebreak.model.impl.entities.platform.normal;

import it.unibo.coffebreak.model.impl.common.Dimension2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.platform.AbstractPlatform;

/**
 * Implementation of a normal platform in the game world.
 * 
 * @see AbstractPlatform
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class NormalPlatform extends AbstractPlatform {

    /**
     * Constructs a new NormalPlatform with specified position, dimensions and slope.
     * 
     * @param position the 2D position of the platform (cannot be null)
     * @param dimension the 2D dimensions of the platform (cannot be null)
     * @param slope the slope orientation of the platform (cannot be null)
     */
    public NormalPlatform(final Position2D position, final Dimension2D dimension, final Slope slope) {
        super(position, dimension, slope);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canBreak() {
        return false;
    }

}
