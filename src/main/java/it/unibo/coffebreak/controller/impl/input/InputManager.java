package it.unibo.coffebreak.controller.impl.input;

import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.controller.api.input.Input;

/**
 * A thread-safe implementation of {@link Input} that manages keyboard input
 * through a command queue and key binding system.
 * 
 * <p>
 * This implementation features:
 * <ul>
 * <li>Thread-safe command queue using {@link ConcurrentLinkedQueue}
 * <li>Configurable key bindings via {@link #bindKey(int, Command)}
 * <li>Input buffering for smooth command processing
 * </ul>
 * 
 * <p>
 * The class maintains an internal mapping between physical key codes
 * ({@link KeyEvent} constants) and logical game commands. When a key event
 * is received via {@link #notifyInput(int)}, it's converted to a command
 * based on the current bindings and added to the processing queue.
 * 
 * @see Command
 * @see java.awt.event.KeyEvent
 * 
 * @author Alessandro Rebosio
 */
public class InputManager implements Input {

    /**
     * The default key bindings for common game commands.
     * Can be modified at runtime via {@link #bindKey(int, Command)}.
     */
    private final Map<Integer, Command> keyBindings;

    /**
     * Thread-safe queue for storing incoming input events before processing.
     * Uses a non-blocking concurrent implementation suitable for game loops.
     */
    private final Queue<Integer> queue;

    /**
     * Constructs a new InputManager with default key bindings.
     * Initializes with the following default bindings:
     * <ul>
     * <li>ENTER → {@link Command#ENTER}
     * <li>ESCAPE → {@link Command#ESC}
     * <li>QUIT → {@link Command#QUIT}
     * <li>Arrow keys → corresponding movement commands
     * <li>JUMP → {@link Command#JUMP}
     * </ul>
     */
    public InputManager() {
        this.keyBindings = new HashMap<>();
        this.queue = new ConcurrentLinkedQueue<>();

        initializeDefaultBindings();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Command getCommand() {
        return this.keyBindings.get(this.queue.poll());
    }

    /**
     * Adds a new command to the queue for processing.
     *
     * @param keyEvent the command to be added to the queue
     */
    @Override
    public void notifyInput(final int keyEvent) {
        this.queue.add(keyEvent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Command bindKey(final int keyCode, final Command command) {
        Objects.requireNonNull(command, "The command connt be null");
        return this.keyBindings.put(keyCode, command);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearQueue() {
        this.queue.clear();
    }

    /**
     * Initializes the default key bindings for common game controls.
     */
    private void initializeDefaultBindings() {
        this.keyBindings.put(KeyEvent.VK_ENTER, Command.ENTER);
        this.keyBindings.put(KeyEvent.VK_ESCAPE, Command.ESC);
        this.keyBindings.put(KeyEvent.VK_Q, Command.QUIT);
        this.keyBindings.put(KeyEvent.VK_UP, Command.MOVE_UP);
        this.keyBindings.put(KeyEvent.VK_DOWN, Command.MOVE_DOWN);
        this.keyBindings.put(KeyEvent.VK_LEFT, Command.MOVE_LEFT);
        this.keyBindings.put(KeyEvent.VK_RIGHT, Command.MOVE_RIGHT);
        this.keyBindings.put(KeyEvent.VK_SPACE, Command.JUMP);
    }

}
