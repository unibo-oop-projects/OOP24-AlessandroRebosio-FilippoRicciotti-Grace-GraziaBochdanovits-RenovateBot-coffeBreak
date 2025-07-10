package it.unibo.coffebreak.impl.model.entities.enemy.barrel;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.PhysicsEntity;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.common.Vector;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;
import it.unibo.coffebreak.impl.model.entities.enemy.AbstractEnemy;

/**
 * Concrete implementation of a rolling barrel enemy in the game world.
 * <p>
 * This class represents the barrel that rolls along platforms with specific
 * movement behavior. The barrel maintains direction-based movement and
 * can be destroyed or transformed into fire.
 * </p>
 *
 * <h3>Movement Behavior:</h3>
 * <ul>
 * <li>Starts moving right at constant speed ({@value #BARREL_SPEED})</li>
 * <li>Only moves horizontally when on a platform</li>
 * <li>Has no horizontal movement when falling/in air</li>
 * <li>Inverts direction only once each time it lands after a fall</li>
 * <li>Affected by game physics (gravity)</li>
 * </ul>
 *
 * @see Barrel
 * @see AbstractEntity
 * @author Grazia Bochdanovits de Kavna
 */
public class GameBarrel extends AbstractEnemy implements Barrel, PhysicsEntity {

    // Physics constants
    private static final float BARREL_SPEED = 20f;

    private final boolean canTransformToFire;
    private boolean isDestroyedByTank;
    private boolean onPlatform;
    private boolean hasFallen;
    private boolean movingRight = true;

    /**
     * Constructs a new game barrel with specified properties.
     *
     * @param position           the initial position of the barrel (cannot be null)
     * @param dimension          the initial dimension of the barrel (cannot be
     *                           null)
     * @param canTransformToFire whether the barrel can turn into fire when
     *                           destroyed
     * @throws NullPointerException if position, dimension or physics are null
     */
    public GameBarrel(final Position position, final BoundigBox dimension, final boolean canTransformToFire) {
        super(position, dimension);
        this.canTransformToFire = canTransformToFire;

        this.onPlatform = true;
        this.setVelocity(new Vector(BARREL_SPEED, 0f));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Barrel movement logic:
     * - Only moves horizontally when on a platform
     * - When falling, has no horizontal movement
     * - Maintains current direction when on platform
     * </p>
     */
    @Override
    public void update(final float deltaTime) {
        final Vector currentVelocity = this.getVelocity();

        if (this.onPlatform) {
            final float horizontalSpeed = this.movingRight ? BARREL_SPEED : -BARREL_SPEED;
            this.setVelocity(new Vector(horizontalSpeed, currentVelocity.y()));
        } else {
            this.setVelocity(new Vector(0f, currentVelocity.y()));
        }
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
        switch (other) {
            case final Tank tank -> {
                this.isDestroyedByTank = true;
                this.destroy();
            }
            case final Platform platform -> this.onPlatformLand();
            default -> {
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * When landing on a platform, inverts direction if the barrel has fallen.
     * </p>
     */
    @Override
    public void onPlatformLand() {
        if (this.hasFallen) {
            this.movingRight = !this.movingRight;
            this.hasFallen = false;
        }

        this.onPlatform = true;
    }

    /**
     * {@inheritDoc}
     * <p>
     * When leaving a platform, marks that the barrel has fallen.
     * </p>
     */
    @Override
    public void onPlatformLeave() {
        this.onPlatform = false;
        this.hasFallen = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getMaxFallingSpeed() {
        return 100f; // Barrels fall slower than Mario
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
