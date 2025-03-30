package it.unibo.coffebreak.model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.score.api.Entry;
import it.unibo.coffebreak.model.score.impl.ScoreEntry;

/**
 * Test class for {@link Entry} interface and {@link ScoreEntry} implementation.
 */
class TestEntry {

    /** Test name used for entry operations. */
    private static final String TEST_NAME = "REB";

    /** Test score used for entry operations. */
    private static final int TEST_SCORE = 1000;

    /** The entry instance under test. */
    private Entry entry;

    /**
     * Initializes the test environment before each test.
     */
    @BeforeEach
    void init() {
        this.entry = new ScoreEntry(TEST_NAME, TEST_SCORE);
    }

    /**
     * Tests the constructor validation.
     * Verifies:
     * - NullPointerException is thrown when name is null
     */
    @Test
    void testNullName() {
        assertThrows(NullPointerException.class,
                () -> new ScoreEntry(null, TEST_SCORE),
                "Should throw exception when name is null");
    }

    /**
     * Tests the getName method functionality.
     * Verifies:
     * - Name is correctly returned
     */
    @Test
    void testGetName() {
        assertEquals(TEST_NAME, this.entry.getName(),
                "Name should match the test value");
    }

    /**
     * Tests the getScore method functionality.
     * Verifies:
     * - Score is correctly returned
     */
    @Test
    void testGetScore() {
        assertEquals(TEST_SCORE, this.entry.getScore(),
                "Score should match the test value");
    }

}
