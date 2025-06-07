package it.unibo.coffebreak.model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.model.score.Score;
import it.unibo.coffebreak.impl.model.score.GameScore;

/**
 * Test class for {@link GameScore} implementation. Verifies score
 * initialization, incrementing, and reset functionality.
 * 
 * @author Alessandro Rebosio
 */
class TestScore {

    /** Test value used for score operations. */
    private static final int TEST_AMOUNT = 1000;

    /** The score instance under test. */
    private Score score;

    /**
     * Initializes the test environment before each test.
     */
    @BeforeEach
    void init() {
        this.score = new GameScore();
    }

    /**
     * Tests that a newly created GameScore has zero value.
     */
    @Test
    void testInitialScoreIsZero() {
        assertEquals(0, score.getScore());
    }

    /**
     * Tests the initial state and increase functionality.
     * Verifies:
     * - Initial score is 0
     * - Score is correctly increased by positive value
     * - Exception is thrown when increasing with negative value
     */
    @Test
    void testScoreIncrease() {
        final int initialScore = 0;
        assertEquals(initialScore, this.score.getScore(), "Initial score should be 0");

        this.score.increase(TEST_AMOUNT);
        assertEquals(TEST_AMOUNT, this.score.getScore(), "Score should be increased by test amount");

        assertThrows(IllegalArgumentException.class,
                () -> this.score.increase(-TEST_AMOUNT),
                "Should throw exception when increasing with negative value");
    }

    /**
     * Tests the reset functionality.
     * Verifies:
     * - Score is correctly reset to 0
     * - Multiple resets don't affect the value
     */
    @Test
    void testScoreReset() {
        this.score.increase(TEST_AMOUNT);
        assertEquals(TEST_AMOUNT, this.score.getScore(), "Precondition: score should be increased");

        this.score.reset();
        final int expectedAfterReset = 0;
        assertEquals(expectedAfterReset, this.score.getScore(),
                "Score should be 0 after reset");

        this.score.reset();
        assertEquals(expectedAfterReset, this.score.getScore(),
                "Score should remain 0 on subsequent resets");
    }

}
