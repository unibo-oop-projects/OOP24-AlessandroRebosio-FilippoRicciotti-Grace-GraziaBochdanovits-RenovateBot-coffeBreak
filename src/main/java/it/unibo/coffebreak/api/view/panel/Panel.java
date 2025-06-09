package it.unibo.coffebreak.api.view.panel;

import it.unibo.coffebreak.api.controller.Controller;

/**
 * Represents a generic view panel responsible for displaying the current
 * state of the game.
 * 
 * @author Filippo Ricciotti
 */
public interface Panel {
    /**
     * Updates the current game screen by switching to the appropriate view state.
     *
     * @param controller the game controller providing the current game state
     */
    void updateViewState(Controller controller);
}
