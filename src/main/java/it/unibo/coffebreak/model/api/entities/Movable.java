package it.unibo.coffebreak.model.api.entities;

/**
 * Represents an entity that can perform movement.
 * Implementing classes must define their specific movement behavior.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface Movable {

    /**
     * Updates the entity's position based on its movement logic.
     * Implementations should calculate the new position according to:
     * - The entity's movement characteristics (speed, direction, etc.)
     * - The elapsed time since the last update
     * 
     * @param deltaTime the time elapsed since the last update (in seconds)
     * @throws IllegalArgumentException if deltaTime is negative
     */
    void update(float deltaTime);
}
