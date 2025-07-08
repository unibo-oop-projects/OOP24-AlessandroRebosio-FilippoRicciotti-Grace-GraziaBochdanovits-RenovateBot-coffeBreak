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

    private static final float GRAVITY = -50f;

    /**
     * Default constructor with standard speed and jump force.
     */
    public GamePhysics() {
        this(-GRAVITY, GRAVITY * 2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector moveRight(final float deltaTime) {
        return new Vector(this.speed, 0f).mul(deltaTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector moveLeft(final float deltaTime) {
        return new Vector(-this.speed, 0f).mul(deltaTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector moveUp(final float deltaTime) {
        return new Vector(0f, -this.speed).mul(deltaTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector moveDown(final float deltaTime) {
        return new Vector(0f, +this.speed).mul(deltaTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector jump(final float deltaTime) {
        return new Vector(0f, this.jumpForce).mul(deltaTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector gravity(final float deltaTime) {
        return new Vector(0f, -GRAVITY).mul(deltaTime);
    }
}
