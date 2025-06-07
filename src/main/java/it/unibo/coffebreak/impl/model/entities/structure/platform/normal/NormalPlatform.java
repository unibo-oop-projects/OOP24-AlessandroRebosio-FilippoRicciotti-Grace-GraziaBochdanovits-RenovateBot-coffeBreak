package it.unibo.coffebreak.impl.model.entities.structure.platform.normal;

import it.unibo.coffebreak.impl.common.BoundingBox2D;
import it.unibo.coffebreak.impl.common.Position2D;
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
     * Constructs a new NormalPlatform with specified position, dimensions and slope.
     * 
     * @param position the 2D position of the platform (cannot be null)
     * @param dimension the 2D dimensions of the platform (cannot be null)
     * @throws NullPointerException if any parameter is null
     */
    public NormalPlatform(final Position2D position, final BoundingBox2D dimension) {
        super(position, dimension);
    }
}
