package it.unibo.coffebreak.impl.model.entities.enemy.barrel;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.Movable;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.api.model.physics.Physics;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;
import it.unibo.coffebreak.impl.model.entities.enemy.AbstractEnemy;
import it.unibo.coffebreak.impl.model.physics.GamePhysics;

/**
 * Concrete implementation of a rolling barrel enemy in the game world.
 * <p>
 * This class represents the barrel that rolls along platforms,
 * changing direction based on platform slopes. The barrel maintains a constant
 * speed
 * throughout its movement and can be destroyed or transformed into fire.
 * </p>
 *
 * <h3>Movement Behavior:</h3>
 * <ul>
 * <li>Rolls at constant speed ({@value #BARREL_SPEED})</li>
 * <li>Follows platform slope directions (LEFT/RIGHT)</li>
 * <li>Uses flat platform's opposite previous slope when on FLAT surfaces</li>
 * <li>Affected by game physics (gravity)</li>
 * </ul>
 *
 * @see Barrel
 * @see AbstractEntity
 * @author Grazia Bochdanovits de Kavna
 */
public class GameBarrel extends AbstractEnemy implements Barrel, Movable {

    private final Physics physics;

    private final boolean canTransformToFire;
    private boolean isDestroyedByTank;
    private boolean onPlatform;

    private Command direction = Command.MOVE_LEFT;

    /**
     * Constructs a new game barrel with specified properties.
     *
     * @param position           the initial position of the barrel (cannot be null)
     * @param canTransformToFire whether the barrel can turn into fire when
     *                           destroyed
     * @throws NullPointerException if position, dimension or physics are null
     */
    public GameBarrel(final Position position, final boolean canTransformToFire) {
        super(position);

        this.physics = new GamePhysics();

        this.canTransformToFire = canTransformToFire;
        this.isDestroyedByTank = false;
        this.onPlatform = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final float deltaTime) {
        final float vx = switch (this.direction) {
            case MOVE_RIGHT -> this.physics.moveRight(deltaTime).x();
            case MOVE_LEFT -> this.physics.moveLeft(deltaTime).x();
            default -> 0f;
        };

        float vy = physics.gravity(deltaTime).y();

        if (this.onPlatform) {
            vy = 0f;
        }

        final Position newPos = new Position(
                super.getPosition().x() + vx,
                super.getPosition().y() + vy);

        super.setPosition(newPos);

        this.onPlatform = false;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Handles collisions with platforms to update the barrel's rolling direction.
     * When colliding with an inclined platform, updates the current slope
     * direction.
     * </p>
     *
     * @param other the entity this barrel collided with
     */
    @Override
    public void onCollision(final Entity other) {
        direction = Command.MOVE_RIGHT;
        switch (other) {
            case final Tank tank -> {
                this.isDestroyedByTank = true;
                this.destroy();
            }
            case final Platform platform -> {
                if (super.getPosition().y() + super.getDimension().height() <= platform.getPosition().y()
                        + platform.getDimension().height()) {
                    this.onPlatform = true;
                }
            }
            default -> {
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return true if the barrel can transform into fire upon destruction
     */
    @Override
    public boolean canTransformToFire() {
        return this.canTransformToFire && this.isDestroyedByTank;
    }
}
