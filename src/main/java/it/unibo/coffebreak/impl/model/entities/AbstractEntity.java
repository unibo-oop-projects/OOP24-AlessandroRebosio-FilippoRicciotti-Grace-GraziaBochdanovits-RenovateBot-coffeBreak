package it.unibo.coffebreak.impl.model.entities;

import java.util.Objects;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.common.Vector;

/**
 * An abstract base class for all game entities, providing common functionality
 * for position, dimension, velocity and facing direction management.
 * This class implements the {@link Entity} interface and serves as the
 * foundation
 * for both static and dynamic game objects.
 * 
 * @see Entity
 * @author Grazia Bochdanovits de Kavna
 */
public abstract class AbstractEntity implements Entity {

    private BoundigBox dimension;
    private Position position;
    private Vector velocity;

    /**
     * Constructs a new game entity with the specified position and dimension.
     *
     * @param position  the initial position of the entity (not null)
     * @param dimension the size of the entity (not null)
     * @throws NullPointerException if either argument is null
     */
    public AbstractEntity(final Position position, final BoundigBox dimension) {
        this.dimension = Objects.requireNonNull(dimension, "Dimension cannot be null");
        this.position = Objects.requireNonNull(position, "Position cannot be null");

        this.velocity = new Vector();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDimension(final BoundigBox dimension) {
        this.dimension = dimension.copy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BoundigBox getDimension() {
        return this.dimension;
    }

    /**
     * Sets the position of the entity.
     *
     * @param position the new position (cannot be {@code null})
     * @throws NullPointerException if position is {@code null}
     */
    @Override
    public void setPosition(final Position position) {
        this.position = position.copy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getPosition() {
        return this.position;
    }

    /**
     * Sets the velocity of the entity.
     *
     * @param vector the new velocity vector
     */
    @Override
    public final void setVelocity(final Vector vector) {
        this.velocity = vector.copy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector getVelocity() {
        return this.velocity;
    }

    /**
     * Checks if this entity intersects with another entity.
     *
     * @param entity the entity to check for intersection with
     * @return {@code false} (to be implemented in subclasses)
     */
    @Override
    public boolean collidesWith(final Entity entity) {
        return this.getPosition().x() < entity.getPosition().x() + entity.getDimension().width()
                && this.getPosition().x() + this.getDimension().width() > entity.getPosition().x()
                && this.getPosition().y() < entity.getPosition().y() + entity.getDimension().height()
                && this.getPosition().y() + this.getDimension().height() > entity.getPosition().y();
    }

    /**
     * Called when this entity collides with another entity.
     * This method should be implemented by subclasses to define specific collision
     * behavior.
     *
     * @param other the entity that this entity collided with
     */
    @Override
    public abstract void onCollision(Entity other);
}
