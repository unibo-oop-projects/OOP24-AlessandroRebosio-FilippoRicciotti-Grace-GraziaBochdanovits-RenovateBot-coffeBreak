package it.unibo.coffebreak.model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.api.score.Entry;
import it.unibo.coffebreak.model.impl.score.ScoreEntry;

/**
 * Comprehensive test suite for {@link Entry} interface and {@link ScoreEntry}
 * implementation.
 * 
 * <p>
 * Tests verify:
 * <ul>
 * <li>Constructor validation and preconditions</li>
 * <li>Proper functioning of accessor methods</li>
 * <li>Immutable characteristics of entries</li>
 * <li>Edge cases in entry creation</li>
 * </ul>
 * 
 * @author Alessandro Rebosio
 */
class TestEntry {

    /** Standard test player name. */
    private static final String TEST_NAME = "REB";

    /** Standard test score value. */
    private static final int TEST_SCORE = 1000;

    /** Edge case: empty player name. */
    private static final String EMPTY_NAME = "";

    /** Edge case: minimum score value. */
    private static final int MIN_SCORE = 0;

    /** Edge case: negative score value. */
    private static final int NEGATIVE_SCORE = -1;

    /** The entry instance under test. */
    private Entry entry;

    /**
     * Initializes a fresh ScoreEntry instance before each test.
     * Creates entry with standard test values.
     */
    @BeforeEach
    void init() {
        this.entry = new ScoreEntry(TEST_NAME, TEST_SCORE);
    }

    /**
     * Verifies constructor rejects null names.
     * Checks that:
     * <ul>
     * <li>NullPointerException is thrown</li>
     * <li>Exception contains proper message</li>
     * </ul>
     */
    @Test
    void shouldRejectNullName() {
        final Exception exception = assertThrows(NullPointerException.class,
                () -> new ScoreEntry(null, TEST_SCORE));

        assertEquals("Name cannot be null", exception.getMessage());
    }

    /**
     * Verifies constructor accepts empty names.
     */
    @Test
    void shouldAcceptEmptyName() {
        final Entry emptyNameEntry = new ScoreEntry(EMPTY_NAME, TEST_SCORE);
        assertEquals(EMPTY_NAME, emptyNameEntry.getName());
    }

    /**
     * Verifies constructor accepts minimum score value (0).
     */
    @Test
    void shouldAcceptMinimumScore() {
        final Entry minScoreEntry = new ScoreEntry(TEST_NAME, MIN_SCORE);
        assertEquals(MIN_SCORE, minScoreEntry.getScore());
    }

    /**
     * Verifies constructor accepts negative scores.
     */
    @Test
    void shouldAcceptNegativeScores() {
        final Entry negativeEntry = new ScoreEntry(TEST_NAME, NEGATIVE_SCORE);
        assertEquals(NEGATIVE_SCORE, negativeEntry.getScore());
    }

    /**
     * Tests getName() returns expected value.
     * Verifies:
     * <ul>
     * <li>Returned name matches constructor argument</li>
     * <li>Name comparison is case-sensitive</li>
     * </ul>
     */
    @Test
    void shouldReturnCorrectName() {
        assertEquals(TEST_NAME, entry.getName());

        final Entry lowerCaseEntry = new ScoreEntry("reb", TEST_SCORE);
        assertEquals("reb", lowerCaseEntry.getName());
    }

    /**
     * Tests getScore() returns expected value.
     * Verifies:
     * <ul>
     * <li>Returned score matches constructor argument</li>
     * <li>Different score values are handled correctly</li>
     * </ul>
     */
    @Test
    void shouldReturnCorrectScore() {
        final int expected = 2000;
        assertEquals(TEST_SCORE, entry.getScore());

        final Entry differentScoreEntry = new ScoreEntry(TEST_NAME, 2000);
        assertEquals(expected, differentScoreEntry.getScore());
    }

    /**
     * Verifies entry immutability.
     */
    @Test
    void shouldBeImmutable() {
        assertEquals(TEST_NAME, entry.getName());
        assertEquals(TEST_SCORE, entry.getScore());

        final boolean isImmutable = ScoreEntry.class.getDeclaredFields()[0].getModifiers() == 26;
        assertTrue(isImmutable);
    }
}
