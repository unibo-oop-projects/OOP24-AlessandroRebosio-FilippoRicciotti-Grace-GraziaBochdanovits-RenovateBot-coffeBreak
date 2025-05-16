package it.unibo.coffebreak.controller.api;

/**
 * Interface for the game controller in the MVC pattern.
 * Handles user input and updates the game model.
 * Acts as a bridge between input and the game state.
 * 
 * @author Alessandro Rebosio
 */
public interface Controller {

    /**
     * Handles a new key input.
     *
     * @param keyCode the key that was pressed
     */
    void notifyInput(int keyCode);

    void removeInput(int keyCode);

    /**
     * Updates the game model based on time passed.
     *
     * @param deltaTime time in seconds since the last update
     */
    void updateModel(float deltaTime);

    /**
     * Processes user input and updates the model.
     */
    void processInput();
}
