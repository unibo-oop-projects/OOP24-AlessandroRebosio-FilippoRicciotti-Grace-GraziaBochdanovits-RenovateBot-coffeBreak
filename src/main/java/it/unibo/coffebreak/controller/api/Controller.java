package it.unibo.coffebreak.controller.api;

import it.unibo.coffebreak.model.api.Model;

/**
 * Defines the controller component in the MVC (Model-View-Controller) pattern.
 * The controller acts as an intermediary between the view (input) and the
 * model,
 * processing user input and updating the model accordingly.
 * 
 * <p>
 * Implementations of this interface should:
 * <ul>
 * <li>Handle user input and translate it into model updates</li>
 * <li>Coordinate between the view and model components</li>
 * <li>Contain the application's main control logic</li>
 * </ul>
 * 
 * @author Alessandro Rebosio
 */
public interface Controller {

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
