package it.unibo.coffebreak.impl.controller.input;

import java.awt.event.KeyEvent;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.controller.input.Input;

/**
 * Thread-safe implementation of {@link Input} for managing keyboard input.
 * <p>
 * Handles key-to-command bindings, input event queuing, and press/release state
 * tracking.
 * Designed for use in the Controller layer of MVC.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public class InputManager implements Input {

    private final Set<Command> pressedKeys = EnumSet.noneOf(Command.class);
    private final Queue<Command> commandQueue = new ConcurrentLinkedQueue<>();
    private final Map<Integer, Command> keyBindings = new HashMap<>();

    /**
     * Constructs a new InputManager with default key bindings.
     * <ul>
     * <li>ENTER → {@link Command#ENTER}</li>
     * <li>ESCAPE → {@link Command#ESCAPE}</li>
     * <li>Q → {@link Command#QUIT}</li>
     * <li>Arrow keys → movement commands</li>
     * <li>SPACE → {@link Command#JUMP}</li>
     * </ul>
     */
    public InputManager() {
        keyBindings.put(KeyEvent.VK_ENTER, Command.ENTER);
        keyBindings.put(KeyEvent.VK_ESCAPE, Command.ESCAPE);
        keyBindings.put(KeyEvent.VK_UP, Command.MOVE_UP);
        keyBindings.put(KeyEvent.VK_DOWN, Command.MOVE_DOWN);
        keyBindings.put(KeyEvent.VK_LEFT, Command.MOVE_LEFT);
        keyBindings.put(KeyEvent.VK_RIGHT, Command.MOVE_RIGHT);
        keyBindings.put(KeyEvent.VK_SPACE, Command.JUMP);
    }

    /**
     * Retrieves and removes the next command from the queue.
     * 
     * @return the next available command, or {@code null} if the queue is empty
     */
    @Override
    public Command getCommand() {
        return this.commandQueue.poll();
    }

    /**
     */
    @Override
    public Command getDirection() {
        final boolean right = pressedKeys.contains(Command.MOVE_RIGHT);
        final boolean left = pressedKeys.contains(Command.MOVE_LEFT);
        final boolean up = pressedKeys.contains(Command.MOVE_UP);
        final boolean down = pressedKeys.contains(Command.MOVE_DOWN);

        if (right ^ left) {
            return right ? Command.MOVE_RIGHT : Command.MOVE_LEFT;
        }
        if (up ^ down && !(right || left)) {
            return up ? Command.MOVE_UP : Command.MOVE_DOWN;
        }
        return Command.NONE;
    }

    /**
     * Registers a key press event and queues the associated command if not already
     * active.
     * 
     * @param keyCode the key code that was pressed
     */
    @Override
    public void keyPressed(final int keyCode) {
        final Command command = keyBindings.get(keyCode);
        if (command != null && pressedKeys.add(command)) {
            commandQueue.add(command);
        }
    }

    /**
     * Registers a key release event and removes the associated command from the
     * active set.
     * 
     * @param keyCode the key code that was released
     */
    @Override
    public void keyReleased(final int keyCode) {
        final Command command = keyBindings.get(keyCode);
        if (command != null) {
            pressedKeys.remove(command);
        }
    }

    /**
     * Binds a physical key to a logical game command.
     * 
     * @param keyCode the key code to bind
     * @param command the command to associate with the key
     * @return the previously bound command, or {@code null} if the key was unbound
     * @throws NullPointerException if {@code command} is null
     */
    @Override
    public Command bindKey(final int keyCode, final Command command) {
        Objects.requireNonNull(command, "Command cannot be null");
        return keyBindings.put(keyCode, command);
    }

    /**
     * Clears all pending commands from the input queue.
     */
    @Override
    public void clearQueue() {
        commandQueue.clear();
    }
}
