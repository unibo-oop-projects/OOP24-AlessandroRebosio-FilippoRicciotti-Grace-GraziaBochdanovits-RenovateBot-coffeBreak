package it.unibo.coffebreak.api.model.level;

import java.util.List;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;

/**
 * Manages the logic of a game level, including entity management,
 * main character handling, bonus calculation, and level progression.
 * Provides methods to access and modify the current level state.
 * 
 * @author Filippo Ricciotti
 */
public interface LevelManager {
    /**
     * Returns the list of entities present in the level.
     * @return list of active entities
     */
    List<Entity> getEntities();

    /**
     * Returns the main character of the level.
     * @return the main character
     */
    MainCharacter getMainCharacter();

    /**
     * Adds a new entity to the level.
     * @param entity the entity to add
     * @return true if added successfully, false otherwise
     */
    boolean addEntity(Entity entity);

    /**
     * Transforms entities according to game logic (e.g., removal, replacement).
     */
    void transformEntities();

    /**
     * Loads the initial state of the level's entities.
     */
    void loadCurrentEntities();

    /**
     * Returns the current bonus value for the level.
     * @return the bonus value
     */
    int getBonusValue();

    /**
     * Calculates and updates the bonus based on elapsed time.
     * @param deltaTime time elapsed since the last update
     */
    void calculateBonus(float deltaTime);

    /**
     * Returns the index of the current level.
     * @return the level index
     */
    int getLevelIndex();

    /**
     * Advances to the next level.
     */
    void advance();
}
