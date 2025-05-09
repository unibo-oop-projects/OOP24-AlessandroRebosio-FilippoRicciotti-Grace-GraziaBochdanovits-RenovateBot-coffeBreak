package it.unibo.coffebreak.model.api.entity;

import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;

/**
 * Represents a fundamental game entity with spatial properties and update capability.
 * Implementations can represent both static and dynamic game elements.
 *
 * @see Position
 * @see Dimension
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface Entity {

    /**
     * Gets the current position of this entity in game world coordinates.
     *
     * @return the current {@link Position} of this entity (never {@code null})
     * @see Position
     */
    Position getPosition();

    /**
     * Sets the current position of the Enity.
     * 
     * @param position the new position
     */
    void setPosition(Position position);

    /**
     * Gets the physical dimensions of this entity.
     *
     * @return the {@link Dimension} of this entity (never {@code null})
     * @see Dimension
     */
    Dimension getDimension();

    /**
     * Updates the entity's state based on elapsed game time.
     *
     * @param deltaTime the time elapsed since last update in milliseconds 
     */
    void update(long deltaTime);


    /**
     * Method that checks if Entity is colliding with another Entity.
     * 
     * @param other the other Entity
     */
    void onCollision(Entity other);
}
