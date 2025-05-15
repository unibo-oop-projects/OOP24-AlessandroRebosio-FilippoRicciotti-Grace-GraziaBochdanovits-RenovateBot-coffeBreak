package it.unibo.coffebreak.controller.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.controller.impl.input.InputManager;

/**
 * Test class for {@link InputManager} that verifies its functionality for
 * handling and processing user input commands.
 * 
 * @author Alessandro Rebosio
 */
class TestInput {

    /** The InputManager instance under test. */
    private InputManager inputManager;

    /**
     * Initializes a new InputManager instance before each test method execution.
     * This ensures test isolation and a clean state for each test case.
     */
    @BeforeEach
    void setUp() {
        this.inputManager = new InputManager();
    }

    /**
     * Tests the basic command notification and retrieval functionality.
     * 
     * <p>
     * Verifies that:
     * <ol>
     * <li>Commands are properly queued in FIFO order</li>
     * <li>The correct Command objects are returned</li>
     * <li>The queue is empty after retrieving all commands</li>
     * </ol>
     */
    @Test
    void testNotifyAndGetCommand() {
        this.inputManager.notifyInput(KeyEvent.VK_UP);
        this.inputManager.notifyInput(KeyEvent.VK_DOWN);

        assertEquals(Command.MOVE_UP, this.inputManager.getCommand());
        assertEquals(Command.MOVE_DOWN, this.inputManager.getCommand());
        assertNull(this.inputManager.getCommand());
    }

    /**
     * Tests the key binding functionality of the InputManager.
     * 
     * <p>
     * Verifies that:
     * <ol>
     * <li>New key bindings can be added at runtime</li>
     * <li>Bound keys produce the expected commands</li>
     * <li>Invalid parameters throw appropriate exceptions</li>
     * </ol>
     */
    @Test
    void testBindKey() {
        this.inputManager.bindKey(KeyEvent.VK_W, Command.JUMP);
        this.inputManager.notifyInput(KeyEvent.VK_W);

        assertEquals(Command.JUMP, this.inputManager.getCommand());

        assertThrows(NullPointerException.class, () -> {
            this.inputManager.bindKey(-1, null);
        });
    }

    /**
     * Tests the queue clearing functionality.
     * 
     * <p>
     * Verifies that:
     * <ol>
     * <li>The clearQueue() method removes all pending commands</li>
     * <li>Subsequent getCommand() calls return null</li>
     * </ol>
     */
    @Test
    void testClearQueue() {
        this.inputManager.notifyInput(KeyEvent.VK_SPACE);
        this.inputManager.notifyInput(KeyEvent.VK_LEFT);
        this.inputManager.clearQueue();

        assertNull(this.inputManager.getCommand());
    }

    /**
     * Tests the behavior with unknown/invalid key codes.
     * 
     * <p>
     * Verifies that:
     * <ol>
     * <li>Unknown key codes don't produce commands</li>
     * <li>The system handles invalid input gracefully</li>
     * </ol>
     */
    @Test
    void testUnknownKeyCodeReturnsNull() {
        this.inputManager.notifyInput(-1);
        assertNull(this.inputManager.getCommand());
    }
}
