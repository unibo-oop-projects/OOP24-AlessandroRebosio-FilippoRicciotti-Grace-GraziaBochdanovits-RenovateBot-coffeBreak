package it.unibo.coffebreak.impl.model.entities.structure.tank;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;

/**
 * Concrete implementation of an oil tank entity in the game world.
 * <p>
 * This stationary entity serves as an environmental hazard that transforms
 * barrels into fire when they collide with it. The tank remains fixed in
 * position
 * and activates visual fire effects upon barrel collisions.
 * </p>
 *
 * @see Tank
 * @see AbstractEntity
 * @author Grazia Bochdanovits de Kavna
 */
public class GameTank extends AbstractEntity implements Tank {

    private static final int SCALE_HEIGHT = 2;
    private boolean isActive;

    /**
     * Constructs a new oil tank at the specified position with given dimensions.
     *
     * @param position  the fixed position of the tank in game coordinates (cannot
     *                  be null)
     * @param dimension the 2D dimension of the tank (cannot be null)
     * @throws NullPointerException if position or dimension are null
     */
    public GameTank(final Position position, final BoundigBox dimension) {
        super(position, dimension.scaleHeight(SCALE_HEIGHT));

        this.isActive = false;
    }

    /**
     * Handles collision events with other game entities.
     *
     * @param other the entity that collided with this tank
     */
    @Override
    public void onCollision(final Entity other) {
        if (other instanceof Barrel && !isActive) {
            this.isActive = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive() {
        return this.isActive;
    }
}
