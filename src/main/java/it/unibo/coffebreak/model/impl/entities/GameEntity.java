package it.unibo.coffebreak.model.impl.entities;

import java.util.Objects;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.impl.util.Dimension2D;
import it.unibo.coffebreak.model.impl.util.Position2D;
import it.unibo.coffebreak.model.impl.util.Vector2D;

/**
 * An abstract base class for all game entities, providing common functionality
 * for position, dimension, velocity and facing direction management.
 * This class implements the {@link Entity} interface and serves as the foundation
 * for both static and dynamic game objects.
 * 
 * @see Entity
 * @author Grazia Bochdanovits de Kavna
 */
public abstract class GameEntity implements Entity {

    private Position2D position;
    private final Dimension2D dimension;
    private Vector2D velocity;
    private boolean isFacingRight;

    /**
     * Constructs a new game entity with the specified position and dimensions.
     *
     * @param position the initial position of the entity (cannot be {@code null})
     * @param dimension the dimensions of the entity (cannot be {@code null})
     * @throws NullPointerException if either position or dimension is {@code null}
     */
    public GameEntity(final Position2D position, final Dimension2D dimension) {
        this.position = Objects.requireNonNull(position, "Position cannot be null");
        this.dimension = Objects.requireNonNull(dimension, "Dimension cannot be null");
        this.velocity = new Vector2D(0, 0);
        this.isFacingRight = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension2D getDimension() {
        return this.dimension;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position2D getPosition() {
        return this.position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2D getVelocity() {
        return new Vector2D(velocity.getX(), velocity.getY());
    }

    /**
     * Checks if this entity intersects with another entity.
     *
     * @param entity the entity to check for intersection with
     * @return {@code false} (to be implemented in subclasses)
     */
    @Override
    public boolean intersect(final Entity entity) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFacingRight() {
        return isFacingRight;
    }

    /**
     * Sets the facing direction of the entity.
     *
     * @param facing {@code true} to make the entity face right,
     *               {@code false} to make it face left
     */
    @Override
    public void setFacingRight(final boolean facing) {
        this.isFacingRight = facing; 
    }

    /**
     * Sets the position of the entity.
     *
     * @param position the new position (cannot be {@code null})
     * @throws NullPointerException if position is {@code null}
     */
    @Override
    public void setPosition(final Position2D position) {
        this.position = new Position2D(position.x(), position.y());
    }

    /**
     * Sets the velocity of the entity.
     *
     * @param vector the new velocity vector
     */
    @Override
    public void setVelocity(final Vector2D vector) {
        this.velocity = new Vector2D(vector.getX(), vector.getY());
    }
}
