package it.unibo.coffebreak.api.view;

import java.awt.event.KeyListener;

/**
 * Represents the View component in the MVC architecture.
 * <p>
 * Responsibilities include:
 * <ul>
 * <li>Displaying the game state to the user</li>
 * <li>Handling user input events</li>
 * <li>Managing the game window</li>
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
     * Typically called once per frame.
     */
    void updateView();

    /**
     * Sets the current state panel.
     * @param panel
     */
    void setStatePanel(String panel);
}
