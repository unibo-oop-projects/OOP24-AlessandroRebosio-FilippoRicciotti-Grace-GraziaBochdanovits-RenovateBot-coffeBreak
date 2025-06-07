package it.unibo.coffebreak.model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.model.score.entry.Entry;
import it.unibo.coffebreak.model.impl.score.entry.ScoreEntry;

/**
 * Test class for {@link ScoreEntry} implementation. Verifies entry creation,
 * comparison, equality, and string representation.
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
     */
    @Test
    void shouldReturnCorrectName() {
        assertEquals(TEST_NAME, entry.getName());

        final Entry lowerCaseEntry = new ScoreEntry("reb", TEST_SCORE);
        assertEquals("reb", lowerCaseEntry.getName());
    }

    /**
     * Tests getScore() returns expected value.
     */
    @Test
    void shouldReturnCorrectScore() {
        final int expected = 2000;
        assertEquals(TEST_SCORE, entry.getScore());

        final Entry differentScoreEntry = new ScoreEntry(TEST_NAME, 2000);
        assertEquals(expected, differentScoreEntry.getScore());
    }

    /**
     * Tests equals method with different scenarios.
     */
    @Test
    void testEquals() {
        final ScoreEntry entry1 = new ScoreEntry(TEST_NAME, TEST_SCORE);
        final ScoreEntry entry2 = new ScoreEntry(TEST_NAME, TEST_SCORE);
        final ScoreEntry entry3 = new ScoreEntry("Player2", TEST_SCORE);
        final ScoreEntry entry4 = new ScoreEntry(TEST_NAME, 900);

        assertEquals(entry1, entry2);
        assertNotEquals(entry1, entry3);
        assertNotEquals(entry1, entry4);
        assertNotEquals(entry1, null);
        assertNotEquals(entry1, new Object());
    }

    /**
     * Tests hashCode consistency with equals.
     */
    @Test
    void testHashCode() {
        final ScoreEntry entry1 = new ScoreEntry(TEST_NAME, TEST_SCORE);
        final ScoreEntry entry2 = new ScoreEntry(TEST_NAME, TEST_SCORE);

        assertEquals(entry1.hashCode(), entry2.hashCode());
    }

    /**
     * Tests toString returns expected format.
     */
    @Test
    void testToString() {
        final ScoreEntry entry = new ScoreEntry(TEST_NAME, TEST_SCORE);
        final String expected = "ScoreEntry[name=REB, score=1000]";
        assertEquals(expected, entry.toString());
    }
}
