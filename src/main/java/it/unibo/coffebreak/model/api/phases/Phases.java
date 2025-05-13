package it.unibo.coffebreak.model.api.phases;

import it.unibo.coffebreak.controller.handle.Action;
import it.unibo.coffebreak.model.api.Model;

/**
 * Interface for handling Game Phases
 */
public interface Phases {
    /**
     * Enum List of Game Phases
     */
    enum PhaseType {

        /**
         * Phasetype for when inside the menu.
         */
        MENU,
        /**
         * Phasetype for when playing the game.
         */
        IN_GAME,
        /**
         * Phasetype for when inside the paused menu.
         */
        PAUSE,
        /**
         * Phasetype for when inside the Game Over menu.
         */
        GAME_OVER
    }

    /**
     * method for signaling the entrance in a new Phase.
     */
    void enterState();

    /**
     * method for signaling a Phase leaving.
     */
    void exitState();

    /**
     * method that handle input depending on the Phase the model is currently in.
     * 
     * @param action
     * @param model
     */
    void handleInput(Action action, Model model);

    /**
     * Updates the logic of the current game Phase based on deltaTime.
     * 
     * @param deltaTime time in milliseconds since the last update call.
     */
    void update(long deltaTime);
}
