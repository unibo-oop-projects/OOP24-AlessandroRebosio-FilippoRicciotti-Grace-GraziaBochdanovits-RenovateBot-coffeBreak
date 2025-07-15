package it.unibo.coffebreak.api.model.entities.enemy.barrel;

import it.unibo.coffebreak.api.model.entities.enemy.Enemy;

/**
 * Represents a barrel enemy in the game world.
 * <p>
 * Barrels are dynamic entities that roll along platforms following their slope direction.
 * They can be destroyed and may transform into fire when colliding with specific objects.
 * </p>
 * 
 * <h3>Behavior Characteristics:</h3>
 * <ul>
 *   <li>Rolls autonomously along platforms</li>
 *   <li>Changes direction when falling from a platform</li>
 *   <li>Can be destroyed by player actions</li>
 *   <li>May transform into fire hazards under certain conditions</li>
 * </ul>
 * 
 * @see Enemy
 * @author Grazia Bochdanovits de Kavna
 */
public interface Barrel extends Enemy {
    /**
     * Determines if this barrel can transform into fire upon destruction or collision.
     * 
     * @return {@code true} if the barrel can transform into fire, {@code false} otherwise
     */
    boolean canTransformToFire();
}
