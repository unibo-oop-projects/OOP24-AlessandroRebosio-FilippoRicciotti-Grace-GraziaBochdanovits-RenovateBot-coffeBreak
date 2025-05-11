package it.unibo.coffebreak.model.api.entity;

import it.unibo.coffebreak.model.impl.util.Dimension2D;
import it.unibo.coffebreak.model.impl.util.Position2D;

/**
 * Represents a fundamental game entity with spatial properties and update capability.
 * Implementations can represent both static and dynamic game elements.
 *
 * @see Position2D
 * @see Dimension2D
 */
public interface Entity {

    /**
     * Gets the current position of this entity in game world coordinates.
     *
     * @return the current {@link Position2D} of this entity (never {@code null})
     * @see Position2D
     */
    Position2D getPosition();

    /**
     * Gets the physical dimensions of this entity.
     *
     * @return the {@link Dimension2D} of this entity (never {@code null})
     * @see Dimension2D
     */
    Dimension2D getDimension();

    /**
     * Updates the entity's state based on elapsed game time.
     *
     * @param deltaTime the time elapsed since last update in milliseconds 
     */
    void update(long deltaTime);
}
