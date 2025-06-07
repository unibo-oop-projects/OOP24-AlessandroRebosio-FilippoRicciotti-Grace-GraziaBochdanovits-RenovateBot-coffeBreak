package it.unibo.coffebreak.impl.model.physics;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.physics.Physics;
import it.unibo.coffebreak.impl.common.Vector2D;

/**
 * Implementation of the Physics interface for a game environment.
 * This record handles basic 2D movement physics including gravity, jumping,
 * and horizontal movement with constant speed.
 * 
 * @author Alessandro Rebosio
 */
public record GamePhysics() implements Physics {

    /** The base movement speed for horizontal and vertical movement. */
    public static final float BASE_SPEED = 0.1f;

    /** The force applied when jumping. */
    public static final float JUMP_FORCE = 10f;

    /** A zero vector used for no movement. */
    public static final Vector2D ZERO_VECTOR = new Vector2D(0f, 0f);

    /**
     * {@inheritDoc}
     * Calculates horizontal movement based on left/right commands.
     * Returns zero vector for non-horizontal movement commands.
     */
    @Override
    public Vector2D calculateX(final float deltaTime, final Command command) {
        return switch (command) {
            case MOVE_RIGHT -> horizontalMovement(deltaTime, BASE_SPEED);
            case MOVE_LEFT -> horizontalMovement(deltaTime, -BASE_SPEED);
            default -> ZERO_VECTOR;
        };
    }

    /**
     * {@inheritDoc}
     * Calculates vertical movement based on up/down/jump commands.
     * Applies gravity when no vertical movement command is given.
     */
    @Override
    public Vector2D calculateY(final float deltaTime, final Command command) {
        return switch (command) {
            case MOVE_UP -> verticalMovement(deltaTime, BASE_SPEED);
            case MOVE_DOWN -> verticalMovement(deltaTime, -BASE_SPEED);
            case JUMP -> verticalMovement(deltaTime, JUMP_FORCE);
            default -> verticalMovement(deltaTime, -BASE_SPEED);
        };
    }

    /**
     * Creates a horizontal movement vector.
     * 
     * @param deltaTime the time elapsed since last update
     * @param speed     the speed of movement (positive for right, negative for
     *                  left)
     * @return a Vector2D representing horizontal movement
     */
    private Vector2D horizontalMovement(final float deltaTime, final float speed) {
        return new Vector2D(speed, 0f).multiply(deltaTime);
    }

    /**
     * Creates a vertical movement vector.
     * 
     * @param deltaTime the time elapsed since last update
     * @param speed     the speed of movement (positive for down, negative for up)
     * @return a Vector2D representing vertical movement
     */
    private Vector2D verticalMovement(final float deltaTime, final float speed) {
        return new Vector2D(0f, speed).multiply(deltaTime);
    }
}
