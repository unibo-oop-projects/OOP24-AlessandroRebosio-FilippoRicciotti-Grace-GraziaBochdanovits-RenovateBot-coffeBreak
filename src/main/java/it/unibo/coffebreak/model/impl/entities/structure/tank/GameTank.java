package it.unibo.coffebreak.model.impl.entities.structure.tank;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.model.impl.common.BoundingBox2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.AbstractEntity;

/**
 * Concrete implementation of an oil tank entity in the game world.
 * <p>
 * This stationary entity serves as an environmental hazard that transforms
 * barrels into fire when they collide with it. The tank remains fixed in position
 * and activates visual fire effects upon barrel collisions.
 * </p>
 *
 * @see Tank
 * @see AbstractEntity
 * @author Grazia Bochdanovits de Kavna
 */
public class GameTank extends AbstractEntity implements Tank {

    private boolean isActive;

    /**
     * Constructs a new oil tank at the specified position with given dimensions.
     *
     * @param position the fixed position of the tank in game coordinates (cannot be null)
     * @param dimension the physical size of the tank (cannot be null)
     * @throws NullPointerException if position or dimension are null
     */
    public GameTank(final Position2D position, final BoundingBox2D dimension) {
        super(position, dimension);
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
