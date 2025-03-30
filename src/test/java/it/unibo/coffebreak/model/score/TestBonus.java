package it.unibo.coffebreak.model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.score.api.Bonus;
import it.unibo.coffebreak.model.score.impl.GameBonus;

/**
 * Test class for {@link Bonus} interface and {@link GameBonus} implementation.
 */
class TestBonus {

    /** Test value used for bonus operations. */
    private static final int TEST_AMOUNT = 1000;

    /** The bonus instance under test. */
    private Bonus bonus;

    /**
     * Initializes the test environment before each test.
     */
    @BeforeEach
    void init() {
        this.bonus = new GameBonus();
    }

    /**
     * Tests the setBonus method functionality.
     * Verifies:
     * - Initial bonus is 0
     * - Bonus is correctly set to a positive value
     * - Exception is thrown when setting negative value
     */
    @Test
    void testSetBonus() {
        final int initialBonus = 0;
        assertEquals(initialBonus, this.bonus.getBonus(), "Initial bonus should be 0");

        this.bonus.setBonus(TEST_AMOUNT);
        assertEquals(TEST_AMOUNT, this.bonus.getBonus(), "Bonus should be set to test amount");

        assertThrows(IllegalArgumentException.class,
                () -> this.bonus.setBonus(-TEST_AMOUNT),
                "Should throw exception when setting negative bonus");
    }

    /**
     * Tests the calculate method functionality.
     * Verifies:
     * - Bonus is correctly reduced by AMOUNT
     * - Bonus doesn't go below 0
     * - Multiple calculations on zero bonus don't change value
     */
    @Test
    void testCalculate() {
        this.bonus.setBonus(GameBonus.AMOUNT);
        assertEquals(GameBonus.AMOUNT, this.bonus.getBonus(), "Bonus should be set to GameBonus.AMOUNT");

        this.bonus.calculate();
        final int expectedAfterCalculation = 0;
        assertEquals(expectedAfterCalculation, this.bonus.getBonus(),
                "Bonus should be 0 after calculation");

        this.bonus.calculate();
        assertEquals(expectedAfterCalculation, this.bonus.getBonus(),
                "Bonus should remain 0 on subsequent calculations");
    }

}
