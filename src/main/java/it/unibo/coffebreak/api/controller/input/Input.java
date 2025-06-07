package it.unibo.coffebreak.api.controller.input;

import it.unibo.coffebreak.api.common.Command;

/**
 * Interface for converting physical input events into logical game commands.
 * <p>
 * Follows the Command Pattern to decouple input handling from command
 * execution.
 * Implementations should handle key bindings and command queuing.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public interface Input {
    /**
     * Retrieves the next pending command from the input queue.
     *
     * @return the next available command, or null if the queue is empty
     */
    Command getCommand();

    /**
     * Registers a key press event and converts it to a game command.
     *
     * @param keyCode the physical key code that was pressed
     */
    void registerKeyPress(int keyCode);

    /**
     * Registers a key release event.
     *
     * @param keyCode the physical key code that was released
     */
    void registerKeyRelease(int keyCode);

    /**
     * Binds a physical key to a logical game command.
     *
     * @param keyCode the key code to bind
     * @param command the command to associate with the key
     * @return the previously bound command, or null if the key was unbound
     */
    Command bindKey(int keyCode, Command command);

    /**
     * Clears all pending commands from the input queue.
     */
    void clearQueue();
}
