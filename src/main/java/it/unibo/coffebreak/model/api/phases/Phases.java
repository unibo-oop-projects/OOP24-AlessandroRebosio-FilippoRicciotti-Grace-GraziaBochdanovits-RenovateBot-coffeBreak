package it.unibo.coffebreak.model.api.phases;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;

/**
 * Interface for handling Game Phases.
 * 
 * @author Filippo Ricciotti
 */
public interface Phases {

    /**
     * method for signaling the entrance in a new Phase.
     */
    void enterPhase();

    /**
     * method for signaling a Phase leaving.
     */
    void exitPhase();

    /**
     * method that handle input depending on the Phase the model is currently in.
     * 
     * @param action Input to handle.
     * @param model  the game model containing the possible Phase to change.
     */
    void handleCommand(Command action, Model model); // TODO: change param. name action to command, and rename it
                                                     // handleCommand

    /**
     * Updates the logic of the current game Phase based on deltaTime.
     * 
     * @param deltaTime time in milliseconds since the last update call.
     */
    void update(float deltaTime);
}
