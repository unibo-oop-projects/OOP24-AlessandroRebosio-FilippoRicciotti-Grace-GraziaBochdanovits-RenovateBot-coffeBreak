package it.unibo.coffebreak.impl.model.physics;

import it.unibo.coffebreak.api.model.physics.Physics;
import it.unibo.coffebreak.impl.common.Vector;

/**
 * Provides basic 2D physics calculations for a platformer game.
 * Includes gravity, jumping, and constant-speed horizontal movement.
 * All methods return a velocity or acceleration vector scaled by deltaTime.
 * 
 * Usage example:
 * 
 * <pre>
 * GamePhysics physics = new GamePhysics();
 * Vector velocity = physics.moveRight(deltaTime);
 * velocity = velocity.sum(physics.gravity(deltaTime));
 * </pre>
 * 
 * @author Alessandro Rebosio
 */
public class GamePhysics implements Physics {

    private static final float BASE_SPEED = 100;
    private static final float JUMP_FORCE = 200f;
    private static final float GRAVITY = -25f;

    /**
     * Returns the horizontal velocity vector for moving right, scaled by deltaTime.
     *
     * @param deltaTime the elapsed time in seconds
     * @return the velocity vector for right movement
     */
    @Override
    public Vector moveRight(final float deltaTime) {
        return new Vector(BASE_SPEED, 0f).mul(deltaTime);
    }

    /**
     * Returns the horizontal velocity vector for moving left, scaled by deltaTime.
     *
     * @param deltaTime the elapsed time in seconds
     * @return the velocity vector for left movement
     */
    @Override
    public Vector moveLeft(final float deltaTime) {
        return new Vector(-BASE_SPEED, 0f).mul(deltaTime);
    }

    /**
     * Returns the vertical velocity vector for moving up, scaled by deltaTime.
     *
     * @param deltaTime the elapsed time in seconds
     * @return the velocity vector for upward movement
     */
    @Override
    public Vector moveUp(final float deltaTime) {
        return new Vector(0f, BASE_SPEED).mul(deltaTime);
    }

    /**
     * Returns the vertical velocity vector for moving down, scaled by deltaTime.
     *
     * @param deltaTime the elapsed time in seconds
     * @return the velocity vector for downward movement
     */
    @Override
    public Vector moveDown(final float deltaTime) {
        return new Vector(0f, -BASE_SPEED).mul(deltaTime);
    }

    /**
     * Returns the vertical velocity vector for jumping, scaled by deltaTime.
     *
     * @param deltaTime the elapsed time in seconds
     * @return the velocity vector for jumping
     */
    @Override
    public Vector jump(final float deltaTime) {
        return new Vector(0f, JUMP_FORCE).mul(deltaTime);
    }

    /**
     * Returns the acceleration vector due to gravity, scaled by deltaTime.
     *
     * @param deltaTime the elapsed time in seconds
     * @return the gravity vector
     */
    @Override
    public Vector gravity(final float deltaTime) {
        return new Vector(0f, -GRAVITY).mul(deltaTime);
    }
}
