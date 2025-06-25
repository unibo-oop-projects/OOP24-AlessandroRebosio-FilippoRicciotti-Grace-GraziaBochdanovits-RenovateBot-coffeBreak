package it.unibo.coffebreak.controller.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.impl.controller.input.InputManager;

/**
 * Test class for {@link InputManager}.
 * 
 * <p>
 * This class verifies the behavior of the InputManager implementation,
 * focusing on:
 * <ul>
 * <li>Default key bindings initialization
 * <li>Key press and release handling
 * <li>Command queue management
 * <li>Key binding customization
 * </ul>
 * 
 * <p>
 * Tests are designed to verify both normal operation and edge cases.
 * 
 * @author Alessandro Rebosio
 */
class TestInputManager {

    private InputManager inputManager;

    /**
     * Initializes a fresh GameLeaderBoard instance before each test.
     */
    @BeforeEach
    void setUp() {
        inputManager = new InputManager();
    }

    /**
     * Tests that default key bindings map the correct KeyEvent codes to Commands.
     */
    @Test
    void testDefaultKeyBindings() {
        inputManager.keyPressed(KeyEvent.VK_ENTER);
        assertEquals(Command.ENTER, inputManager.getCommand());
        inputManager.keyPressed(KeyEvent.VK_ESCAPE);
        assertEquals(Command.ESCAPE, inputManager.getCommand());
        inputManager.keyPressed(KeyEvent.VK_UP);
        assertEquals(Command.MOVE_UP, inputManager.getCommand());
        inputManager.keyPressed(KeyEvent.VK_DOWN);
        assertEquals(Command.MOVE_DOWN, inputManager.getCommand());
        inputManager.keyPressed(KeyEvent.VK_LEFT);
        assertEquals(Command.MOVE_LEFT, inputManager.getCommand());
        inputManager.keyPressed(KeyEvent.VK_RIGHT);
        assertEquals(Command.MOVE_RIGHT, inputManager.getCommand());
        inputManager.keyPressed(KeyEvent.VK_SPACE);
        assertEquals(Command.JUMP, inputManager.getCommand());
    }

    /**
     * Tests that pressing the same key multiple times does not queue duplicate
     * commands until released.
     */
    @Test
    void testNoDuplicateCommandWhilePressed() {
        inputManager.keyPressed(KeyEvent.VK_SPACE);
        assertEquals(Command.JUMP, inputManager.getCommand());
        inputManager.keyPressed(KeyEvent.VK_SPACE);
        assertNull(inputManager.getCommand());
        inputManager.keyReleased(KeyEvent.VK_SPACE);
        inputManager.keyPressed(KeyEvent.VK_SPACE);
        assertEquals(Command.JUMP, inputManager.getCommand());
    }

    /**
     * Tests that releasing a key removes it from the pressed set.
     */
    @Test
    void testKeyRelease() {
        inputManager.keyPressed(KeyEvent.VK_LEFT);
        assertEquals(Command.MOVE_LEFT, inputManager.getCommand());
        inputManager.keyReleased(KeyEvent.VK_LEFT);
        inputManager.keyPressed(KeyEvent.VK_LEFT);
        assertEquals(Command.MOVE_LEFT, inputManager.getCommand());
    }

    /**
     * Tests that clearQueue empties the command queue and pressed keys.
     */
    @Test
    void testClearQueue() {
        inputManager.keyPressed(KeyEvent.VK_RIGHT);
        inputManager.keyPressed(KeyEvent.VK_UP);
        inputManager.clearQueue();
        assertNull(inputManager.getCommand());
        inputManager.keyPressed(KeyEvent.VK_RIGHT);
        assertEquals(Command.MOVE_RIGHT, inputManager.getCommand());
    }

    /**
     * Tests that getCommand returns null if the queue is empty.
     */
    @Test
    void testGetCommandEmpty() {
        assertNull(inputManager.getCommand());
    }
}
