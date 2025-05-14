package it.unibo.coffebreak.model.api.entities.donkeykong;

import it.unibo.coffebreak.model.api.entities.Entity;

/**
 * Represents Donkey Kong, the iconic arcade game character who throws barrels.
 * This interface extends {@link Entity}, inheriting common game entity behaviors,
 * and adds Donkey Kong-specific functionality.
 *
 * @see Entity
 * @author Grazia Bochdanovits de Kavna
 */
public interface DonkeyKong extends Entity {

    /**
     * Triggers Donkey Kong to throw a new barrel.
     *
     * <p>Implementations should:
     * <ul>
     *   <li>Create a new {@link Barrel} instance (typically via {@link BarrelFactory})</li>
     *   <li>Position the barrel at Donkey Kong's current location</li>
     *   <li>Register the barrel with the game world</li>
     * </ul>
     *
     */
    void throwBarrel();
}
