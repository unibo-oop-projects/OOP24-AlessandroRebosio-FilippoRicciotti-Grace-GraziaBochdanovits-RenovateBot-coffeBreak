package it.unibo.coffebreak.model.api.entities;

import it.unibo.coffebreak.model.impl.common.BoundingBox2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.common.Vector2D;

/**
 * Represents a fundamental game entity with spatial properties and update
 * capability.
 * Implementations can represent both static and dynamic game elements.
 *
 * @see Position2D
 * @see BoundingBox2D
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface Entity {
    /**
     * Gets the current position of this entity in game world coordinates.
     *
     * @return the current {@link Position2D} of this entity (never {@code null})
     */
    Position2D getPosition();

    /**
     * Sets the position of this entity in the game world.
     * The position should represent the entity's center point in world coordinates.
     *
     * @param position the new position to set (must not be {@code null})
     * @throws NullPointerException if the position parameter is null
     */
    void setPosition(Position2D position);

    /**
     * Gets the physical dimensions of this entity.
     *
     * @return the {@link BoundingBox2D} of this entity (never {@code null})
     */
    BoundingBox2D getDimension();

    /**
     * Gets the current velocity vector of this entity.
     *
     * @return the current {@link Vector2D} representing the entity's velocity
     */
    Vector2D getVelocity();

    /**
     * Sets the velocity vector of this entity.
     * The velocity determines how the entity moves during each update cycle.
     *
     * @param vector the new velocity vector to set
     */
    void setVelocity(Vector2D vector);

    /**
     * Checks if this entity intersects with another entity.
     * The intersection is typically determined by comparing the position and
     * dimensions
     * of both entities.
     *
     * @param other the entity to check for intersection with
     * @return {@code true} if this entity intersects with the given entity,
     *         {@code false} otherwise
     */
    boolean collidesWith(Entity other);

    /**
     * Handles collision detection response with another entity.
     * This method is invoked when a collision is detected between this entity and
     * another entity,
     * and should contain the logic to respond to the collision.
     * 
     * @param other the entity that collided with this entity (never {@code null})
     * 
     * @throws NullPointerException if the other parameter is null
     */
    void onCollision(Entity other);
}
