package it.unibo.coffebreak.model.impl.entities.enemy.barrel;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.Movable;
import it.unibo.coffebreak.model.api.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.model.api.entities.structure.Platform;
import it.unibo.coffebreak.model.api.physics.Physics;
import it.unibo.coffebreak.model.impl.common.BoundingBox2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.common.Vector2D;
import it.unibo.coffebreak.model.impl.entities.AbstractEntity;
import it.unibo.coffebreak.model.impl.entities.enemy.AbstractEnemy;
import it.unibo.coffebreak.model.impl.entities.structure.tank.GameTank;
import it.unibo.coffebreak.model.impl.physics.GamePhysics;

/**
 * Concrete implementation of a rolling barrel enemy in the game world.
 * <p>
 * This class represents the barrel that rolls along platforms,
 * changing direction based on platform slopes. The barrel maintains a constant speed
 * throughout its movement and can be destroyed or transformed into fire.
 * </p>
 *
 * <h3>Movement Behavior:</h3>
 * <ul>
 *   <li>Rolls at constant speed ({@value #BARREL_SPEED})</li>
 *   <li>Follows platform slope directions (LEFT/RIGHT)</li>
 *   <li>Uses flat platform's opposite previous slope when on FLAT surfaces</li>
 *   <li>Affected by game physics (gravity)</li>
 * </ul>
 *
 * @see Barrel
 * @see AbstractEntity
 * @author Grazia Bochdanovits de Kavna
 */
public class GameBarrel extends AbstractEnemy implements Barrel, Movable {

    /** The constant rolling speed of the barrel. */
    private static final float BARREL_SPEED = 1.5f;

    private final Physics physics;
    private final boolean canTransformToFire;
    private Command currentDirection;
    private boolean isOnPlatform;

    /**
     * Constructs a new game barrel with specified properties.
     *
     * @param position the initial position of the barrel (cannot be null)
     * @param dimension the physical dimensions of the barrel (cannot be null)
     * @param canTransformToFire whether the barrel can turn into fire when destroyed
     * @param initialDirection the initial direction of the barrel
     * @throws NullPointerException if position, dimension or physics are null
     */
    public GameBarrel(final Position2D position, final BoundingBox2D dimension, 
                        final boolean canTransformToFire, final Command initialDirection) {
        super(position, dimension);
        this.canTransformToFire = canTransformToFire;
        this.currentDirection = initialDirection;
        this.physics = new GamePhysics();
        this.isOnPlatform = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(final float deltaTime) {
        if (isDestroyed()) {
            return;
        }
        Vector2D velocity = physics.calculateX(BARREL_SPEED * deltaTime, currentDirection);
        if (!isOnPlatform) {
            velocity = velocity.sum(physics.calculateY(deltaTime, Command.MOVE_DOWN));
        }
        setPosition(getPosition().sum(velocity));
        isOnPlatform = false;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Handles collisions with platforms to update the barrel's rolling direction.
     * When colliding with an inclined platform, updates the current slope direction.
     * </p>
     *
     * @param other the entity this barrel collided with
     */
    @Override
    public void onCollision(final Entity other) {
        if (other instanceof final Platform platform) {
            isOnPlatform = true;
            switch (platform.getSlope()) {
                case RIGHT -> currentDirection = Command.MOVE_RIGHT;
                case LEFT -> currentDirection = Command.MOVE_LEFT;
                case FLAT -> currentDirection = currentDirection.getInverseDirection();
            }
        }
        if (other instanceof GameTank) {
            destroy();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return true if the barrel can transform into fire upon destruction
     */
    @Override
    public boolean canTransformToFire() {
        return this.canTransformToFire;
    }
}
