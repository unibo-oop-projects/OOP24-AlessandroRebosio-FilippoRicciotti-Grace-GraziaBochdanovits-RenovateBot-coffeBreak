package it.unibo.coffebreak.model.api;

import java.util.List;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.npc.Antagonist;
import it.unibo.coffebreak.model.api.phases.Phases;

/**
 * Represents the main model interface for the game.
 * <p>
 * The model acts as the central point for accessing game entities,
 * player character, and game state management.
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
     * Gets the player character.
     * 
     * @return the player character, or null if no player is set
     */
    Character getPlayer();

    /**
     * Gets Donkey Kong.
     * 
     * @return the Donkey Kong entity, or null if no DK is set
     */
    Antagonist getDK();

    /**
     * Changes the current game phase to the specified one.
     * 
     * @param newPhase the phase to switch to
     * @throws NullPointerException if newPhase is null
     */
    void setState(Phases newPhase);

    /**
     * Processes and executes the given game command.
     * 
     * @param command the command to be executed
     * @throws NullPointerException if command is null
     */
    void executeCommand(Command command);

    /**
     * Updates the game logic based on elapsed time.
     *
     * @param deltaTime time in seconds since last update
     */
    void update(float deltaTime);

    /**
     * Checks Collision of all entities in Game.
     */
    void checkCollision();

    /**
     * Checks if the game simulation is currently running.
     * 
     * @return true if the game is running, false otherwise
     */
    boolean isRunning();

    /**
     * Stops the game simulation and triggers any necessary cleanup.
     */
    void stop();
}
