package it.unibo.coffebreak.impl.controller.input;

import java.awt.event.KeyEvent;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
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
     * <li>Arrow keys → movement commands</li>
     * <li>SPACE → {@link Command#JUMP}</li>
     * </ul>
     */
    public InputManager() {
        this.keyBindings.put(KeyEvent.VK_ENTER, Command.ENTER);
        this.keyBindings.put(KeyEvent.VK_ESCAPE, Command.ESCAPE);
        this.keyBindings.put(KeyEvent.VK_UP, Command.MOVE_UP);
        this.keyBindings.put(KeyEvent.VK_DOWN, Command.MOVE_DOWN);
        this.keyBindings.put(KeyEvent.VK_LEFT, Command.MOVE_LEFT);
        this.keyBindings.put(KeyEvent.VK_RIGHT, Command.MOVE_RIGHT);
        this.keyBindings.put(KeyEvent.VK_SPACE, Command.JUMP);
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
     * Registers a key press event and queues the associated command if not already
     * active.
     * 
     * @param keyCode the key code that was pressed
     */
    @Override
    public void keyPressed(final int keyCode) {
        final Command command = keyBindings.get(keyCode);
        if (command != null && this.pressedKeys.add(command)) {
            this.commandQueue.add(command);
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
        this.pressedKeys.remove(this.keyBindings.get(keyCode));
    }

    /**
     * Clears all pending commands from the input queue.
     */
    @Override
    public void clearQueue() {
        this.commandQueue.clear();
        this.pressedKeys.clear();
    }
}
