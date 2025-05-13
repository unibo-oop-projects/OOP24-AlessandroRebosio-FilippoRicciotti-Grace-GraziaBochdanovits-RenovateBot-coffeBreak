package it.unibo.coffebreak.model.api.phases;

import it.unibo.coffebreak.controller.handle.Action;
import it.unibo.coffebreak.model.api.Model;

/**
 * Interface for handling Game Phases.
 */
public interface Phases {
    /**
     * Enum List of Game Phases.
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
     * @param action Input to handle.
     * @param model  the game model containing the possible Phase to change.
     */
    void handleInput(Action action, Model model);

    /**
     * Updates the logic of the current game Phase based on deltaTime.
     * 
     * @param deltaTime time in milliseconds since the last update call.
     */
    void update(long deltaTime);
}

/*
 * TODO: Create an abstract implementation of this interface, where onEnter and
 * onExit are empty methods. Make handleAction a protected abstract method and
 * modifiy a protected update(float deltaTime) method where the only instruction
 * is this.deltaTime = deltaTime, the same for enterPhase and exitPhaseIn the
 * subclasses, call super.update(deltaTime). Then, make GameOverPhase,
 * InGamePhase, MenuPhase, and PausePhase extend this abstract class, and
 * override only handleAction, moving each of them into a directory with its
 * name. (e.g. impl/phases/gameOver/GameOverPhase.java)
 */
