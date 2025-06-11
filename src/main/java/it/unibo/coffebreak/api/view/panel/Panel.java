package it.unibo.coffebreak.api.view.panel;

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
     * @param deltaTime the time elapsed since the last update
     */
    void update(float deltaTime);
}
