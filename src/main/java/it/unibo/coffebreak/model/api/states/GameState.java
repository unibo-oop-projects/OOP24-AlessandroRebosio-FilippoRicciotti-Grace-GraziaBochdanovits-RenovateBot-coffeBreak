package it.unibo.coffebreak.model.api.states;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;

/**
 * Interface for handling Game Phases.
 * 
 * @author Filippo Ricciotti
 */
public interface GameState {
    /**
     * Represents the possible high-level states of the game.
     * Each constant defines a distinct phase in the game's lifecycle,
     * used to control behavior transitions and rendering logic.
     * 
     * @author Filippo Ricciotti
     */
    enum GameStateType {

        /**
         * The game is in the main menu or sub-menus.
         */
        MENU,

        /**
         * The game is actively being played.
         */
        IN_GAME,

        /**
         * The game is temporarily paused.
         */
        PAUSE,

        /**
         * The game has ended (player won or lost).
         */
        GAME_OVER;
    }

    /**
     * method for signaling the entrance in a new Phase.
     * 
     * @param model the model changing state
     */
    void onEnter(Model model);

    /**
     * method for signaling a Phase leaving.
     * 
     * @param model the model changing state
     */
    void onExit(Model model);

    /**
     * method that handle input depending on the Phase the model is currently in.
     * 
     * @param command Input to handle.
     * @param model   the game model containing the possible Phase to change.
     */
    void handleCommand(Model model, Command command);

    /**
     * Updates the logic of the current game Phase based on deltaTime.
     * 
     * @param model     model to update
     * @param deltaTime time in milliseconds since the last update call.
     */
    void update(Model model, float deltaTime);

    /**
     * Returns the current game state type associated with this state.
     * 
     * @return the enumerated game state type, guaranteed non-null
     */
    GameStateType getStateType();
}
