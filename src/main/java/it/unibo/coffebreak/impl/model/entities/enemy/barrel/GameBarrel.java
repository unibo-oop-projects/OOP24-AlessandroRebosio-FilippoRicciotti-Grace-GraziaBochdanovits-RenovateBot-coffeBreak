package it.unibo.coffebreak.impl.model.entities.enemy.barrel;

import it.unibo.coffebreak.api.model.entities.Entity;
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
 * <li>Inverts direction only once each time it lands after a fall</li>
 * <li>Affected by game physics (gravity)</li>
 * </ul>
 *
 * @see Barrel
 * @see AbstractEntity
 * @author Grazia Bochdanovits de Kavna
 */
public class GameBarrel extends AbstractEnemy implements Barrel {

    private static final int VALUE = 100;

    private static final float BARREL_SPEED = 40f;

    private final boolean canTransformToFire;
    private boolean isDestroyedByTank;
    private boolean hasFallen;

    /**
     * Constructs a new game barrel with specified properties.
     *
     * @param position           the initial position of the barrel (cannot be null)
     * @param dimension          the initial dimension of the barrel (cannot be null)
     * @param canTransformToFire whether the barrel can turn into fire when destroyed
     * @throws NullPointerException if position, dimension or physics are null
     */
    public GameBarrel(final Position position, final BoundigBox dimension, final boolean canTransformToFire) {
        super(position, dimension, VALUE);
        this.canTransformToFire = canTransformToFire;

        this.setVelocity(new Vector(BARREL_SPEED, 0f));
    }

     /**
     * {@inheritDoc}
     * <p>
     * Barrel movement logic:
     * - Only moves horizontally when on a platform
     * - Maintains current direction when on platform
     * </p>
     */
    @Override
    public void update(final float deltaTime) {
        if (isOnPlatform()) {
            setVelocity(new Vector(getHorizontalSpeed(BARREL_SPEED), getVelocity().y()));
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Handles collisions with platforms to update the barrel's rolling direction.
     * </p>
     *
     * @param other the entity this barrel collided with
     */
    @Override
    public void onCollision(final Entity other) {
        switch (other) {
            case final Tank tank -> {
                isDestroyedByTank = true;
                destroy();
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
        super.onPlatformLand();
        if (hasFallen) {
            invertDirection();
            hasFallen = false;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * When leaving a platform, marks that the barrel has fallen.
     * </p>
     */
    @Override
    public void onPlatformLeave() {
        super.onPlatformLeave();
        hasFallen = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getMaxFallingSpeed() {
        return 100f;
    }

    /**
     * {@inheritDoc}
     *
     * @return true if the barrel can transform into fire upon destruction
     */
    @Override
    public boolean canTransformToFire() {
        return canTransformToFire && isDestroyedByTank;
    }
}
