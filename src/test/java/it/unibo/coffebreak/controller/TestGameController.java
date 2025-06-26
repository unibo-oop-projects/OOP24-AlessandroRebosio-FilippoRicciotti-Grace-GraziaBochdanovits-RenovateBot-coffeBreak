package it.unibo.coffebreak.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.leaderboard.entry.Entry;
import it.unibo.coffebreak.impl.controller.GameController;

/**
 * Unit tests for the {@link GameController} class.
 * 
 * @author Alessandro Rebosio
 */
class TestGameController {

    private static final float DEFAULT_DELTA_TIME = 0.016f;
    private static final int TEST_KEY_CODE = 10;

    private GameController controller;

    /**
     * Initializes a fresh GameLeaderBoard instance before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new GameController();
    }

    /**
     * Tests that processInput does not throw when no command is present.
     */
    @Test
    void testProcessInputNoCommand() {
        assertDoesNotThrow(controller::processInput);
    }

    /**
     * Tests that updateModel does not throw for a typical deltaTime.
     */
    @Test
    void testUpdateModel() {
        assertDoesNotThrow(() -> controller.updateModel(DEFAULT_DELTA_TIME));
    }

    /**
     * Tests that keyPressed and keyReleased do not throw for valid key codes.
     */
    @Test
    void testKeyPressedAndReleased() {
        assertDoesNotThrow(() -> controller.keyPressed(TEST_KEY_CODE));
        assertDoesNotThrow(() -> controller.keyReleased(TEST_KEY_CODE));
    }

    /**
     * Tests that getEntities returns a non-null list.
     */
    @Test
    void testGetEntities() {
        final List<Entity> entities = controller.getEntities();
        assertNotNull(entities);
    }

    /**
     * Tests that getScoreValue, getBonusValue, getHighestScore, getLevelIndex
     * return an int.
     */
    @Test
    void testGetIntValues() {
        assertDoesNotThrow(controller::getBonusValue);
        assertDoesNotThrow(controller::getHighestScore);
        assertDoesNotThrow(controller::getLevelIndex);
    }

    /**
     * Tests that getLeaderBoard returns a non-null list.
     */
    @Test
    void testGetLeaderBoard() {
        final List<Entry> leaderboard = controller.getLeaderBoard();
        assertNotNull(leaderboard);
    }

    /**
     * Tests that getGameState returns a ModelState (can be null if not
     * initialized).
     */
    @Test
    void testGetGameState() {
        assertDoesNotThrow(controller::getGameState);
    }

    /**
     * Tests that isGameActive returns a boolean.
     */
    @Test
    void testIsGameActive() {
        assertDoesNotThrow(controller::isGameActive);
    }
}
