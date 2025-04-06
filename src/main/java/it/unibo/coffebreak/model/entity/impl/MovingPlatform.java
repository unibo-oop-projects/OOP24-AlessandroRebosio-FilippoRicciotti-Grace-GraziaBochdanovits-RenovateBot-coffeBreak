package it.unibo.coffebreak.model.entity.impl;

import it.unibo.coffebreak.model.entity.api.Platform;
import it.unibo.coffebreak.model.utility.Vector2D;

/**
 * Represents a moving platform in the game.
 * This class extends the PlatformDecorator to provide a platform that can move with a specified velocity.
 */
public class MovingPlatform extends PlatformDecorator {

    private final Vector2D velocity;
    private static final float FRICTION = 0.7f; 

    /**
     * Constructs a MovingPlatform with the specified base platform and velocity.
     *
     * @param basePlatform the base platform to be decorated
     * @param velocity the velocity of the moving platform
     */
    public MovingPlatform(final Platform basePlatform, final Vector2D velocity) {
        super(basePlatform);
        this.velocity = velocity;
    }

    /**
     * Gets the friction level of the platform.
     * This implementation returns a fixed friction level of 0.7.
     *
     * @return the friction level of the platform
     */
    @Override
    public float getFriction() {
        return FRICTION; 
    }

    /**
     * Gets the velocity of the moving platform.
     *
     * @return the velocity of the platform
     */
    public Vector2D getVelocity() {
        return this.velocity;
    }

}
