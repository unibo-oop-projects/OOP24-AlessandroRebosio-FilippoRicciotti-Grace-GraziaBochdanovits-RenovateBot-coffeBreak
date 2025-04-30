package it.unibo.coffebreak.model.entity.donkeykong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.impl.entity.donkeykong.AngerManager;

/**
 * Unit tests for the {@link AngerManager} class.
 * <p>
 * This test class verifies the behavior of the AngerManager, including:
 * <ul>
 *   <li>Initialization with default and custom cooldown values</li>
 *   <li>Anger level progression and maximum level detection</li>
 *   <li>Cooldown time retrieval for each level</li>
 *   <li>Reset functionality</li>
 *   <li>Error handling for invalid constructor arguments</li>
 * </ul>
 * </p>
 */
class TestAngerManager {

    private static final long[] CUSTOM_COOLDOWNS = {5000, 4000, 3000};
    private static final int DEFAULT_L0 = 4000;
    private static final int DEFAULT_L1 = 3000;
    private static final int DEFAULT_L2 = 2000;
    private static final int DEFAULT_L3 = 1000;
    private static final int CUSTOM_L0 = 5000;
    private static final int CUSTOM_L1 = 4000;
    private static final int CUSTOM_L2 = 3000;
    private AngerManager defaultAngerManager;
    private AngerManager customAngerManager;

    @BeforeEach
    void setUp() {
        defaultAngerManager = new AngerManager();
        customAngerManager = new AngerManager(CUSTOM_COOLDOWNS);
    }

    @Test
    void testDefaultConstructor() {
        assertEquals(0, defaultAngerManager.getLevel());
        assertEquals(DEFAULT_L0, defaultAngerManager.getCurrentCooldown());
        assertFalse(defaultAngerManager.isMaxLevel());
    }

    @Test
    void testCustomConstructor() {
        assertEquals(0, customAngerManager.getLevel());
        assertEquals(CUSTOM_L0, customAngerManager.getCurrentCooldown());
        assertFalse(customAngerManager.isMaxLevel());
    }

    @Test
    void testConstructorWithNullCooldowns() {
        assertThrows(NullPointerException.class, () -> new AngerManager(null));
    }

    @Test
    void testConstructorWithEmptyCooldowns() {
        assertThrows(IllegalArgumentException.class, () -> new AngerManager(new long[0]));
    }

    @Test
    void testIncrease() {
        assertEquals(0, defaultAngerManager.getLevel());
        defaultAngerManager.increase();
        assertEquals(1, defaultAngerManager.getLevel());
        assertEquals(DEFAULT_L1, defaultAngerManager.getCurrentCooldown());

        defaultAngerManager.increase();
        assertEquals(2, defaultAngerManager.getLevel());
        assertEquals(DEFAULT_L2, defaultAngerManager.getCurrentCooldown());

        defaultAngerManager.increase();
        assertEquals(3, defaultAngerManager.getLevel());
        assertEquals(DEFAULT_L3, defaultAngerManager.getCurrentCooldown());
        assertTrue(defaultAngerManager.isMaxLevel());

        defaultAngerManager.increase();
        assertEquals(3, defaultAngerManager.getLevel());
    }

    @Test
    void testIncreaseWithCustomLevels() {
        assertEquals(0, customAngerManager.getLevel());
        customAngerManager.increase();
        assertEquals(1, customAngerManager.getLevel());
        assertEquals(CUSTOM_L1, customAngerManager.getCurrentCooldown());

        customAngerManager.increase();
        assertEquals(2, customAngerManager.getLevel());
        assertEquals(CUSTOM_L2, customAngerManager.getCurrentCooldown());
        assertTrue(customAngerManager.isMaxLevel());

        customAngerManager.increase();
        assertEquals(2, customAngerManager.getLevel());
    }

    @Test
    void testReset() {
        defaultAngerManager.increase();
        defaultAngerManager.increase();
        assertEquals(2, defaultAngerManager.getLevel());

        defaultAngerManager.reset();
        assertEquals(0, defaultAngerManager.getLevel());
        assertEquals(DEFAULT_L0, defaultAngerManager.getCurrentCooldown());
        assertFalse(defaultAngerManager.isMaxLevel());
    }

    @Test
    void testGetCurrentCooldown() {
        assertEquals(DEFAULT_L0, defaultAngerManager.getCurrentCooldown());
        defaultAngerManager.increase();
        assertEquals(DEFAULT_L1, defaultAngerManager.getCurrentCooldown());
    }

    @Test
    void testIsMaxLevel() {
        assertFalse(defaultAngerManager.isMaxLevel());
        defaultAngerManager.increase();
        defaultAngerManager.increase();
        assertFalse(defaultAngerManager.isMaxLevel());
        defaultAngerManager.increase();
        assertTrue(defaultAngerManager.isMaxLevel());
    }

    @Test
    void testGetLevel() {
        assertEquals(0, defaultAngerManager.getLevel());
        defaultAngerManager.increase();
        assertEquals(1, defaultAngerManager.getLevel());
    }
}
