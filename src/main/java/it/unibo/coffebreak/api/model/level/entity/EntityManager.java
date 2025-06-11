package it.unibo.coffebreak.api.model.level.entity;

import java.util.List;
import java.util.Optional;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;

/**
 * Manages the entities in the game level, providing methods to load, add,
 * remove, and transform entities.
 * 
 * @author Filippo Ricciotti
 */
public interface EntityManager {
    /**
     * Gets the list of all currently managed entities.
     * 
     * @return a list of entities
     */
    List<Entity> getEntities();

    /**
     * Gets the player.
     * 
     * @return the player
     */
    Optional<MainCharacter> getPlayer();

    /**
     * Loads entities from a map represented by text lines.
     * 
     * @param mapLines the lines representing the map layout
     */
    void loadEntitiesFromMap(List<String> mapLines);

    /**
     * Adds a new entity to the manager.
     * 
     * @param entity the entity to add
     * @return true if the entity was added successfully, false otherwise
     */
    boolean addEntity(Entity entity);

    /**
     * Removes all entities from the manager.
     */
    void cleanEntities();

    /**
     * Resets the entities to their initial state based on the provided map.
     * 
     * @param mapLines the lines representing the map layout
     */
    void resetEntities(List<String> mapLines);

    /**
     * Transforms specific entities (e.g., barrels) in the game.
     */
    void transformBarrels();
}
