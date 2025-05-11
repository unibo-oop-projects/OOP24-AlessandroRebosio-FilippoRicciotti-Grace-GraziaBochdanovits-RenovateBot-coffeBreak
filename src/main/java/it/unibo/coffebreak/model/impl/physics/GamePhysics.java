package it.unibo.coffebreak.model.impl.physics;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.physics.Physics;
import it.unibo.coffebreak.model.impl.util.Position2D;
import it.unibo.coffebreak.model.impl.util.Vector2D;

public record GamePhysics(float acceleration, float maxSpeed, float deceleration, float gravity) implements Physics {

    @Override
    public void updateMovement(final Entity entity, final float deltaTime, final Direction direction) {
        float vX = entity.getVelocity().getX();
        final float vY = entity.getVelocity().getY() + gravity * deltaTime;

        switch (direction) {
            case RIGHT:
                vX += this.acceleration * deltaTime;
                entity.setFacingRight(true);
                break;
            case LEFT:
                vX -= this.acceleration * deltaTime;
                entity.setFacingRight(false);
                break;
            default:
                vX = applyDeceleration(vX, deltaTime);
        }

        vX = clamp(vX);
        entity.setVelocity(new Vector2D(vX, vY));
        entity.setPosition(new Position2D(entity.getPosition().x() + vX * deltaTime,
                entity.getPosition().y() + vY * deltaTime));
    }

    private float applyDeceleration(final float currentVx, final float deltaTime) {
        if (currentVx > 0) {
            return Math.max(0, currentVx - this.deceleration * deltaTime);
        } else if (currentVx < 0) {
            return Math.min(0, currentVx + this.deceleration * deltaTime);
        }
        return 0;
    }

    private float clamp(final float value) {
        return Math.max(-maxSpeed, Math.min(maxSpeed, value));
    }
}
