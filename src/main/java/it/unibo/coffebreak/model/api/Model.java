package it.unibo.coffebreak.model.api;

import java.util.List;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.phases.Phases;

/**
 * Represents the main model interface for the Coffee Break game.
 * The model acts as the central point for accessing game entities,
 * player character, and score management functionality.
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
     * Method that changes the game Phase to a specified one.
     * 
     * @param newPhase specified Phase to switch to
     */
    void setState(Phases newPhase);

    /**
     * Processes and executes the given command, modifying the game state
     * accordingly.
     * Implementations should define the specific behavior for each supported
     * command type.
     * 
     * @param command the command to be executed (must not be null)
     */
    void handleCommand(Command command); // TODO: remove this method, is useless, and add Phases getCurrentPhases(), in
                                         // GameModel return the currentPhase, fix error in GameController, on line 66,
                                         // change with this.model.getCurrentPhase().handleCommand(command, this.model)
}
