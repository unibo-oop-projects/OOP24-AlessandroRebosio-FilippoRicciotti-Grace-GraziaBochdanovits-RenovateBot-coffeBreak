package it.unibo.coffebreak.view.api;

/**
 * Represents the View component in the MVC architecture.
 * <p>
 * The View is responsible for displaying the game state to the user.
 * This interface defines a method to render the current visual state.
 * </p>
 * 
 */
public interface View {

    /**
     * Renders the current state of the game.
     * <p>
     * This method should update the graphical interface to reflect
     * the latest state of the game model.
     * </p>
     */
    void render();
}
