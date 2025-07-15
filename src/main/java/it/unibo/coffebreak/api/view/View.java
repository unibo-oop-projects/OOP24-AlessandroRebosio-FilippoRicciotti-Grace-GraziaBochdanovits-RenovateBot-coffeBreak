package it.unibo.coffebreak.api.view;

/**
 * The View component in MVC.
 * <p>
 * Responsible for displaying the game state and handling user input.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public interface View {

    /**
     * Closes the view and releases resources.
     */
    void close();

    /**
     * Updates the view with the current game state.
     * 
     * @param deltaTime time elapsed since last update, in seconds
     */
    void update(float deltaTime);
}
