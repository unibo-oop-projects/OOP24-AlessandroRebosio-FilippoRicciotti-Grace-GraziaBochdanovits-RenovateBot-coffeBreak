package it.unibo.coffebreak.controller.api;

import it.unibo.coffebreak.controller.api.input.Input;
import it.unibo.coffebreak.model.api.Model;

/**
 * Defines the controller component in the MVC (Model-View-Controller) pattern.
 * The controller acts as an intermediary between the view (input) and the
 * model, processing user input and updating the model accordingly.
 * 
 * @author Alessandro Rebosio
 */
public interface Controller {

    /**
     * Returns the input manager responsible for handling and queueing user input
     * commands.
     * The input manager acts as a bridge between user input events (from the view)
     * and the command processing system in the controller.
     *
     * @return the {@link Input} manager instance used by this controller
     */
    Input getInputManager();

    /**
     * Processes input and updates the model accordingly.
     * This method serves as the main entry point for the controller's logic,
     * where input is interpreted and appropriate model changes are triggered.
     *
     * @param model the game model to be updated based on processed input
     * @throws IllegalArgumentException if the provided model is null
     * @throws IllegalStateException    if the controller cannot process input in
     *                                  its current state
     */
    void processInput(Model model);
}
