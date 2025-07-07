package it.unibo.coffebreak.model.entities.character.lives;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.model.entities.character.lives.LivesManager;
import it.unibo.coffebreak.impl.model.entities.mario.lives.GameLivesManager;

/**
 * Test class for {@link LivesManager} implementation ({@link GameLivesManager}).
 * Verifies the correct behavior of lives management functionality.
 * 
 * @author Grazia Bochdanovits de kavna
 */
class TestLivesManager {

    private LivesManager livesManager;

    /**
     * Initializes a new LivesManager instance before each test.
     */
    @BeforeEach
    void setUp() {
        livesManager = new GameLivesManager(); 
    }

    /**
     * Tests the initial state of the lives manager.
     */
    @Test
    void testInitialLives() {
        assertEquals(3, livesManager.getLives(), "Initial lives should be 3");
        assertTrue(livesManager.isAlive(), "Character should be alive initially");
    }

    /**
     * Tests losing a single life.
     */
    @Test
    void testLoseLife() {
        livesManager.loseLife();
        assertEquals(2, livesManager.getLives(), "Lives should decrease by 1");
        assertTrue(livesManager.isAlive(), "Character should still be alive");
    }

    /**
     * Tests losing all lives and attempting to lose additional lives.
     */
    @Test
    void testLoseAllLives() {
        livesManager.loseLife();
        livesManager.loseLife();
        livesManager.loseLife();

        assertEquals(0, livesManager.getLives(), "Lives should be 0");
        assertFalse(livesManager.isAlive(), "Character should be dead");

        livesManager.loseLife();
        assertEquals(0, livesManager.getLives(), "Lives should remain 0 when trying to lose life while dead");
    }

    /**
     * Tests resetting lives to initial value.
     */
    @Test
    void testResetLives() {
        livesManager.loseLife();
        livesManager.loseLife();

        livesManager.resetLives();

        assertEquals(3, livesManager.getLives(), "Lives should be reset to 3");
        assertTrue(livesManager.isAlive(), "Character should be alive after reset");
    }
}
