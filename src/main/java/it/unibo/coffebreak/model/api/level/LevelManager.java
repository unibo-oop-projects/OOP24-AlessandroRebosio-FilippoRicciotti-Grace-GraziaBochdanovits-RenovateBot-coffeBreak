package it.unibo.coffebreak.model.api.level;

import java.util.List;

import it.unibo.coffebreak.model.api.entities.Entity;

/**
 * Interface for managing levels and maps in the game.
 * It handles loading, adding/removing entities, and transitioning between maps
 * and levels.
 * 
 * @author Filippo Ricciotti
 */
public interface LevelManager {
    /**
     * Returns an unmodifiable view of the current list of entities.
     *
     * @return unmodifiable list of entities
     */
    List<Entity> getEntities();

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
     * Transforms certain entities into other entities according to game logic.
     * This could include power-ups changing state, enemies evolving, or
     * environmental objects transitioning forms.
     */
    void transformEntities();

    /**
     * Cleans the current list of entities by removing destroyed enemies
     * and collected collectibles.
     */
    void cleanEntities();

    /**
     * Resets all entities in the current level to their initial state.
     * This may include repositioning, restoring health, or other properties.
     */
    void resetEntities();
}
