package it.unibo.coffebreak.api.model.entities.npc;

import java.util.Optional;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;

/**
 * Represents Donkey Kong, the iconic arcade game character who throws barrels.
 * This interface extends {@link Entity}, inheriting common game entity
 * behaviors,
 * and adds Donkey Kong-specific functionality.
 *
 * @see Entity
 * @author Grazia Bochdanovits de Kavna
 */
public interface Antagonist extends Entity {
    /**
     * Attempts to throw a barrel if the throw interval has elapsed.
     * <p>
     * This method checks if the accumulated time since the last throw
     * ({@code deltaTime})
     * has reached or exceeded {@link #BARREL_THROW_INTERVAL}. If so, it resets the
     * throw
     * timer and returns a new barrel wrapped in an {@link Optional}. Otherwise,
     * returns
     * an empty {@link Optional}.
     * 
     * @param deltaTime the accumulated time since the last update (in seconds)
     * @return {@link Optional} containing a new {@link Barrel} if the interval has
     *         elapsed,
     *         otherwise empty
     */
    Optional<Barrel> tryThrowBarrel(float deltaTime);

    /**
     * Checks if the antagonist is currently in the process of throwing a barrel.
     * <p>
     * This method can be used to determine if the antagonist is in the throwing
     * animation
     * or actively performing a throw action.
     * 
     * @return true if the antagonist is throwing a barrel, false otherwise
     */
    boolean isThrowing();
}
