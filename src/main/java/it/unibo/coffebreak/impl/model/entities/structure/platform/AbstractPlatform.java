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

    private static final float EPSILON = 0.001f;

    /**
     * Constructs a new Platform with specified position, dimensions and slope.
     * 
     * @param position  the 2D position of the platform (cannot be null)
     * @param dimension the 2D dimension of the platform (cannot be null)
     */
    public AbstractPlatform(final Position position, final BoundigBox dimension) {
        super(position, dimension);
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
    public CollisionSide getCollisionSide(final Entity other) {
        final float platformTop = this.getPosition().y();
        final float platformBottom = platformTop + this.getDimension().height();
        final float platformLeft = this.getPosition().x();
        final float platformRight = platformLeft + this.getDimension().width();

        final float otherTop = other.getPosition().y();
        final float otherBottom = otherTop + other.getDimension().height();
        final float otherLeft = other.getPosition().x();
        final float otherRight = otherLeft + other.getDimension().width();

        final float overlapTop = otherBottom - platformTop;
        final float overlapBottom = platformBottom - otherTop;
        final float overlapLeft = otherRight - platformLeft;
        final float overlapRight = platformRight - otherLeft;

        final float minOverlap = Math.min(Math.min(overlapTop, overlapBottom),
                Math.min(overlapLeft, overlapRight));

        if (Math.abs(minOverlap - overlapTop) < EPSILON) {
            return CollisionSide.TOP;
        }
        if (Math.abs(minOverlap - overlapBottom) < EPSILON) {
            return CollisionSide.BOTTOM;
        }
        if (Math.abs(minOverlap - overlapLeft) < EPSILON) {
            return CollisionSide.LEFT;
        }
        if (Math.abs(minOverlap - overlapRight) < EPSILON) {
            return CollisionSide.RIGHT;
        }

        return CollisionSide.NONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        // Default empty implementation
    }
}
