package it.unibo.coffebreak.impl.model.entities.structure.platform.breakable;

import it.unibo.coffebreak.impl.common.BoundingBox2D;
import it.unibo.coffebreak.impl.common.Position2D;
import it.unibo.coffebreak.impl.model.entities.structure.platform.AbstractPlatform;

/**
 * Implementation of a breakable platform in the game world.
 * <p>
 * This platform type can be destroyed or broken under certain game conditions,
 * though the current implementation marks it as unbreakable ({@code canBreak()}
 * returns false).
 * Designed to be extended for specific breakable platform behaviors.
 * </p>
 * 
 * @see AbstractPlatform
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class BreakablePlatform extends AbstractPlatform {

    private boolean isBroken;

    /**
     * Constructs a new breakable platform with specified position, dimensions and
     * slope.
     *
     * @param position  the 2D position of the platform in game world coordinates
     *                  (cannot be null)
     * @param dimension the physical dimensions of the platform (cannot be null)
     * @throws NullPointerException if any parameter is null
     */
    public BreakablePlatform(final Position2D position, final BoundingBox2D dimension) {
        super(position, dimension);
        this.isBroken = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        this.isBroken = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBroken() {
        return this.isBroken;
    }
}
