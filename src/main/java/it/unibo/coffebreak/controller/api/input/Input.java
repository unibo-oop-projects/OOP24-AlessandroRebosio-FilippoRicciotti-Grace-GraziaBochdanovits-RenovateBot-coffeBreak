package it.unibo.coffebreak.controller.api.input;

import it.unibo.coffebreak.controller.api.command.Command;

/**
 * Interface for handling user input and converting it into game commands.
 * <p>
 * This interface follows the Command Pattern to separate input handling from
 * command execution.
 * </p>
 * 
 * <p>
 * Usage pattern:
 * <ul>
 * <li>Input events are notified via {@link #registerKeyPress(int)}</li>
 * <li>The controller retrieves pending commands using
 * {@link #getCommand()}</li>
 * <li>Commands are executed by the game logic</li>
 * </ul>
 * </p>
 * 
 * @see Command
 * 
 * @author Alessandro Rebosio
 */
public interface Input {

    /**
     * Retrieves the next available command from the input queue.
     *
     * @return the next {@link Command}, or {@code null} if no commands are
     *         available
     */
    Command getCommand();

    /**
     * Notifies the input system of a new input event.
     * 
     * @param keyCode the key code representing the user input
     * @throws NullPointerException if the key code is invalid or unmapped
     */
    void registerKeyPress(int keyCode);

    /**
     * Removes the association between the specified key code and its command.
     * 
     * @param keyCode the key code to remove from bindings
     */
    void registerKeyRelease(int keyCode);

    /**
     * Binds a key code to a specific command.
     *
     * @param keyCode the key code to bind
     * @param command the command to associate with the key code
     * @return the previous command bound to this key, or {@code null} if there was
     *         none
     */
    Command bindKey(int keyCode, Command command);

    /**
     * Clears all pending commands from the input queue.
     */
    void clearQueue();
}
