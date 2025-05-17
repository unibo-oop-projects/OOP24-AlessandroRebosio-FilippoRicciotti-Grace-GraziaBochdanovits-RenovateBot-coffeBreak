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
     * Processes all pending input commands and applies them to the game model.
     * Should be called once per frame to ensure responsive controls.
     */
    void processInput();

    /**
     * Updates the game model based on the elapsed time.
     *
     * @param deltaTime the time elapsed since the last update (in seconds)
     */
    void updateModel(float deltaTime);

    /**
     * Checks if the game should continue running.
     * <p>
     * This method combines the game's logical state (from controller)
     * with the model's running state to determine if the game loop should continue.
     * </p>
     * 
     * @return {@code true} if the game should continue running,
     *         {@code false} if the game should terminate.
     * 
     * @see Model#isRunning()
     */
    public boolean isGameActive();
}
