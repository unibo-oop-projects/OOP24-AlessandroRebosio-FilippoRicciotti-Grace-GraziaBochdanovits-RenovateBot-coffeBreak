package it.unibo.coffebreak.model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.api.score.Entry;
import it.unibo.coffebreak.model.api.score.ScoreManager;
import it.unibo.coffebreak.model.impl.score.GameScoreManager;

/**
 * Comprehensive test suite for {@link ScoreManager} interface and
 * {@link GameScoreManager} implementation.
 * 
 * <p>
 * Tests verify:
 * <ul>
 * <li>Score and bonus management</li>
 * <li>Map lifecycle operations</li>
 * <li>Game session handling</li>
 * <li>Leaderboard functionality</li>
 * <li>Error conditions and edge cases</li>
 * </ul>
 * 
 * @author Alessandro Rebosio
 */
class TestScoreManager {

    private static final String TEST_PLAYER = "Player1";
    private static final String TEST_PLAYER_2 = "Player2";
    private static final String TEST_PLAYER_3 = "Player3";
    private static final String EMPTY_NAME = "";
    private static final String WHITESPACE_NAME = "   ";

    private static final int SCORE_ZERO = 0;
    private static final int SCORE_SMALL = 100;
    private static final int SCORE_MEDIUM = 500;
    private static final int SCORE_LARGE = 1000;
    private static final int SCORE_XLARGE = 2000;
    private static final int BONUS_VALUE = 250;

    private ScoreManager<Entry> scoreManager;

    /**
     * Initializes a fresh GameScoreManager and cleans up test files before each
     * test.
     */
    @BeforeEach
    void setUp() {
        tearDown();
        this.scoreManager = new GameScoreManager();
    }

    /**
     * Final cleanup after all tests complete.
     * 
     */
    @AfterAll
    static void tearDown() {
        TestRepository.tearDown();
    }

    /**
     * Tests initial state of a new score manager.
     */
    @Test
    void shouldInitializeWithZeroScoreAndBonus() {
        assertEquals(SCORE_ZERO, scoreManager.getCurrentScore());
        assertEquals(SCORE_ZERO, scoreManager.getCurrentBonus());
    }

    /**
     * Tests point earning functionality with valid and invalid amounts.
     */
    @Test
    void shouldAccumulatePointsAndRejectNegativeValues() {
        scoreManager.earnPoints(SCORE_SMALL);
        assertEquals(SCORE_SMALL, scoreManager.getCurrentScore());

        scoreManager.earnPoints(SCORE_MEDIUM);
        assertEquals(SCORE_SMALL + SCORE_MEDIUM, scoreManager.getCurrentScore());

        assertThrows(IllegalArgumentException.class,
                () -> scoreManager.earnPoints(-SCORE_SMALL),
                "Should reject negative points");
    }

    /**
     * Tests bonus calculation with different initial values.
     */
    @Test
    void shouldCalculateBonusCorrectly() {
        scoreManager.startMap(BONUS_VALUE);
        scoreManager.calculateBonus();
        assertTrue(scoreManager.getCurrentBonus() < BONUS_VALUE,
                "Bonus should decrease after calculation");

        scoreManager.startMap(SCORE_SMALL);
        for (int i = 0; i < 10; i++) {
            scoreManager.calculateBonus();
        }
        assertEquals(SCORE_ZERO, scoreManager.getCurrentBonus(),
                "Bonus should not go below zero");
    }

    /**
     * Tests complete map lifecycle including bonus conversion.
     */
    @Test
    void shouldConvertBonusToPointsAtMapEnd() {
        scoreManager.startMap(BONUS_VALUE);
        final int initialBonus = scoreManager.getCurrentBonus();

        scoreManager.earnPoints(SCORE_MEDIUM);
        final int scoreBeforeEnd = scoreManager.getCurrentScore();

        scoreManager.endMap();

        assertEquals(scoreBeforeEnd + initialBonus, scoreManager.getCurrentScore());
    }

    /**
     * Tests game completion with various invalid player names.
     */
    @Test
    void shouldRejectInvalidPlayerNames() {
        assertThrows(NullPointerException.class,
                () -> scoreManager.endGame(null),
                "Should reject null player name");

        assertThrows(IllegalArgumentException.class,
                () -> scoreManager.endGame(EMPTY_NAME),
                "Should reject empty player name");

        assertThrows(IllegalArgumentException.class,
                () -> scoreManager.endGame(WHITESPACE_NAME),
                "Should reject whitespace-only player name");
    }

    /**
     * Tests leaderboard ordering and highest score functionality.
     */
    @Test
    void shouldMaintainLeaderboardOrderAndHighestScore() {
        submitScore(TEST_PLAYER, SCORE_LARGE);
        submitScore(TEST_PLAYER_2, SCORE_XLARGE);
        submitScore(TEST_PLAYER_3, SCORE_MEDIUM);

        final List<Entry> leaderboard = scoreManager.getLeaderBoard();
        assertEquals(SCORE_XLARGE, leaderboard.get(0).getScore());
        assertEquals(SCORE_LARGE, leaderboard.get(1).getScore());
        assertEquals(SCORE_MEDIUM, leaderboard.get(2).getScore());

        assertEquals(SCORE_XLARGE, scoreManager.getHighestScore());
    }

    /**
     * Tests complete game workflow with multiple maps.
     */
    @Test
    void shouldHandleCompleteGameWorkflow() {
        // First map
        scoreManager.startMap(BONUS_VALUE);
        scoreManager.earnPoints(SCORE_SMALL);
        scoreManager.endMap();

        // Second map
        scoreManager.startMap(BONUS_VALUE / 2);
        scoreManager.earnPoints(SCORE_MEDIUM);
        scoreManager.endMap();

        final int expectedTotal = SCORE_SMALL + BONUS_VALUE + SCORE_MEDIUM + (BONUS_VALUE / 2);
        scoreManager.endGame(TEST_PLAYER);

        assertTrue(scoreManager.getLeaderBoard().stream()
                .anyMatch(e -> TEST_PLAYER.equals(e.getName()) && e.getScore() == expectedTotal));
    }

    /**
     * Helper method to submit a score to the leaderboard.
     * 
     * @param playerName name of the player
     * @param score      current score
     */
    private void submitScore(final String playerName, final int score) {
        scoreManager.earnPoints(score);
        scoreManager.endGame(playerName);
    }
}
