package it.unibo.coffebreak.model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
	private static final int TEST_SCORE_ZERO = 0;
	private static final int TEST_SCORE_SMALL = 100;
	private static final int TEST_SCORE_MEDIUM = 500;
	private static final int TEST_SCORE_LARGE = 1000;
	private static final int TEST_SCORE_XLARGE = 2000;
	private static final int TEST_BONUS_VALUE = 250;

	/** The score manager instance under test. */
	private ScoreManager<Entry> scoreManager;

	/**
	 * Initializes the test environment before each test.
	 */
	@BeforeEach
	void init() throws IOException {
		this.scoreManager = new GameScoreManager();
		Files.deleteIfExists(Paths.get(TestRepository.FILE_PATH));
	}

	/**
	 * Tests the initial score state.
	 * Verifies:
	 * - New score manager starts with 0 points
	 */
	@Test
	void testGetCurrentScore() {
		assertEquals(TEST_SCORE_ZERO, this.scoreManager.getCurrentScore(), "Initial score should be 0");
	}

	/**
	 * Tests the initial bonus state.
	 * Verifies:
	 * - New score manager starts with 0 bonus
	 */
	@Test
	void testGetCurrentBonus() {
		assertEquals(TEST_SCORE_ZERO, this.scoreManager.getCurrentBonus(), "Initial bonus should be 0");
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
		assertEquals(TEST_SCORE_ZERO, this.scoreManager.getCurrentBonus(),
				"Bonus should remain 0 after calculation with no setup");
	}

	/**
	 * Tests the bonus calculation with map initialization.
	 * Verifies:
	 * - Bonus is correctly set and retrieved
	 */
	@Test
	void testBonusCalculationWithMap() {
		this.scoreManager.startMap(TEST_BONUS_VALUE);
		assertEquals(TEST_BONUS_VALUE, this.scoreManager.getCurrentBonus(),
				"Bonus should match the initialized value");

		this.scoreManager.calculateBonus();
		assertTrue(this.scoreManager.getCurrentBonus() >= 0,
				"Calculated bonus should be non-negative");
	}

	/**
	 * Tests the map completion mechanism.
	 * Verifies:
	 * - Current bonus is correctly converted to points
	 * - Bonus is reset after map completion
	 * - Total score is properly updated
	 */
	@Test
	void testMapCompletion() {
		this.scoreManager.earnPoints(TEST_SCORE_SMALL);
		this.scoreManager.startMap(TEST_BONUS_VALUE);

		final int scoreBeforeEnd = this.scoreManager.getCurrentScore();
		final int bonusBeforeEnd = this.scoreManager.getCurrentBonus();

		this.scoreManager.endMap();

		assertEquals(scoreBeforeEnd + bonusBeforeEnd, this.scoreManager.getCurrentScore(),
				"Score should include bonus points after map completion");
	}

	/**
	 * Tests that ending a game with invalid player names throws exceptions.
	 */
	@Test
	void testGameCompletionWithInvalidNames() {
		this.scoreManager.earnPoints(TEST_SCORE_SMALL);

		assertThrows(NullPointerException.class,
				() -> scoreManager.endGame(null),
				"Should throw IllegalArgumentException for null name");

		assertThrows(IllegalArgumentException.class,
				() -> scoreManager.endGame(""),
				"Should throw IllegalArgumentException for empty name");

		assertThrows(IllegalArgumentException.class,
				() -> scoreManager.endGame("   "),
				"Should throw IllegalArgumentException for whitespace name");
	}

	/**
	 * Tests the leaderboard ordering and highest score retrieval.
	 * Verifies:
	 * - Leaderboard maintains entries in descending order
	 * - Correctly identifies the highest score
	 * - Handles empty leaderboard case
	 */
	@Test
	void testLeaderboardOrderingAndHighestScore() {
		final Entry entry1 = new ScoreEntry(TEST_PLAYER, TEST_SCORE_LARGE);
		final Entry entry2 = new ScoreEntry("Player2", TEST_SCORE_XLARGE);
		final Entry entry3 = new ScoreEntry("Player3", TEST_SCORE_MEDIUM);

		this.scoreManager.earnPoints(entry2.getScore());
		this.scoreManager.endGame(entry2.getName());

		this.scoreManager.earnPoints(entry3.getScore());
		this.scoreManager.endGame(entry3.getName());

		this.scoreManager.earnPoints(entry1.getScore());
		this.scoreManager.endGame(entry1.getName());

		final List<Entry> leaderboard = this.scoreManager.getLeaderBoard();
		assertEquals(TEST_SCORE_XLARGE, leaderboard.get(0).getScore(),
				"Highest score should be first in leaderboard");
		assertEquals(TEST_SCORE_LARGE, leaderboard.get(1).getScore(),
				"Second highest score should be second");
		assertEquals(TEST_SCORE_MEDIUM, leaderboard.get(2).getScore(),
				"Lowest score should be last");

		assertEquals(TEST_SCORE_XLARGE, this.scoreManager.getHighestScore(),
				"getHighestScore should return the top leaderboard entry");
	}

	/**
	 * Tests the full workflow including map completion and game ending.
	 */
	@Test
	void testFullWorkflow() {
		this.scoreManager.startMap(TEST_BONUS_VALUE);
		this.scoreManager.earnPoints(TEST_SCORE_SMALL);
		this.scoreManager.endMap();

		this.scoreManager.startMap(TEST_BONUS_VALUE / 2);
		this.scoreManager.earnPoints(TEST_SCORE_MEDIUM);
		this.scoreManager.endMap();

		final int expectedTotal = TEST_SCORE_SMALL + TEST_BONUS_VALUE + TEST_SCORE_MEDIUM + (TEST_BONUS_VALUE / 2);
		this.scoreManager.endGame(TEST_PLAYER);

		final Entry expectedEntry = new ScoreEntry(TEST_PLAYER, expectedTotal);
		assertTrue(this.scoreManager.getLeaderBoard().contains(expectedEntry),
				"Leaderboard should contain entry with correct total score");
	}
}
