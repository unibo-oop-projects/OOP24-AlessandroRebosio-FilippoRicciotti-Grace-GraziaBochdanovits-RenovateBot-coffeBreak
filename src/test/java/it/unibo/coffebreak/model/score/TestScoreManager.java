package it.unibo.coffebreak.model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.score.api.Entry;
import it.unibo.coffebreak.model.score.api.ScoreManager;
import it.unibo.coffebreak.model.score.impl.GameScoreManager;
import it.unibo.coffebreak.model.score.impl.ScoreEntry;

/**
 * Test class for {@link ScoreManager} interface and {@link GameScoreManager}
 * implementation.
 */
class TestScoreManager {

    /** Test player name used for entries. */
    private static final String TEST_PLAYER = "Player1";

    /** Test score values used for testing. */
    private static final int TEST_SCORE_SMALL = 100;
    private static final int TEST_SCORE_MEDIUM = 500;
    private static final int TEST_SCORE_LARGE = 1000;
    private static final int TEST_SCORE_XLARGE = 2000;

    /** The score manager instance under test. */
    private ScoreManager<Entry> scoreManager;

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
        assertEquals(0, this.scoreManager.getCurrentScore(), "Initial score should be 0");
    }

    /**
     * Tests the initial bonus state.
     * Verifies:
     * - New score manager starts with 0 bonus
     */
    @Test
    void testGetCurrentBonus() {
        assertEquals(0, this.scoreManager.getCurrentBonus(), "Initial bonus should be 0");
    }

    /**
     * Tests the points earning mechanism.
     * Verifies:
     * - Points are correctly added to current score
     */
    @Test
    void testEarnPoints() {
        this.scoreManager.earnPoints(TEST_SCORE_MEDIUM);
        assertEquals(TEST_SCORE_MEDIUM, this.scoreManager.getCurrentScore(),
                "Score should match earned points");
        assertThrows(IllegalArgumentException.class, () -> scoreManager.earnPoints(-TEST_SCORE_MEDIUM));
    }

    /**
     * Tests the bonus calculation.
     * Verifies:
     * - Default bonus calculation behavior
     */
    @Test
    void testCalculateBonus() {
        this.scoreManager.calculateBonus();
        assertEquals(0, this.scoreManager.getCurrentBonus(),
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
        this.scoreManager.earnPoints(TEST_SCORE_SMALL);
        this.scoreManager.calculateBonus();
        final int scoreBeforeEnd = this.scoreManager.getCurrentScore();
        this.scoreManager.endMap();
        assertEquals(scoreBeforeEnd + this.scoreManager.getCurrentBonus(),
                this.scoreManager.getCurrentScore(),
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
        final Entry entry = new ScoreEntry(TEST_PLAYER, TEST_SCORE_LARGE);

        this.scoreManager.earnPoints(entry.getScore());
        this.scoreManager.endGame(entry.getName());
        assertTrue(this.scoreManager.getLeaderBoard().contains(entry),
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
        final Entry entry1 = new ScoreEntry(TEST_PLAYER, TEST_SCORE_LARGE);
        final Entry entry2 = new ScoreEntry("Player2", TEST_SCORE_XLARGE);
        this.scoreManager.earnPoints(TEST_SCORE_LARGE);
        this.scoreManager.endGame(entry1.getName());
        this.scoreManager.endGame(entry2.getName());

        assertEquals(TEST_SCORE_XLARGE, this.scoreManager.getHighestScore(),
                "Should return the highest score from leaderboard");
    }
}
