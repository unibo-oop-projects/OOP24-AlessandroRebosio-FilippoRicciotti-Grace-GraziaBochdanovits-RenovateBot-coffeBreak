package it.unibo.coffebreak.impl.model.physics;

import java.util.Objects;

import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.PhysicsEntity;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.model.physics.Physics;
import it.unibo.coffebreak.api.model.physics.PhysicsEngine;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.common.Vector;

/**
 * Unified physics engine implementation that coordinates physics calculations
 * and collision detection.
 * <p>
 * This class provides a centralized system for managing both physics and
 * collision
 * systems, ensuring consistent behavior across all entities while maintaining
 * separation of concerns internally.
 * </p>
 * 
 * <h3>Key Features:</h3>
 * <ul>
 * <li><strong>Unified Interface:</strong> Single point of contact for physics
 * and collision</li>
 * <li><strong>Consistent Behavior:</strong> All entities follow the same
 * physics rules</li>
 * <li><strong>Optimized Performance:</strong> Centralized collision detection
 * reduces redundant checks</li>
 * <li><strong>Extensible Design:</strong> Easy to add new physics or collision
 * behaviors</li>
 * </ul>
 * 
 * @author Alessandro Rebosio
 */
public class GamePhysicsEngine implements PhysicsEngine {

    private static final String ENTITY_NULL = "Entity cannot be null";

    private final Physics physics = new GamePhysics();

    /**
     * Creates a new physics engine with default physics configuration.
     */
    public GamePhysicsEngine() {
        // Physics constants are now embedded in the class
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEntity(final Entity entity, final Model model, final float deltaTime) {
        Objects.requireNonNull(entity, ENTITY_NULL);
        Objects.requireNonNull(model, "Model cannot be null");

        if (deltaTime < 0) {
            throw new IllegalArgumentException("Delta time cannot be negative");
        }

        final boolean wasOnPlatform = this.isOnPlatform(entity, model);

        if (!wasOnPlatform) {
            this.applyGravity(entity, deltaTime);
        } else {
            if (entity.getVelocity() != null) {
                final Vector currentVelocity = entity.getVelocity();
                if (currentVelocity.y() > 0) {
                    entity.setVelocity(new Vector(currentVelocity.x(), 0f));
                }
            }
        }

        this.applyMovement(entity, deltaTime);
        this.handleCollisions(entity, model);

        final boolean isOnPlatformNow = this.isOnPlatform(entity, model);
        if (entity instanceof PhysicsEntity physicsEntity) {
            if (!wasOnPlatform && isOnPlatformNow) {
                physicsEntity.onPlatformLand();
            } else if (wasOnPlatform && !isOnPlatformNow) {
                physicsEntity.onPlatformLeave();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyGravity(final Entity entity, final float deltaTime) {
        Objects.requireNonNull(entity, ENTITY_NULL);
        if (deltaTime < 0) {
            throw new IllegalArgumentException("Delta time cannot be negative");
        }

        if (entity.getVelocity() != null) {
            final boolean affectedByGravity = !(entity instanceof PhysicsEntity)
                    || ((PhysicsEntity) entity).isAffectedByGravity();

            if (affectedByGravity) {
                final Vector currentVelocity = entity.getVelocity();
                final Vector gravityVector = this.physics.gravity().mul(deltaTime);

                float newVerticalVelocity = currentVelocity.y() + gravityVector.y();

                final float maxFallingSpeed = entity instanceof PhysicsEntity
                        ? ((PhysicsEntity) entity).getMaxFallingSpeed()
                        : 200f;
                newVerticalVelocity = Math.min(newVerticalVelocity, maxFallingSpeed);

                entity.setVelocity(new Vector(currentVelocity.x(), newVerticalVelocity));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyMovement(final Entity entity, final float deltaTime) {
        Objects.requireNonNull(entity, ENTITY_NULL);
        if (deltaTime < 0) {
            throw new IllegalArgumentException("Delta time cannot be negative");
        }

        final Vector velocity = entity.getVelocity();
        if (velocity != null && (velocity.x() != 0.0f || velocity.y() != 0.0f)) {
            final Position currentPosition = entity.getPosition();
            final Vector displacement = new Vector(velocity.x() * deltaTime, velocity.y() * deltaTime);
            entity.setPosition(currentPosition.sum(displacement));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCollisions(final Entity entity, final Model model) {
        Objects.requireNonNull(entity, ENTITY_NULL);
        Objects.requireNonNull(model, "Model cannot be null");

        this.handleWorldBoundaryCollisions(entity, model);

        model.getEntities().stream()
                .filter(other -> !other.equals(entity))
                .filter(entity::collidesWith)
                .forEach(other -> {
                    entity.onCollision(other);
                    other.onCollision(entity);
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOnPlatform(final Entity entity, final Model model) {
        Objects.requireNonNull(entity, ENTITY_NULL);
        Objects.requireNonNull(model, "Model cannot be null");

        return model.getEntities().stream()
                .filter(Platform.class::isInstance)
                .map(Platform.class::cast)
                .anyMatch(platform -> entity.collidesWith(platform)
                        && platform.getCollisionSide(entity) == Platform.CollisionSide.TOP);
    }

    /**
     * Handles collisions between an entity and the world boundaries.
     * <p>
     * This method ensures entities stay within the game world bounds by
     * clamping their position to the valid area.
     * </p>
     * 
     * @param entity the entity to check
     * @param model  the game model containing world boundaries
     */
    private void handleWorldBoundaryCollisions(final Entity entity, final Model model) {
        final Position current = entity.getPosition();
        final BoundigBox bounds = model.getGameBound();
        final BoundigBox size = entity.getDimension();

        final float newX = Math.max(0, Math.min(current.x(), bounds.width() - size.width()));
        final float newY = Math.max(0, Math.min(current.y(), bounds.height() - size.height()));

        entity.setPosition(new Position(newX, newY));
    }
}
