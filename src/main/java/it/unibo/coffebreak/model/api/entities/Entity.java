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
     * Gets the physical dimensions of this entity.
     *
     * @return the {@link Dimension2D} of this entity (never {@code null})
     */
    Dimension2D getDimension();

    /**
     * Updates the entity's state based on elapsed game time.
     * This method should be called periodically to update the entity's position,
     * state, or any other time-dependent properties.
     *
     * @param deltaTime the time elapsed since last update in milliseconds
     */
    void update(long deltaTime);

    /**
     * Checks if this entity intersects with another entity.
     * The intersection is typically determined by comparing the position and
     * dimensions
     * of both entities.
     *
     * @param entity the entity to check for intersection with
     * @return {@code true} if this entity intersects with the given entity,
     *         {@code false} otherwise
     */
    boolean intersect(Entity entity);

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
     * Sets the velocity vector of this entity.
     * The velocity determines how the entity moves during each update cycle.
     *
     * @param vector the new velocity vector to set
     */
    void setVelocity(Vector2D vector);

    /**
     * Gets the current velocity vector of this entity.
     *
     * @return the current {@link Vector2D} representing the entity's velocity
     */
    Vector2D getVelocity();

    /**
     * Checks if the entity is currently facing to the right.
     * This is typically used for graphical representation or movement direction.
     *
     * @return {@code true} if the entity is facing right, {@code false} otherwise
     */
    boolean isFacingRight();

    /**
     * Sets the entity's facing direction to right.
     * This should affect both the graphical representation and potentially
     * the movement direction of the entity.
     */
    void setFacingRight();
}
