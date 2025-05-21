package it.unibo.coffebreak.model.api.entities.npc;

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
public interface Antagonist extends Entity {

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
     * Attempts to throw a barrel if the throw interval has elapsed.
     * <p>
     * This method checks if the accumulated time since the last throw ({@code deltaTime}) 
     * has reached or exceeded {@link #BARREL_THROW_INTERVAL}. If so, it resets the throw 
     * timer and returns a new barrel wrapped in an {@link Optional}. Otherwise, returns 
     * an empty {@link Optional}.
     * 
     * @param deltaTime the accumulated time since the last update (in seconds)
     * @return {@link Optional} containing a new {@link Barrel} if the interval has elapsed, 
     *         otherwise empty
     */
    Optional<Barrel> tryThrowBarrel(float deltaTime);
}
