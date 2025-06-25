package it.unibo.coffebreak.controller;

import static org.junit.jupiter.api.Assertions.*;

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
public class TestGameController {

    private GameController controller;

    @BeforeEach
    void setUp() {
        controller = new GameController();
    }

    /**
     * Tests that processInput does not throw when no command is present.
     */
    @Test
    void testProcessInputNoCommand() {
        assertDoesNotThrow(() -> controller.processInput());
    }

    /**
     * Tests that updateModel does not throw for a typical deltaTime.
     */
    @Test
    void testUpdateModel() {
        assertDoesNotThrow(() -> controller.updateModel(0.016f));
    }

    /**
     * Tests that updateGameBounds does not throw for typical values.
     */
    @Test
    void testUpdateGameBounds() {
        assertDoesNotThrow(() -> controller.updateGameBounds(800, 600));
    }

    /**
     * Tests that keyPressed and keyReleased do not throw for valid key codes.
     */
    @Test
    void testKeyPressedAndReleased() {
        assertDoesNotThrow(() -> controller.keyPressed(10));
        assertDoesNotThrow(() -> controller.keyReleased(10));
    }

    /**
     * Tests that getEntities returns a non-null list.
     */
    @Test
    void testGetEntities() {
        List<Entity> entities = controller.getEntities();
        assertNotNull(entities);
    }

    /**
     * Tests that getScoreValue, getBonusValue, getHighestScore, getLevelIndex
     * return an int.
     */
    @Test
    void testGetIntValues() {
        assertDoesNotThrow(() -> controller.getBonusValue());
        assertDoesNotThrow(() -> controller.getHighestScore());
        assertDoesNotThrow(() -> controller.getLevelIndex());
    }

    /**
     * Tests that getLeaderBoard returns a non-null list.
     */
    @Test
    void testGetLeaderBoard() {
        List<Entry> leaderboard = controller.getLeaderBoard();
        assertNotNull(leaderboard);
    }

    /**
     * Tests that getGameState returns a ModelState (can be null if not
     * initialized).
     */
    @Test
    void testGetGameState() {
        assertDoesNotThrow(() -> controller.getGameState());
    }

    /**
     * Tests that isGameActive returns a boolean.
     */
    @Test
    void testIsGameActive() {
        assertDoesNotThrow(() -> controller.isGameActive());
    }
}
