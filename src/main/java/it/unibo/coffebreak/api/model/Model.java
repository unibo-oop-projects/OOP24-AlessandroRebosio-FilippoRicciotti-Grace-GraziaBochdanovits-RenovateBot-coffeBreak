package it.unibo.coffebreak.api.model;

import java.util.List;
import java.util.function.Supplier;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.states.GameState;

/**
 * Represents the main model interface for the game.
 * <p>
 * The model acts as the central point for accessing game entities,
 * player information, and game state management. It handles the core
 * game logic and maintains the game world state.
 * </p>
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
     * Gets the player.
     * 
     * @return the player
     */
    MainCharacter getMainCharacter();

    /**
     * Gets the current game state.
     * 
     * @return the current game state
     */
    GameState getGameState();

    /**
     * Method for get bonus value.
     * 
     * @return the bonus value
     */
    int getBonusValue();

    /**
     * Changes the current game state to the specified one.
     * 
     * @param newState Supplier providing the new game state
     * @throws NullPointerException if newState is null
     */
    void setState(Supplier<GameState> newState);

    /**
     * Adds an entity to the game model.
     * The entity will be included in the list of active entities.
     *
     * @param entity the {@link Entity} to be added
     * @return true if the entity was added successfully, false otherwise
     * @throws NullPointerException if entity is null
     */
    boolean addEntity(Entity entity);

    /**
     * Adds the current player's score to the leaderboard.
     */
    void addEntryInLeaderBoard();

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

    /**
     * Transforms certain entities into other entities according to game logic.
     * This could include power-ups changing state, enemies evolving, or
     * environmental objects transitioning forms.
     */
    void transformEntities();

    /**
     * Calculates and applies a time-based bonus duration is decremented by
     * {@code deltaTime}.
     * 
     * @param deltaTime the time elapsed since the last frame (in seconds).
     */
    void calculateBonus(float deltaTime);

    /**
     * Advances to the next map in the game sequence.
     */
    void nextMap();

    /**
     * Starts the first level of the game.
     */
    void start();

    /**
     * Stops the game simulation and triggers any necessary cleanup.
     */
    void stop();

    /**
     * Processes and executes the given game command.
     * 
     * @param command the command to be executed
     * @throws NullPointerException if command is null
     */
    void handleCommand(Command command);

    /**
     * Updates the game logic based on elapsed time.
     * This includes entity movements, collisions, and game state checks.
     *
     * @param deltaTime time in seconds since last update
     */
    void update(float deltaTime);

    /**
     * Calculates and applies any time-based bonuses.
     * 
     * @return true if the game is running, false otherwise
     */
    boolean isRunning();
}
