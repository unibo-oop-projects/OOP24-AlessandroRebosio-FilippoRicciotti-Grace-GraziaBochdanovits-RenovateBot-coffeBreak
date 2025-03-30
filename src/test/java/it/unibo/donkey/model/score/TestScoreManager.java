package it.unibo.donkey.model.score;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.coffeBreak.model.score.api.Entry;
import it.unibo.coffeBreak.model.score.api.ScoreManager;
import it.unibo.coffeBreak.model.score.impl.GameScoreManager;
import it.unibo.coffeBreak.model.score.impl.ScoreEntry;
import java.util.List;

/**
 * Test class for {@link ScoreManager} interface and {@link GameScoreManager} implementation.
 */
public class TestScoreManager {

    /** The score manager instance under test. */
    private ScoreManager<Entry> scoreManager;

    /** Test player name used for entries. */
    private static final String TEST_PLAYER = "Player1";

    /** Test score values used for testing. */
    private static final int TEST_SCORE_SMALL = 100;
    private static final int TEST_SCORE_MEDIUM = 500;
    private static final int TEST_SCORE_LARGE = 1000;
    private static final int TEST_SCORE_XLARGE = 2000;

    /**
     * Initializes the test environment before each test.
     */
    @BeforeEach
    void init() {
        this.scoreManager = new GameScoreManager();
    }

    /**
     * Tests the initial score state.
     * Verifies:
     * - New score manager starts with 0 points
     */
    @Test
    void testGetCurrentScore() {
        assertEquals(0, scoreManager.getCurrentScore(), "Initial score should be 0");
    }

    /**
     * Tests the initial bonus state.
     * Verifies:
     * - New score manager starts with 0 bonus
     */
    @Test
    void testGetCurrentBonus() {
        assertEquals(0, scoreManager.getCurrentBonus(), "Initial bonus should be 0");
    }

    /**
     * Tests the points earning mechanism.
     * Verifies:
     * - Points are correctly added to current score
     */
    @Test
    void testEarnPoints() {
        scoreManager.earnPoints(TEST_SCORE_MEDIUM);
        assertEquals(TEST_SCORE_MEDIUM, scoreManager.getCurrentScore(), 
            "Score should match earned points");
    }

    /**
     * Tests the bonus calculation.
     * Verifies:
     * - Default bonus calculation behavior
     */
    @Test
    void testCalculateBonus() {
        scoreManager.calculateBonus();
        assertEquals(0, scoreManager.getCurrentBonus(), 
            "Bonus should remain 0 after calculation with no setup");
    }

    /**
     * Tests the map completion mechanism.
     * Verifies:
     * - Current bonus is correctly converted to points
     * - Total score is properly updated
     */
    @Test
    void testEndMap() {
        scoreManager.earnPoints(TEST_SCORE_SMALL);
        scoreManager.calculateBonus();
        int scoreBeforeEnd = scoreManager.getCurrentScore();
        scoreManager.endMap();
        assertEquals(scoreBeforeEnd + scoreManager.getCurrentBonus(), 
            scoreManager.getCurrentScore(), 
            "Score should include bonus points after map completion");
    }

    /**
     * Tests the game completion mechanism.
     * Verifies:
     * - Entry is correctly added to leaderboard
     * - Entry data is preserved
     */
    @Test
    void testEndGame() {
        Entry entry = new ScoreEntry(TEST_PLAYER, TEST_SCORE_LARGE);
        scoreManager.endGame(entry);

        List<Entry> leaderBoard = scoreManager.getLeaderBoard();
        assertTrue(leaderBoard.contains(entry), 
            "Leaderboard should contain the added entry");
        assertEquals(TEST_SCORE_LARGE, entry.getScore(), 
            "Entry score should match the provided value");
    }

    /**
     * Tests the highest score retrieval.
     * Verifies:
     * - Correctly identifies the highest score in leaderboard
     * - Handles multiple entries properly
     */
    @Test
    void testGetHighestScore() {
        Entry entry1 = new ScoreEntry(TEST_PLAYER, TEST_SCORE_LARGE);
        Entry entry2 = new ScoreEntry("Player2", TEST_SCORE_XLARGE);
        scoreManager.endGame(entry1);
        scoreManager.endGame(entry2);

        assertEquals(TEST_SCORE_XLARGE, scoreManager.getHighestScore(), 
            "Should return the highest score from leaderboard");
    }

}
