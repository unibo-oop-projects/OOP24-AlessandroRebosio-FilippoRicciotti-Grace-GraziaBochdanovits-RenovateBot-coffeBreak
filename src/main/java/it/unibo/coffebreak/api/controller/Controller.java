package it.unibo.coffebreak.api.controller;

import it.unibo.coffebreak.api.model.Model;

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
    void keyPressed(int keyCode);

    /**
     * Handles a key release event by forwarding it to the input system.
     * 
     * @param keyCode the key code of the released key
     */
    void keyReleased(int keyCode);

    /**
     * Gets the game model associated with this controller.
     * 
     * @return the game model instance
     */
    Model getModel();

    /**
     * Processes all pending input commands and applies them to the game model.
     * Should be called once per frame to ensure responsive controls.
     */
    void processInput();

    /**
     * Updates the game model based on the elapsed time.
     *
     * @param deltaTime the time elapsed since the last update (in seconds)
     * @throws IllegalArgumentException if deltaTime is negative
     */
    void updateModel(float deltaTime);

    /**
     * Checks if the game should continue running.
     * 
     * @return true if the game should continue running, false otherwise
     */
    boolean isGameActive();
}
