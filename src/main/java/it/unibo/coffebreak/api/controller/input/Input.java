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
    void keyPressed(int keyCode);

    /**
     * Registers a key release event.
     *
     * @param keyCode the physical key code that was released
     */
    void keyReleased(int keyCode);

    /**
     * Clears all pending commands from the input queue.
     */
    void clearQueue();
}
