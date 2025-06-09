package it.unibo.coffebreak.model.level.bonus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.model.level.bonus.Bonus;
import it.unibo.coffebreak.impl.model.level.bonus.GameBonus;

/**
 * Test class for {@link GameBonus} implementation. Verifies bonus
 * initialization, setting, calculation, and edge cases.
 * 
 * @author Alessandro Rebosio
 */
class TestBonus {

    /** Standard test value for positive bonus amounts. */
    private static final int TEST_AMOUNT = 1000;

    /** The bonus instance under test. */
    private Bonus bonus;

    /**
     * Initializes a fresh GameBonus instance before each test.
     * Ensures test isolation by creating new instance for each test method.
     */
    @BeforeEach
    void setUp() {
        this.bonus = new GameBonus();
    }

    /**
     * Verifies that a newly created bonus instance starts with zero value.
     */
    @Test
    void initialBonusShouldBeZero() {
        assertEquals(0, bonus.getBonus());
    }

    /**
     * Tests that positive bonus values can be set correctly.
     */
    @Test
    void shouldSetPositiveBonus() {
        bonus.setBonus(TEST_AMOUNT);
        assertEquals(TEST_AMOUNT, bonus.getBonus());
    }

    /**
     * Tests that zero can be set as a valid bonus value.
     */
    @Test
    void shouldSetZeroBonus() {
        bonus.setBonus(0);
        assertEquals(0, bonus.getBonus());
    }

    /**
     * Verifies that attempting to set negative bonus values throws
     * IllegalArgumentException.
     */
    @Test
    void shouldThrowWhenSettingNegativeBonus() {
        assertThrows(IllegalArgumentException.class, () -> bonus.setBonus(-1));
        assertThrows(IllegalArgumentException.class, () -> bonus.setBonus(-TEST_AMOUNT));
        assertThrows(IllegalArgumentException.class, () -> bonus.setBonus(Integer.MIN_VALUE));
    }

    /**
     * Tests that the calculate method correctly reduces the bonus
     * by the fixed AMOUNT value.
     */
    @Test
    void shouldReduceBonusByFixedAmount() {
        bonus.setBonus(GameBonus.AMOUNT * 2);
        bonus.calculate();
        assertEquals(GameBonus.AMOUNT, bonus.getBonus());
    }

    /**
     * Verifies that the bonus cannot become negative after calculation
     * and remains zero on subsequent calculations.
     */
    @Test
    void shouldNotReduceBonusBelowZero() {
        bonus.setBonus(GameBonus.AMOUNT);
        bonus.calculate();
        assertEquals(0, bonus.getBonus());

        bonus.calculate();
        assertEquals(0, bonus.getBonus());
    }

    /**
     * Tests edge case behavior when bonus is just above the reduction amount.
     */
    @Test
    void shouldHandleEdgeCaseJustAboveAmount() {
        bonus.setBonus(GameBonus.AMOUNT + 1);
        bonus.calculate();
        assertEquals(1, bonus.getBonus());

        bonus.calculate();
        assertEquals(0, bonus.getBonus());
    }
}
