package it.unibo.coffebreak.model.impl.entities.enemy.barrel;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.model.api.entities.platform.Platform;
import it.unibo.coffebreak.model.api.entities.platform.Platform.Slope;
import it.unibo.coffebreak.model.api.physics.Physics;
import it.unibo.coffebreak.model.impl.common.Dimension2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.common.Vector2D;
import it.unibo.coffebreak.model.impl.entities.AbstractEntity;
import it.unibo.coffebreak.model.impl.entities.enemy.AbstractEnemy;
import it.unibo.coffebreak.model.impl.entities.tank.GameTank;
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
public class GameBarrel extends AbstractEnemy implements Barrel {

    /** The constant rolling speed of the barrel. */
    private static final float BARREL_SPEED = 1.5f;

    private final Physics physics;
    private final boolean canTransformToFire;
    private Command currentDirection = Command.MOVE_RIGHT;
    private boolean isOnPlatform;

    /**
     * Constructs a new game barrel with specified properties.
     *
     * @param position the initial position of the barrel (cannot be null)
     * @param dimension the physical dimensions of the barrel (cannot be null)
     * @param canTransformToFire whether the barrel can turn into fire when destroyed
     * @throws NullPointerException if position, dimension or physics are null
     */
    public GameBarrel(final Position2D position, final Dimension2D dimension, final boolean canTransformToFire) {
        super(position, dimension);
        this.canTransformToFire = canTransformToFire;
        this.physics = new GamePhysics();
        this.isOnPlatform = false;
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
            currentDirection = platform.getSlope() == Slope.RIGHT 
                                ? Command.MOVE_RIGHT : Command.MOVE_LEFT;
        }

        if (other instanceof GameTank) {
            destroy();
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Updates the barrel's position based on its current slope direction and game physics.
     * Combines horizontal movement (based on slope) with vertical gravity.
     * </p>
     *
     * @param deltaTime the time elapsed since last frame (in seconds)
     */
    @Override
    public void roll(final float deltaTime) {
        if (isDestroyed()) {
            return;
        }

        final Vector2D movement = calculateHorizontalMovement(deltaTime);
        final Vector2D gravity = physics.calculateY(deltaTime, 
            isOnPlatform ? Command.NONE : Command.MOVE_DOWN);

        setPosition(getPosition().sum(movement.sum(gravity)));
        isOnPlatform = false;
    }

    /**
     * Calculates horizontal movement vector based on current slope direction.
     *
     * @param deltaTime the time elapsed since last frame (in seconds)
     * @return the horizontal movement vector adjusted for barrel speed
     */
    private Vector2D calculateHorizontalMovement(final float deltaTime) {
        return physics.calculateX(deltaTime, currentDirection).multiply(BARREL_SPEED);
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
