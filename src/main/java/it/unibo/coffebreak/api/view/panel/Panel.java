package it.unibo.coffebreak.api.view.panel;

import it.unibo.coffebreak.api.model.states.ModelState;

/**
 * Represents a generic view panel responsible for displaying the current
 * state of the game.
 * 
 * @author Filippo Ricciotti
 */
public interface Panel {
    /**
     * Updates the view to reflect the current model state of the game.
     * <p>
     * Implementations should use the provided {@code ModelState} to determine
     * which screen or view should be displayed, such as menus, gameplay,
     * pause screens, or game over screens.
     * </p>
     *
     * @param modelState the new model state to be visualized
     */
    void updateViewState(ModelState modelState);
}
