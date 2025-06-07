package it.unibo.coffebreak.model.impl.entities.enemy.barrel;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.Movable;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.model.impl.common.BoundingBox2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.AbstractEntity;
import it.unibo.coffebreak.model.impl.entities.enemy.AbstractEnemy;
import it.unibo.coffebreak.model.impl.entities.structure.tank.GameTank;

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

    private final boolean canTransformToFire;

    private boolean isDestroyedByTank;

    /**
     * Constructs a new game barrel with specified properties.
     *
     * @param position           the initial position of the barrel (cannot be null)
     * @param dimension          the physical dimensions of the barrel (cannot be
     *                           null)
     * @param canTransformToFire whether the barrel can turn into fire when
     *                           destroyed
     * @throws NullPointerException if position, dimension or physics are null
     */
    public GameBarrel(final Position2D position, final BoundingBox2D dimension, final boolean canTransformToFire) {
        super(position, dimension);

        this.canTransformToFire = canTransformToFire;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final float deltaTime) {
        // if (isDestroyed()) {
        //     return;
        // }

        // TODO: Barrel update()
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
        if (other instanceof GameTank) {
            this.isDestroyedByTank = true;
            this.destroy();
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
