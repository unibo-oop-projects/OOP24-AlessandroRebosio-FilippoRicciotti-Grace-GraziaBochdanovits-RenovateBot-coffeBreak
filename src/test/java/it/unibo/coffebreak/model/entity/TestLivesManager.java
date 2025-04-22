package it.unibo.coffebreak.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.impl.entity.LivesManager;

/**
 * Test class for {@link LivesManager}, verifying its life management logic.
 * Covers standard operations, edge cases, and state transitions.
 */
class TestLivesManager {

    private LivesManager livesManager;

    /**
     * Initializes a new LivesManager instance before each test.
     */
    @BeforeEach
    void setUp() {
        livesManager = new LivesManager(); 
    }

    /**
     * Tests the initial state of LivesManager.
     * Verifies that:
     * <ul>
     *   <li>Initial lives count is 3</li>
     *   <li>Game is not over at startup</li>
     * </ul>
     */
    @Test
    void testInitialSetUp() {
        assertEquals(3, livesManager.getLives());
        assertFalse(livesManager.isGameOver());
    }

    /**
     * Tests standard life loss operation.
     * Verifies that:
     * <ul>
     *   <li>loseLife() decrements the counter by 1</li>
     *   <li>Game doesn't end after losing one life</li>
     * </ul>
     */
    @Test
    void testLoseLife() {
        livesManager.loseLife();
        assertEquals(2, livesManager.getLives());
        assertFalse(livesManager.isGameOver());
    }

    /**
     * Tests life loss boundary conditions.
     * Verifies that:
     * <ul>
     *   <li>Life counter reaches zero after 3 decrements</li>
     *   <li>Game enters over state at zero lives</li>
     *   <li>Counter doesn't go below zero</li>
     * </ul>
     */
    @Test
    void testLoseLifeAtZeroLives() {
        for (int i = 0; i < 3; i++) {
            livesManager.loseLife();
        }
        assertEquals(0, livesManager.getLives());
        assertTrue(livesManager.isGameOver());

        livesManager.loseLife();
        assertEquals(0, livesManager.getLives()); 
    }

    /**
     * Tests the reset functionality.
     * Verifies that:
     * <ul>
     *   <li>reset() restores the initial lives count (3)</li>
     * </ul>
     */
    @Test
    void testReset() {
        livesManager.loseLife();
        livesManager.reset();
        assertEquals(3, livesManager.getLives());
    }

    /**
     * Tests game over state transition.
     * Verifies that:
     * <ul>
     *   <li>Game starts in non-over state</li>
     *   <li>After losing all lives, game enters over state</li>
     * </ul>
     */
    @Test
    void testGameOver() {
        assertFalse(livesManager.isGameOver());
        for (int i = 0; i < 3; i++) {
            livesManager.loseLife();
        }
        assertEquals(0, livesManager.getLives());
        assertTrue(livesManager.isGameOver());
    }

    /**
     * Tests reset operation after game over.
     * Verifies that:
     * <ul>
     *   <li>reset() works correctly after game over</li>
     *   <li>Both lives counter and game state are properly restored</li>
     * </ul>
     */
    @Test
    void testResetAfterGameOver() {
        for (int i = 0; i < 3; i++) {
            livesManager.loseLife();
        }
        livesManager.reset();
        assertEquals(3, livesManager.getLives());
        assertFalse(livesManager.isGameOver()); 
    }
}
