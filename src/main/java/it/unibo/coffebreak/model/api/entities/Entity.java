package it.unibo.coffebreak.model.api.entities;

import it.unibo.coffebreak.model.impl.util.Dimension2D;
import it.unibo.coffebreak.model.impl.util.Position2D;
import it.unibo.coffebreak.model.impl.util.Vector2D;

/**
 * Represents a fundamental game entity with spatial properties and update
 * capability.
 * Implementations can represent both static and dynamic game elements.
 *
 * @see Position2D
 * @see Dimension2D
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
     * @return the {@link Dimension2D} of this entity (never {@code null})
     */
    Dimension2D getDimension();

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
     * Sets the horizontal facing direction of this entity.
     * This affects both graphical representation and potentially movement behavior.
     *
     * @param facing {@code true} to face right (positive X direction),
     *               {@code false} to face left (negative X direction)
     */
    void setFacingRight(boolean facing);

    /**
     * Checks if the entity is currently facing to the right.
     * This is typically used for graphical representation or movement direction.
     *
     * @return {@code true} if the entity is facing right, {@code false} otherwise
     */
    boolean isFacingRight();

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
    boolean intersect(Entity other);

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

    /**
     * Updates the entity's state based on elapsed game time.
     * This method should be called periodically to update the entity's position,
     * state, or any other time-dependent properties.
     *
     * @param deltaTime the time elapsed since last update in milliseconds
     */
    void update(float deltaTime);
}
