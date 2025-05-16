package it.unibo.coffebreak.view.api;

import java.awt.event.KeyListener;

/**
 * Represents the View component in the MVC architecture.
 * <p>
 * The View is responsible for displaying the game state to the user
 * and handling user input. Implementations should provide:
 * </p>
 * <ul>
 * <li>Visual rendering of game state
 * <li>Input handling capabilities
 * <li>Window management
 * </ul>
 * 
 * @author Alessandro Rebosio
 */
public interface View extends KeyListener {
    /**
     * Closes the game view and releases resources.
     */
    void close();

    /**
     * Updates the view with the current game state.
     */
    void update();
}
