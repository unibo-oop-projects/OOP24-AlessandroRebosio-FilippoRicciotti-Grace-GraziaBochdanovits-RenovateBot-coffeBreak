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
     * 
     * @param model the model changing state
     */
    void enterPhase(Model model);

    /**
     * method for signaling a Phase leaving.
     * 
     * @param model the model changing state
     */
    void exitPhase(Model model);

    /**
     * method that handle input depending on the Phase the model is currently in.
     * 
     * @param command Input to handle.
     * @param model   the game model containing the possible Phase to change.
     */
    void handleCommand(Model model, Command command);

    /**
     * Updates the logic of the current game Phase based on deltaTime.
     * 
     * @param model     model to update
     * @param deltaTime time in milliseconds since the last update call.
     */
    void update(Model model, float deltaTime);
}
