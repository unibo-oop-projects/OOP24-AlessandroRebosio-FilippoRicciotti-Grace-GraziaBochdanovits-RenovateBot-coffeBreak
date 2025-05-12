package it.unibo.coffebreak.model.api;

import java.util.List;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;

/**
 * Represents the main model interface for the Coffee Break game.
 * The model acts as the central point for accessing game entities,
 * player character, and score management functionality.
 * 
 * @author Alessandro Rebosio
 */
public interface Model {

    /**
     * Gets all entities currently present in the game world.
     * 
     * @return an unmodifiable list of all game entities
     */
    List<Entity> getEntities();

    /**
     * Gets the player character.
     * 
     * @return the player character, or null if no player is set
     */
    Character getPlayer();

}
