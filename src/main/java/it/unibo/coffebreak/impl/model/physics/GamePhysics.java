package it.unibo.coffebreak.impl.model.physics;

import it.unibo.coffebreak.api.model.physics.Physics;
import it.unibo.coffebreak.impl.common.Vector;

/**
 * Provides basic 2D physics calculations for a platformer game.
 * Includes gravity, jumping, and constant-speed horizontal movement.
 * All methods return a velocity or acceleration vector scaled by deltaTime.
 * 
 * @param speed
 * @param jumpForce
 * 
 * @author Alessandro Rebosio
 */
public record GamePhysics(float speed, float jumpForce) implements Physics {

    private static final float GRAVITY = 70; // Positive value, applied downward
    private static final float DEFAULT_SPEED = 20; // Reduced speed for smoother movement
    private static final float DEFAULT_JUMP_FORCE = 40; // Positive jump force

    /**
     * Default constructor with standard speed and jump force.
     */
    public GamePhysics() {
        this(DEFAULT_SPEED, DEFAULT_JUMP_FORCE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector moveRight() {
        return new Vector(this.speed, 0f);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector moveLeft() {
        return new Vector(-this.speed, 0f);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector moveUp() {
        return new Vector(0f, -this.speed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector moveDown() {
        return new Vector(0f, +this.speed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector jump() {
        return new Vector(0f, -this.jumpForce);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector gravity() {
        return new Vector(0f, GRAVITY);
    }
}
