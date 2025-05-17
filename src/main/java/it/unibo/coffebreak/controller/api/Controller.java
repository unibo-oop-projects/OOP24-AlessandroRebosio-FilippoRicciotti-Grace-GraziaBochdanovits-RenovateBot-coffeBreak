package it.unibo.coffebreak.controller.api;

/**
 * The game controller in the MVC (Model-View-Controller) pattern.
 * <p>
 * Acts as the intermediary between the view (user input) and the model
 * (game logic). Handles input processing and coordinates model updates.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public interface Controller {
    /**
     * Handles a key press event by forwarding it to the input system.
     *
     * @param keyCode the key code of the pressed key
     */
    void handleKeyDown(int keyCode);

    /**
     * Handles a key release event by forwarding it to the input system.
     *
     * @param keyCode the key code of the released key
     */
    void handleKeyUp(int keyCode);

    /**
     * Updates the game model based on the elapsed time.
     *
     * @param deltaTime the time elapsed since the last update (in seconds)
     * @return true if the game should continue running, false otherwise
     */
    boolean updateModel(float deltaTime);

    /**
     * Processes all pending input commands and applies them to the game model.
     * Should be called once per frame to ensure responsive controls.
     */
    void processInput();
}
