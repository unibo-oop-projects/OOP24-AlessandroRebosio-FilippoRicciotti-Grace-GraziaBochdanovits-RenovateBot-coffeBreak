package it.unibo.coffebreak.model.impl.entities.tank;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.tank.Tank;
import it.unibo.coffebreak.model.impl.common.Dimension2D;
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

    /**
     * Constructs a new oil tank at the specified position with given dimensions.
     *
     * @param position the fixed position of the tank in game coordinates (cannot be null)
     * @param dimension the physical size of the tank (cannot be null)
     * @throws NullPointerException if position or dimension are null
     */
    public GameTank(final Position2D position, final Dimension2D dimension) {
        super(position, dimension);
    }

    /**
     * Handles collision events with other game entities.
     * <p>
     * When colliding with barrels, this implementation should:
     * <ol>
     *   <li>Transform the barrel into a fire entity</li>
     *   <li>Trigger visual effects</li>
     *   <li>Remove the original barrel from the game</li>
     * </ol>
     * </p>
     *
     * @param other the entity that collided with this tank (typically a barrel)
     */
    @Override
    public void onCollision(final Entity other) {
        //TODO: anche qui pattern Observer per notificare il GameModel (?)
    }
}
