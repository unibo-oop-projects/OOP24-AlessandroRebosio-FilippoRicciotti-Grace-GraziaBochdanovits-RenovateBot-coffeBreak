package it.unibo.coffebreak.controller.api.input;

import it.unibo.coffebreak.controller.api.command.Command;

/**
 * An interface for handling user input and translating it into game commands.
 * Follows the Command pattern to decouple input detection from command
 * execution.
 * 
 * <p>
 * Implementations should handle raw input events and convert them into
 * appropriate {@link Command} objects that can be processed by the game logic.
 * 
 * <p>
 * Typical usage:
 * <ol>
 * <li>Input events are captured and notified via {@link #notifyInput(int)}
 * <li>The game loop polls for commands via {@link #getNextCommand()}
 * <li>Commands are executed by the game controller
 * </ol>
 * 
 * @see Command
 * 
 * @author Alessandro Rebosio
 */
public interface Input {

    /**
     * Retrieves the next available command from the input queue.
     * 
     * @return the next {@link Command} ready for processing.
     */
    Command getCommand();

    /**
     * Notifies the input system of a new raw input event.
     * 
     * @param keyEvent the key code representing the input event
     * @throws NullPointerException     if the command is null
     */
    void notifyInput(int keyEvent);

    /**
     * Binds a specific key code to a game command.
     * 
     * @param keyCode the physical key code to bind
     * @param command the command to associate with the key
     * 
     * @return the previous command bound to this key, or null if unbound
     */
    Command bindKey(int keyCode, Command command);

    /**
     * Clears all pending commands from the input queue.
     */
    void clearQueue();
}
