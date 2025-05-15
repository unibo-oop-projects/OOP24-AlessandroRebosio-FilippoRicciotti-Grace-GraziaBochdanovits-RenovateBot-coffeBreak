package it.unibo.coffebreak.controller.api;

/**
 * The Controller component in the MVC (Model-View-Controller) pattern.
 * Acts as the intermediary between View (input) and Model (game state),
 * processing user input and updating the model accordingly.
 * 
 * @author Alessandro Rebosio
 */
public interface Controller {

    /**
     * Notifies the controller about a user input event.
     * 
     * <p>
     * The controller will translate this raw input into game commands
     * according to its current key bindings and state.
     *
     * @param keyCode the physical key code received from input system
     */
    void notifyInput(int keyCode);

    /**
     * Processes input and updates the model accordingly.
     * This method serves as the main entry point for the controller's logic,
     * where input is interpreted and appropriate model changes are triggered.
     */
    void processInput();
}
