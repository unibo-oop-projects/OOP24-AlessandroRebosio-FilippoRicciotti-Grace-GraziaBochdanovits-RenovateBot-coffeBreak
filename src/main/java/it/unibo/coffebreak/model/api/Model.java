package it.unibo.coffebreak.model.api;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.npc.Antagonist;
import it.unibo.coffebreak.model.api.entities.npc.Princess;
import it.unibo.coffebreak.model.api.states.GameState;

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
     * Retrieves the name of the player.
     * 
     * @return the current player name
     */
    String getPlayerName();

    /**
     * Retrieves the player character entity, if present.
     *
     * @return an {@link Optional} containing the player character, or empty if not
     *         present
     */
    Optional<Character> getPlayer();

    /**
     * Retrieves the Antagonist entity, if present.
     *
     * @return an {@link Optional} containing the Antagonist, or empty if not
     *         present
     */
    Optional<Antagonist> getAntagonist();

    /**
     * Retrieves the Target entity, if present.
     *
     * @return an {@link Optional} containing the Target, or empty if not present
     */
    Optional<Princess> getPrincess();

    /**
     * Gets the current game state.
     * 
     * @return the current game state
     */
    GameState getGameState();

    /**
     * Sets or updates the player's name.
     * 
     * @param newPlayerName the new name to assign to the player
     * @throws NullPointerException     if the name is null
     * @throws IllegalArgumentException if the name is empty or invalid
     */
    void setPlayerName(String newPlayerName);

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

    void getNextMap();

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
    void executeCommand(Command command);

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

    /**
     * @param deltaTime time in seconds since last calculation
     */
    void calculateBonus(float deltaTime);
}
