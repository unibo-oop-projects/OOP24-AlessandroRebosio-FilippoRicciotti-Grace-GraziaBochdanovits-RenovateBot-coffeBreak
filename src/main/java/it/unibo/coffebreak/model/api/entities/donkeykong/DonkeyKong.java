package it.unibo.coffebreak.model.api.entities.donkeykong;

import java.util.Optional;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.model.api.entities.enemy.barrel.BarrelFactory;

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
     * @return the newly created and thrown {@link Barrel} instance
     */
    Barrel throwBarrel();

    /**
     * Attempts to throw a barrel. This method may return an empty Optional if 
     * Donkey Kong cannot throw a barrel at this time.
     *
     * @return an {@link Optional} containing the thrown {@link Barrel} if successful,
     *         or an empty {@link Optional} if no barrel was thrown
     */
    Optional<Barrel> tryThrowBarrel();
}
