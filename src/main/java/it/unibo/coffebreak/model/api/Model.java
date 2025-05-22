package it.unibo.coffebreak.model.api;

import java.lang.annotation.Target;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.model.api.entities.npc.Antagonist;
import it.unibo.coffebreak.model.api.states.GameState;

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
     * Retrieves the player character entity, if present.
     *
     * @return an {@link Optional} containing the player character, or an empty
     *         {@code Optional} if not present
     */
    Optional<Character> getPlayer();

    /**
     * Retrieves the Antagonist entity, if present.
     *
     * @return an {@link Optional} containing the Antagonist entity, or an empty
     *         {@code Optional} if not present
     */
    Optional<Antagonist> getAntagonist();

    /**
     * Retrieves the Target entity, if present.
     *
     * @return an {@link Optional} containing the Target entity, or an empty
     *         {@code Optional} if not present
     */
    Optional<Target> getTarget();

    /**
     * Adds a barrel to the game model.
     * The barrel will be included in the list of active entities if it is not null.
     *
     * @param barrel the {@link Barrel} to be added; must not be {@code null}
     * @return {@code true} if the barrel was added successfully, {@code false}
     *         otherwise
     * @throws NullPointerException if {@code barrel} is {@code null}
     */
    boolean addBarrel(Barrel barrel);

    /**
     * Changes the current game state to the specified one.
     * 
     * @param newState Supplier of the state to switch to
     * @throws NullPointerException if newState is null
     */
    void setState(Supplier<GameState> newState);

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
     * Checks if the game simulation is currently running.
     * 
     * @return true if the game is running, false otherwise
     */
    boolean isRunning();

    /**
     * Stops the game simulation and triggers any necessary cleanup.
     */
    void stop();

    /**
     * Starts the first level.
     */
    void start();
}
