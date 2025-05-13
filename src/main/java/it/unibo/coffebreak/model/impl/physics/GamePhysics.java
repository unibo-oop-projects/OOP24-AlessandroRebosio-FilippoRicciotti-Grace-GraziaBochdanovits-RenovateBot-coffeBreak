package it.unibo.coffebreak.model.impl.physics;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.physics.Physics;
import it.unibo.coffebreak.model.impl.util.Position2D;
import it.unibo.coffebreak.model.impl.util.Vector2D;

/**
 * Implementation of the {@link Physics} interface.
 * Handles the basic 2D physics behavior for entities including acceleration,
 * gravity, deceleration, and velocity clamping.
 *
 * @param acceleration the horizontal acceleration applied when moving
 * @param maxSpeed     the maximum horizontal speed an entity can reach
 * @param deceleration the deceleration applied when no input is given
 * @param gravity      the constant vertical acceleration (e.g. downward)
 * 
 * @author Alessandro Rebosio
 */
public record GamePhysics(float acceleration, float maxSpeed, float deceleration, float gravity) implements Physics {

    private static final float CLIMB_SPEED = 10f;
    private static final float JUMP_FORCE = 15f;

    /**
     * Updates the movement of the given {@link Entity} based on input direction and
     * elapsed time.
     * Handles horizontal movement with acceleration and deceleration, vertical
     * movement with gravity, jumping, and climbing.
     * <ul>
     * <li><b>LEFT / RIGHT</b>: apply horizontal acceleration and update facing
     * direction</li>
     * <li><b>JUMP</b>: applies an upward force if the entity is on the ground</li>
     * <li><b>UP / DOWN</b>: allows vertical climbing if the entity can climb</li>
     * <li><b>NONE</b>: apply deceleration if no horizontal input</li>
     * </ul>
     *
     * @param entity    the entity to update
     * @param deltaTime time elapsed since the last update, in seconds
     * @param direction the direction of input (movement or action)
     */
    @Override
    public void updateMovement(final Entity entity, final float deltaTime, final Direction direction) {
        float vX = entity.getVelocity().getX();
        float vY = entity.getVelocity().getY();

        switch (direction) {
            case RIGHT -> {
                vX += this.acceleration * deltaTime;
                entity.setFacingRight(true);
            }
            case LEFT -> {
                vX -= this.acceleration * deltaTime;
                entity.setFacingRight(false);
            }
            case NONE -> {
                vX = applyDeceleration(vX, deltaTime);
            }
            default -> {
            }
        }

        if (entity instanceof final Character character) {
            switch (direction) {
                case JUMP -> {
                    if (character.isOnGround()) {
                        vY = -JUMP_FORCE;
                    }
                }
                case UP -> {
                    if (character.canClimb()) {
                        vY = CLIMB_SPEED;
                    }
                }
                case DOWN -> {
                    if (character.canClimb()) {
                        vY = -CLIMB_SPEED;
                    }
                }
                default -> vY += gravity * deltaTime;
            }
        } else {
            vY += gravity * deltaTime;
        }

        vX = clamp(vX);
        entity.setVelocity(new Vector2D(vX, vY));
        entity.setPosition(new Position2D(
                entity.getPosition().x() + vX * deltaTime,
                entity.getPosition().y() + vY * deltaTime));
    }

    /**
     * Applies deceleration to the current horizontal velocity, gradually reducing
     * it towards zero.
     *
     * @param currentVx the current horizontal velocity
     * @param deltaTime the time elapsed since the last update
     * @return the updated horizontal velocity after deceleration
     */
    private float applyDeceleration(final float currentVx, final float deltaTime) {
        if (currentVx > 0) {
            return Math.max(0, currentVx - this.deceleration * deltaTime);
        } else if (currentVx < 0) {
            return Math.min(0, currentVx + this.deceleration * deltaTime);
        }
        return 0;
    }

    /**
     * Clamps the given velocity to ensure it remains within the allowed maximum
     * speed bounds.
     *
     * @param value the velocity value to clamp
     * @return the clamped velocity
     */
    private float clamp(final float value) {
        return Math.max(-maxSpeed, Math.min(maxSpeed, value));
    }
}
