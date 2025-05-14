package it.unibo.coffebreak.model.impl.physics;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.physics.Physics;
import it.unibo.coffebreak.model.impl.common.Vector2D;

/**
 * Implementation of {@link Physics} that handles 2D game physics calculations.
 * This record provides concrete physics computations for game entities
 * including:
 * <ul>
 * <li>Movement with acceleration </li>
 * <li>Gravity application</li>
 * </ul>
 *
 * <p>
 * The physics system uses frame-rate independent calculations based on delta
 * time.
 * 
 * @author Alessandro Rebosio
 */
public record GamePhysics() implements Physics {

    private static final float GRAVITY = 9.81f;
    private static final float BASE_SPEED = 0.1f;
    private static final float JUMP_FORCE = 10f;
    private static final Vector2D ZERO_VECTOR = new Vector2D(0f, 0f);

    @Override
    public Vector2D calculateX(final float deltaTime, final Command command) {
        return switch (command) {
            case MOVE_RIGHT -> horizontalMovement(deltaTime, BASE_SPEED);
            case MOVE_LEFT -> horizontalMovement(deltaTime, -BASE_SPEED);
            default -> ZERO_VECTOR;
        };
    }

    @Override
    public Vector2D calculateY(final float deltaTime, final Command command) {
        return switch (command) {
            case MOVE_UP -> verticalMovement(deltaTime, -BASE_SPEED);
            case MOVE_DOWN -> verticalMovement(deltaTime, BASE_SPEED);
            case JUMP -> verticalMovement(deltaTime, JUMP_FORCE);
            default -> applyGravity(deltaTime);
        };
    }

    private Vector2D horizontalMovement(final float deltaTime, final float speed) {
        return new Vector2D(speed, 0f).multiply(deltaTime);
    }

    private Vector2D verticalMovement(final float deltaTime, final float speed) {
        return new Vector2D(0f, speed).multiply(deltaTime);
    }

    private Vector2D applyGravity(final float deltaTime) {
        return new Vector2D(0f, -GRAVITY).multiply(deltaTime);
    }
}
