package it.unibo.coffebreak.api.model.physics;

import it.unibo.coffebreak.impl.common.Vector;

/**
 * Represents the physics system for game entities in a 2D space.
 * This interface defines the contract for physics calculations including
 * movement and gravity effects on game entities.
 * 
 * @author Alessandro Rebosio
 */
public interface Physics {
    /**
     * Returns the horizontal velocity vector for moving right, scaled by deltaTime.
     * 
     * @return the velocity vector for right movement
     */
    Vector moveRight();

    /**
     * Returns the horizontal velocity vector for moving left, scaled by deltaTime.
     *
     * @return the velocity vector for left movement
     */
    Vector moveLeft();

    /**
     * Returns the vertical velocity vector for moving up, scaled by deltaTime.
     *
     * @return the velocity vector for upward movement
     */
    Vector moveUp();

    /**
     * Returns the vertical velocity vector for moving down, scaled by deltaTime.
     *
     * @return the velocity vector for downward movement
     */
    Vector moveDown();

    /**
     * Returns the vertical velocity vector for jumping, scaled by deltaTime.
     *
     * @return the velocity vector for jumping
     */
    Vector jump();

    /**
     * Returns the acceleration vector due to gravity, scaled by deltaTime.
     *
     * @return the gravity vector
     */
    Vector gravity();
}
