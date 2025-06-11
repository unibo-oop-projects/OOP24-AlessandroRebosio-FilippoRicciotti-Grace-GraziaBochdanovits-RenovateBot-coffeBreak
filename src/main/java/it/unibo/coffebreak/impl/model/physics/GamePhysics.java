package it.unibo.coffebreak.impl.model.physics;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.physics.Physics;
import it.unibo.coffebreak.impl.common.Vector;

/**
 * Implementation of the Physics interface for a game environment.
 * Handles basic 2D movement physics including gravity, jumping,
 * and horizontal movement with constant speed.
 * 
 * @author Alessandro Rebosio
 */
public record GamePhysics() implements Physics {

    private static final float BASE_SPEED = 15f;
    private static final float JUMP_FORCE = 10f;

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector calculateX(final Command command) {
        return switch (command) {
            case MOVE_RIGHT -> horizontalMovement(BASE_SPEED);
            case MOVE_LEFT -> horizontalMovement(-BASE_SPEED);
            default -> new Vector();
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector calculateY(final Command command) {
        return switch (command) {
            case MOVE_UP -> verticalMovement(BASE_SPEED);
            case MOVE_DOWN -> verticalMovement(-BASE_SPEED);
            case JUMP -> verticalMovement(JUMP_FORCE);
            default -> verticalMovement(-BASE_SPEED);
        };
    }

    /**
     * Creates a horizontal movement vector.
     * 
     * @param speed the speed of movement (positive for right, negative for
     *              left)
     * @return a Vector2D representing horizontal movement
     */
    private Vector horizontalMovement(final float speed) {
        return new Vector(speed, 0f);
    }

    /**
     * Creates a vertical movement vector.
     * 
     * @param speed the speed of movement (positive for down, negative for up)
     * @return a Vector2D representing vertical movement
     */
    private Vector verticalMovement(final float speed) {
        return new Vector(0f, speed);
    }
}
