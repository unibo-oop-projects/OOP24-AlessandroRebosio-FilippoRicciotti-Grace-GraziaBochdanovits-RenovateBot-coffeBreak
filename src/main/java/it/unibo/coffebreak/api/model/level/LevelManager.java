package it.unibo.coffebreak.api.model.level;

import java.util.List;
import java.util.Optional;

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
     * 
     * @return list of active entities
     */
    List<Entity> getEntities();

    /**
     * Returns the main character of the level.
     * 
     * @return the main character
     */
    Optional<MainCharacter> getMainCharacter();

    /**
     * Adds a new entity to the level.
     * 
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
     * 
     * @return the bonus value
     */
    int getBonusValue();

    /**
     * Calculates and updates the bonus based on elapsed time.
     * 
     * @param deltaTime time elapsed since the last update
     */
    void calculateBonus(float deltaTime);

    /**
     * Returns the index of the current level.
     * 
     * @return the level index
     */
    int getLevelIndex();

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

    /**
     * Advances to the next level.
     */
    void advance();
}
