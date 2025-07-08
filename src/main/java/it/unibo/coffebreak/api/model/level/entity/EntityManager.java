package it.unibo.coffebreak.api.model.level.entity;

import java.util.List;
import java.util.Optional;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;

/**
 * Manages the entities within a game level, including their loading,
 * addition, transformation, and access to the main character.
 * Provides methods to interact with and modify the set of entities
 * present in the current level.
 * 
 * @author Filippo Ricciotti
 */
public interface EntityManager {
    /**
     * Returns the list of entities present in the level.
     * 
     * @return list of entities
     */
    List<Entity> getEntities();

    /**
     * Returns the main character if present.
     * 
     * @return an Optional containing the main character, or empty if not present
     */
    Optional<MainCharacter> getMainCharacter();

    /**
     * Loads entities from the provided map data.
     * 
     * @param map the map data to load entities from
     */
    void loadEntities(List<String> map, boolean canDonkeyThrowBarrel);

    /**
     * Adds a new entity to the level.
     * 
     * @param entity the entity to add
     * @return true if the entity was added successfully, false otherwise
     */
    boolean addEntity(Entity entity);

    /**
     * Transforms entities according to game logic (e.g., removal, replacement).
     */
    void transformEntities();
}
