package it.unibo.coffebreak.controller.api;

/**
 * Represents the game controller in the MVC (Model-View-Controller) pattern.
 * <p>
 * The controller handles user input, translates it into game commands,
 * and updates the game model accordingly. It acts as a bridge between
 * the view (input events) and the model (game state).
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public interface Controller {

    /**
     * Notifies the controller of a new user input event.
     *
     * @param keyCode the key code representing the user input
     */
    void notifyInput(int keyCode);

    /**
     * Removes a previously notified input event, if applicable.
     *
     * @param keyCode the key code to remove from active inputs
     */
    void removeInput(int keyCode);

    /**
     * Updates the game model based on the time elapsed since the last update.
     *
     * @param deltaTime time in seconds since the last update call
     */
    void updateModel(float deltaTime);

    /**
     * Processes all queued inputs and updates the game model accordingly.
     */
    void processInput();
}
