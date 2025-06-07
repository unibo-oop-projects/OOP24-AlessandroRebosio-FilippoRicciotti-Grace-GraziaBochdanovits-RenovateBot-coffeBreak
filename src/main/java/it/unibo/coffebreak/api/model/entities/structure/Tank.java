package it.unibo.coffebreak.api.model.entities.structure;

import it.unibo.coffebreak.api.model.entities.Entity;

/**
 * Represents a stationary oil tank that can ignite barrels on contact.
 * <p>
 * When a barrel reaches the tank's position, the barrel transforms into a fire entity.
 * </p>
 *
 * @see Entity
 * @author Grazia Bochdanovits de Kavna
 */
public interface Tank extends Entity {
    /***
     * Checks the activation state of the oil tank.
     * <p>
     * An active tank can interact with barrels, igniting them when they come into contact.
     * An inactive tank will not affect barrels.
     * </p>
     *
     * @return true if the thank is active, false otherwise
     */
    boolean isActive();
}
