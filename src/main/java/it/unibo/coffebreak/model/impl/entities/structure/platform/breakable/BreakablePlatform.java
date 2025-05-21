package it.unibo.coffebreak.model.impl.entities.structure.platform.breakable;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.impl.common.BoundingBox2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.structure.platform.AbstractPlatform;

/**
 * Implementation of a breakable platform in the game world.
 * <p>
 * This platform type can be destroyed or broken under certain game conditions,
 * though the current implementation marks it as unbreakable ({@code canBreak()} returns false).
 * Designed to be extended for specific breakable platform behaviors.
 * </p>
 * 
 * @see AbstractPlatform
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class BreakablePlatform extends AbstractPlatform {

    /**
     * Constructs a new breakable platform with specified position, dimensions and slope.
     *
     * @param position the 2D position of the platform in game world coordinates (cannot be null)
     * @param dimension the physical dimensions of the platform (cannot be null)
     * @param direction the inclination of the platform (cannot be null)
     * @throws NullPointerException if any parameter is null
     */
    public BreakablePlatform(final Position2D position, final BoundingBox2D dimension, final Command direction) {
        super(position, dimension, direction);
    }

    /**
     * {@inheritDoc}
     * 
     * @return {@code false} by default, indicating an unbreakable platform
     */
    @Override
    public boolean canBreak() {
        return false;
    }

}
