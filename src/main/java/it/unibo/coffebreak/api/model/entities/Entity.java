package it.unibo.coffebreak.api.model.entities;

import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.common.Vector;

/**
 * Represents a fundamental game entity with spatial properties and update
 * capability.
 * Implementations can represent both static and dynamic game elements.
 *
 * @see Position
 * @see BoundigBox
 * 
 * @author Grazia Bochdanovits de Kavna
 */

public interface Entity {
    /**
     * Gets the current position of this entity in game world coordinates.
     *
     * @return the current {@link Position} of this entity (never {@code null})
     */
    Position getPosition();

    /**
     * Sets the position of this entity in the game world.
     * The position should represent the entity's center point in world coordinates.
     *
     * @param position the new position to set (must not be {@code null})
     * @throws NullPointerException if the position parameter is null
     */
    void setPosition(Position position);

    /**
     * Gets the physical dimensions of this entity.
     *
     * @return the {@link BoundigBox} of this entity (never {@code null})
     */
    BoundigBox getDimension();

    /**
     * Sets the physical dimensions (bounding box) of this entity.
     * The dimension defines the size and shape of the entity in the game world.
     *
     * @param dimension the new {@link BoundigBox} to set (must not be
     *                  {@code null})
     */
    void setDimension(BoundigBox dimension);

    /**
     * Sets the velocity vector of this entity.
     * The velocity determines how the entity moves during each update cycle.
     *
     * @param vector the new velocity vector to set
     */
    void setVelocity(Vector vector);

    /**
     * Gets the current velocity vector of this entity.
     *
     * @return the current {@link Vector} representing the entity's velocity
     */
    Vector getVelocity();

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

    /**
     * Updates the entity's position based on its movement logic.
     * Implementations should calculate the new position according to:
     * - The entity's movement characteristics (speed, direction, etc.)
     * - The elapsed time since the last update
     * 
     * @param deltaTime the time elapsed since the last update (in seconds)
     */
    void update(float deltaTime);
}
