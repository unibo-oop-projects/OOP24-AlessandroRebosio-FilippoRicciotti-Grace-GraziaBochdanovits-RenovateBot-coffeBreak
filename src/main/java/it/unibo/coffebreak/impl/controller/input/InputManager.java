package it.unibo.coffebreak.impl.controller.input;

import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.Objects;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.controller.input.Input;

/**
 * A thread-safe implementation of {@link Input} that manages keyboard input
 * through a command queue and key binding system.
 * 
 * <p>
 * This implementation features:
 * <ul>
 * <li>Key-to-command bindings configuration</li>
 * <li>Input event queuing</li>
 * <li>Press/release state tracking</li>
 * </ul>
 * <p>
 * Uses ConcurrentLinkedQueue for thread-safe command processing and
 * prevents opposite direction commands from being active simultaneously.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public class InputManager implements Input {

    private final Map<Integer, Command> keyBindings;
    private final Set<Command> pressedKeys;
    private final Queue<Command> commandQueue;

    /**
     * Constructs a new InputManager with default key bindings.
     * Initializes with the following default bindings:
     * <ul>
     * <li>ENTER → {@link Command#ENTER}
     * <li>ESCAPE → {@link Command#ESCAPE}
     * <li>QUIT → {@link Command#QUIT}
     * <li>Arrow keys → corresponding movement commands
     * <li>JUMP → {@link Command#JUMP}
     * </ul>
     */
    public InputManager() {
        this.keyBindings = new HashMap<>();
        this.pressedKeys = EnumSet.noneOf(Command.class);
        this.commandQueue = new ConcurrentLinkedQueue<>();
        this.initializeDefaultBindings();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves and removes the next command from the queue.
     * Returns {@code null} if no commands are available.
     */
    @Override
    public Command getCommand() {
        return this.commandQueue.poll();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Processing logic:
     * <ol>
     * <li>Looks up command bound to the key</li>
     * <li>Verifies the inverse command isn't active</li>
     * <li>Adds to active commands set</li>
     * <li>Queues the command for processing</li>
     * </ol>
     */
    @Override
    public void registerKeyPress(final int keyCode) {
        final Command command = this.keyBindings.get(keyCode);
        if (command != null && !this.pressedKeys.contains(command.getInverseDirection())) {
            this.pressedKeys.add(command);
            this.commandQueue.add(command);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Removes the command associated with the released key
     * from the active commands set.
     */
    @Override
    public void registerKeyRelease(final int keyCode) {
        this.pressedKeys.remove(keyBindings.get(keyCode));
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if the command parameter is null
     */
    @Override
    public Command bindKey(final int keyCode, final Command command) {
        Objects.requireNonNull(command, "Command cannot be null");
        return this.keyBindings.put(keyCode, command);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Clears all pending commands while maintaining
     * current key bindings and active key states.
     */
    @Override
    public void clearQueue() {
        this.commandQueue.clear();
    }

    /**
     * Initializes the default key bindings for common game controls.
     */
    private void initializeDefaultBindings() {
        this.keyBindings.put(KeyEvent.VK_ENTER, Command.ENTER);
        this.keyBindings.put(KeyEvent.VK_ESCAPE, Command.ESCAPE);
        this.keyBindings.put(KeyEvent.VK_Q, Command.QUIT);
        this.keyBindings.put(KeyEvent.VK_UP, Command.MOVE_UP);
        this.keyBindings.put(KeyEvent.VK_DOWN, Command.MOVE_DOWN);
        this.keyBindings.put(KeyEvent.VK_LEFT, Command.MOVE_LEFT);
        this.keyBindings.put(KeyEvent.VK_RIGHT, Command.MOVE_RIGHT);
        this.keyBindings.put(KeyEvent.VK_SPACE, Command.JUMP);
    }
}
