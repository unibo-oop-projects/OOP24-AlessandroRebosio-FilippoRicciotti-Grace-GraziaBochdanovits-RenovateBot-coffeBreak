package it.unibo.coffebreak.model.api.physics;

import it.unibo.coffebreak.model.api.Model;

/**
 * Represents a collision detection mechanism for the Coffee Break game model.
 * Implementations of this interface are responsible for detecting and handling
 * collisions between game entities in the model.
 * 
 * @author Alessandro Rebosio
 */
public interface Collision {

    /**
     * Checks for collisions in the game model and triggers appropriate responses.
     * 
     * @param model the game model containing entities to check for collisions
     * @throws IllegalArgumentException if the provided model is null
     * @throws IllegalStateException    if the collision detection cannot be
     *                                  performed
     *                                  due to an invalid model state
     */
    void checkCollision(Model model);
}
