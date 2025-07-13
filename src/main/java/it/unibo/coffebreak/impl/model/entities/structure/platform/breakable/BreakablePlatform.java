package it.unibo.coffebreak.impl.model.entities.structure.platform.breakable;

import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
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

    private boolean broken;

    /**
     * Constructs a new breakable platform with specified position, dimensions and
     * slope.
     *
     * @param position  the 2D position of the platform in game world
     *                  coordinates
     *                  (cannot be null)
     * @param dimension the 2D dimension of the platform (cannot be null)
     */
    public BreakablePlatform(final Position position, final BoundigBox dimension) {
        super(position, dimension);
    }

    /**
     * Constructs a new Platform with specified position, dimensions and canGoDown property.
     * 
     * @param position   the 2D position of the platform (cannot be null)
     * @param dimension  the 2D dimension of the platform (cannot be null)
     * @param canGoDown  true if entities (e.g., Mario) can go down through this platform
     */
    public BreakablePlatform(final Position position, final BoundigBox dimension, final boolean canGoDown) {
        super(position, dimension, canGoDown);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        this.broken = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBroken() {
        return broken;
    }
}
