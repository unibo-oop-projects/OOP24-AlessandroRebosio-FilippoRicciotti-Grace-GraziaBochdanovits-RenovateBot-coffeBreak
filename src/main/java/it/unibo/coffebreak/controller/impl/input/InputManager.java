package it.unibo.coffebreak.controller.impl.input;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.controller.api.input.Input;

/**
 * A thread-safe implementation of {@link Input} that manages commands in a FIFO
 * queue.
 * This class serves as a buffer between input sources and command processors,
 * allowing asynchronous command handling.
 *
 * <p>
 * The implementation uses a {@link ConcurrentLinkedQueue} to ensure thread-safe
 * operations, making it suitable for multi-threaded environments where input
 * might be generated from different threads than the one processing commands.
 * 
 * @author Alessandro Rebosio
 */
public class InputManager implements Input {

    private final Queue<Command> queue;

    /**
     * Constructs a new InputManager with an empty command queue.
     * The underlying queue is thread-safe and non-blocking.
     */
    public InputManager() {
        this.queue = new ConcurrentLinkedQueue<>();
    }

    /**
     * Retrieves and removes the next command from the queue (FIFO order).
     * Returns {@code null} if the queue is empty.
     *
     * @return the next available {@link Command}, or {@code null} if none available
     */
    @Override
    public Command getCommand() {
        return this.queue.poll();
    }

    /**
     * Adds a new command to the queue for processing.
     *
     * @param command the command to be added to the queue
     * @throws IllegalArgumentException if the command is {@code null}
     */
    @Override
    public void notifyCommand(final Command command) {
        Objects.requireNonNull(command, "Command cannot be null");
        this.queue.add(command);
    }
}
