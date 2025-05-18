package it.unibo.coffebreak.controller.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.controller.impl.input.InputManager;

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
class TestInput {

    private InputManager inputManager;

    /**
     * Sets up a fresh InputManager instance before each test.
     */
    @BeforeEach
    void setUp() {
        inputManager = new InputManager();
    }

    /**
     * Tests that default key bindings are correctly initialized.
     */
    @Test
    void testDefaultBindings() {
        assertEquals(Command.ENTER, inputManager.bindKey(KeyEvent.VK_ENTER, Command.NONE));
        assertEquals(Command.ESCAPE, inputManager.bindKey(KeyEvent.VK_ESCAPE, Command.NONE));
        assertEquals(Command.QUIT, inputManager.bindKey(KeyEvent.VK_Q, Command.NONE));
        assertEquals(Command.MOVE_UP, inputManager.bindKey(KeyEvent.VK_UP, Command.NONE));
        assertEquals(Command.MOVE_DOWN, inputManager.bindKey(KeyEvent.VK_DOWN, Command.NONE));
        assertEquals(Command.MOVE_LEFT, inputManager.bindKey(KeyEvent.VK_LEFT, Command.NONE));
        assertEquals(Command.MOVE_RIGHT, inputManager.bindKey(KeyEvent.VK_RIGHT, Command.NONE));
        assertEquals(Command.JUMP, inputManager.bindKey(KeyEvent.VK_SPACE, Command.NONE));
    }

    /**
     * Tests that key presses are correctly converted to commands
     * and added to the queue.
     */
    @Test
    void testRegisterKeyPress() {
        inputManager.registerKeyPress(KeyEvent.VK_UP);
        assertEquals(Command.MOVE_UP, inputManager.getCommand());

        inputManager.registerKeyPress(KeyEvent.VK_SPACE);
        assertEquals(Command.JUMP, inputManager.getCommand());
    }

    /**
     * Tests that pressing an unbound key doesn't add anything to the queue.
     */
    @Test
    void testUnboundKeyPress() {
        inputManager.registerKeyPress(KeyEvent.VK_F1);
        assertNull(inputManager.getCommand());
    }

    /**
     * Tests that key releases are properly handled.
     */
    @Test
    void testRegisterKeyRelease() {
        inputManager.registerKeyPress(KeyEvent.VK_UP);
        inputManager.registerKeyRelease(KeyEvent.VK_UP);

        assertEquals(Command.MOVE_UP, inputManager.getCommand());
        assertNull(inputManager.getCommand());
    }

    /**
     * Tests that opposite direction commands can't be pressed simultaneously.
     */
    @Test
    void testOppositeDirections() {
        inputManager.registerKeyPress(KeyEvent.VK_UP);
        inputManager.registerKeyPress(KeyEvent.VK_DOWN);

        assertEquals(Command.MOVE_UP, inputManager.getCommand());
        assertNull(inputManager.getCommand());
    }

    /**
     * Tests the command queue behavior with multiple inputs.
     */
    @Test
    void testCommandQueueOrder() {
        inputManager.registerKeyPress(KeyEvent.VK_LEFT);
        inputManager.registerKeyPress(KeyEvent.VK_RIGHT);
        inputManager.registerKeyPress(KeyEvent.VK_SPACE);

        assertEquals(Command.MOVE_LEFT, inputManager.getCommand());
        assertEquals(Command.JUMP, inputManager.getCommand());
        assertNull(inputManager.getCommand());
    }

    /**
     * Tests that the queue can be properly cleared.
     */
    @Test
    void testClearQueue() {
        inputManager.registerKeyPress(KeyEvent.VK_ENTER);
        inputManager.registerKeyPress(KeyEvent.VK_ESCAPE);
        inputManager.clearQueue();

        assertNull(inputManager.getCommand());
    }

    /**
     * Tests custom key binding functionality.
     */
    @Test
    void testCustomKeyBinding() {
        inputManager.bindKey(KeyEvent.VK_A, Command.MOVE_LEFT);

        inputManager.registerKeyPress(KeyEvent.VK_A);
        assertEquals(Command.MOVE_LEFT, inputManager.getCommand());
    }

    /**
     * Tests that binding a key to null removes the binding.
     */
    @Test
    void testRemoveKeyBinding() {
        inputManager.bindKey(KeyEvent.VK_SPACE, Command.NONE);

        inputManager.registerKeyPress(KeyEvent.VK_SPACE);
        assertEquals(Command.NONE, inputManager.getCommand());
    }

    /**
     * Tests that pressing and releasing a key doesn't leave it in the pressed
     * state.
     */
    @Test
    void testKeyPressReleaseCycle() {
        inputManager.registerKeyPress(KeyEvent.VK_UP);
        inputManager.registerKeyRelease(KeyEvent.VK_UP);
        inputManager.registerKeyPress(KeyEvent.VK_DOWN);

        assertEquals(Command.MOVE_UP, inputManager.getCommand());
        assertEquals(Command.MOVE_DOWN, inputManager.getCommand());
    }
}
