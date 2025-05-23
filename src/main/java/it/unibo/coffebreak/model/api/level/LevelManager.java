package it.unibo.coffebreak.model.api.level;

import it.unibo.coffebreak.model.api.entities.Entity;

/**
 * Interface for managing levels and maps in the game.
 * It handles loading, adding/removing entities, and transitioning between maps
 * and levels.
 */
public interface LevelManager {

    /**
     * Loads all the entities associated with the current level and map.
     * This should be called when initializing or transitioning to a new level/map.
     */
    void loadEntities();

    /**
     * Adds an entity to the current level.
     *
     * @param entity the entity to add
     * @return true if the entity was successfully added, false otherwise
     */
    boolean addEntity(Entity entity);

    /**
     * Removes an entity from the current level.
     *
     * @param entity the entity to remove
     * @return true if the entity was successfully removed, false otherwise
     */
    boolean removeEntity(Entity entity);

    /**
     * Removes all entities from the current level.
     * Useful when resetting or unloading a level.
     */
    void cleanEntities();

    /**
     * Resets all entities in the current level to their initial state.
     * This may include repositioning, restoring health, or other properties.
     */
    void resetEntities();

    /**
     * Advances to the next map in the current level.
     * Typically used when the player completes the current map.
     */
    void nextMap();

    /**
     * Advances to the next level.
     * Typically used when all maps in the current level are completed.
     */
    void nextLevel();
}
