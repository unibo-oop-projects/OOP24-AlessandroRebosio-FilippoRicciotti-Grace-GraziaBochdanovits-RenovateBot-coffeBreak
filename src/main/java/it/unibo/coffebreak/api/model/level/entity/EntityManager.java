package it.unibo.coffebreak.api.model.level.entity;

import java.util.List;
import java.util.Optional;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;

/**
 * Interface for managing the entities within a game level, including their loading,
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
     * 
     */
    /**
     * Loads entities from the provided map data.
     * 
     * @param map                  the map data to load entities from
     * @param canDonkeyThrowBarrel indicates if Donkey can throw barrels
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
     * Transforms certain entities (e.g., barrels that can transform to fire),
     * removes collected collectibles, broken platforms, and destroyed enemies,
     * and adds new entities as needed.
     */
    void transformEntities();

    /**
     * Returns the number of rows in the level or map managed by this EntityManager.
     *
     * @return the number of rows
     */
    int getRow();

    /**
     * Returns the number of columns in the level or map managed by this
     * EntityManager.
     *
     * @return the number of rows
     */
    int getColumn();

    /**
     * Resets the main character to its initial state or position in the level.
     * This method is typically used to restart the character after a game event,
     * such as losing a life or restarting the level.
     */
    void resetCharacter();
}
