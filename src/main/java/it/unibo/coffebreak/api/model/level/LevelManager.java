package it.unibo.coffebreak.api.model.level;

import java.util.List;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;

/**
 * Manages game levels and maps, handling entity operations and transitions
 * between game states.
 * Provides functionality to control entities within the current level,
 * including loading,
 * transforming, and resetting them, as well as managing level progression.
 * 
 * @author Filippo RIcciotti
 */
public interface LevelManager {
    /**
     * Gets an unmodifiable view of all entities currently active in the level.
     * 
     * @return an unmodifiable list containing all active entities
     */
    List<Entity> getEntities();

    /**
     * Gets the player.
     * 
     * @return the player
     */
    MainCharacter getPlayer();

    /**
     * Gets the bonus score associated with completing the current map.
     * 
     * @return the bonus points awarded for completing this map
     */
    int getCurrentLevelBonus();

    /**
     * Gets the current level index.
     * 
     * @return the index of the current level
     */
    int getLevelIndex();

    /**
     * Calculates and applies a time-based bonus duration is decremented by
     * {@code deltaTime}.
     * 
     * @param deltaTime the time elapsed since the last frame (in seconds).
     */
    void calculateBonus(float deltaTime);

    /**
     * Loads all entities for the next level or map in the sequence.
     * This method should be called when transitioning to a new game area
     * to initialize all required entities.
     */
    void loadNextEnitites();

    /**
     * Adds a new entity to the current game level.
     * 
     * @param entity the entity to be added to the level
     * @return true if the entity was successfully added, false if the addition
     *         failed
     */
    boolean addEntity(Entity entity);

    /**
     * Transforms specific entities according to game rules and state changes.
     */
    void transformEntities();

    /**
     * Cleans up the entity list.
     */
    void cleanEntities();

    /**
     * Resets all entities in the current level to their initial states.
     */
    void resetEntities();

    /**
     * Advances to the next level if conditions are met.
     * 
     * @return true if advaceLevel, otherwise false
     */
    boolean advanceLevel();
}
