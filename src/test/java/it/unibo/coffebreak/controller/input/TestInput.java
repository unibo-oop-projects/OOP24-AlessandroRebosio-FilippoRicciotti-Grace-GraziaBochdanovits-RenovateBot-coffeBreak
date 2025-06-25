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
     * Tests that key presses are correctly converted to commands
     * and added to the queue.
     */
    @Test
    void testRegisterKeyPress() {
        inputManager.keyPressed(KeyEvent.VK_UP);
        assertEquals(Command.MOVE_UP, inputManager.getCommand());

        inputManager.keyPressed(KeyEvent.VK_SPACE);
        assertEquals(Command.JUMP, inputManager.getCommand());
    }

    /**
     * Tests that pressing an unbound key doesn't add anything to the queue.
     */
    @Test
    void testUnboundKeyPress() {
        inputManager.keyPressed(KeyEvent.VK_F1);
        assertNull(inputManager.getCommand());
    }

    /**
     * Tests the command queue behavior with multiple inputs.
     */
    @Test
    void testCommandQueueOrder() {
        inputManager.keyPressed(KeyEvent.VK_LEFT);
        inputManager.keyPressed(KeyEvent.VK_RIGHT);
        inputManager.keyPressed(KeyEvent.VK_SPACE);

        assertEquals(Command.MOVE_LEFT, inputManager.getCommand());
        assertEquals(Command.MOVE_RIGHT, inputManager.getCommand());
        assertEquals(Command.JUMP, inputManager.getCommand());
        assertNull(inputManager.getCommand());
    }

    /**
     * Tests that the queue can be properly cleared.
     */
    @Test
    void testClearQueue() {
        inputManager.keyPressed(KeyEvent.VK_ENTER);
        inputManager.keyPressed(KeyEvent.VK_ESCAPE);
        inputManager.clearQueue();

        assertNull(inputManager.getCommand());
    }

}
