package it.unibo.coffebreak.model.entities.npc.donkeykong;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.enemy.barrel.GameBarrel;
import it.unibo.coffebreak.impl.model.entities.npc.donkeykong.DonkeyKong;

/**
 * Test class for {@link DonkeyKong} implementation.
 * Verifies the behavior of Donkey Kong including:
 * <ul>
 * <li>Barrel throwing mechanics</li>
 * <li>Throwing interval enforcement</li>
 * <li>State management (isTrowing flag)</li>
 * <li>Construction validation</li>
 * </ul>
 * 
 * @author Grazia Bochdanovits de Kavna
 */
class TestDonkeyKong {
    private static final float TEST_THROW_INTERVAL = 2.0f;
    private static final float SMALL_DELTA = 0.1f;
    private static final float HALF_INTERVAL = TEST_THROW_INTERVAL / 2;
    private static final float OVER_INTERVAL = TEST_THROW_INTERVAL + SMALL_DELTA;

    private static final Position TEST_POSITION = new Position(0, 0);
    private static final BoundigBox TEST_DIMENSION = new BoundigBox(5, 5);

    private DonkeyKong donkeyKong;

    /**
     * Initializes a DonkeyKong instance before each test.
     */
    @BeforeEach
    void setUp() {
        donkeyKong = new DonkeyKong(TEST_POSITION, TEST_DIMENSION, true);
    }

    /**
     * Verifies that Donkey Kong doesn't throw barrels before the interval elapses.
     */
    @Test
    void testNoBarrelThrownBeforeInterval() {
        assertFalse(donkeyKong.tryThrowBarrel(HALF_INTERVAL).isPresent());
        assertFalse(donkeyKong.isThrowing());
    }

    /**
     * Tests the complete barrel throwing cycle.
     * <ol>
     *   <li>Accumulates time up to half interval (no throw)</li>
     *   <li>Completes the interval (should throw)</li>
     *   <li>Verifies correct barrel type is created</li>
     *   <li>Checks isTrowing flag is set</li>
     * </ol>
     */
    @Test
    void testBarrelThrownAfterInterval() {
        assertFalse(donkeyKong.tryThrowBarrel(HALF_INTERVAL).isPresent());
        final Optional<Barrel> barrel = donkeyKong.tryThrowBarrel(HALF_INTERVAL + SMALL_DELTA);

        assertTrue(barrel.isPresent());
        assertTrue(barrel.get() instanceof GameBarrel);
        assertTrue(donkeyKong.isThrowing());
    }

     /**
     * Verifies behavior when Donkey Kong is configured not to throw barrels.
     * Ensures that:
     * <ul>
     *   <li>No barrels are ever thrown</li>
     *   <li>isTrowing flag remains false</li>
     * </ul>
     * even when sufficient time has elapsed.
     */
    @Test
    void testBarrelNotThrownWhenDisabled() {
        final DonkeyKong nonThrowing = new DonkeyKong(TEST_POSITION, TEST_DIMENSION, false);

        assertFalse(nonThrowing.tryThrowBarrel(OVER_INTERVAL).isPresent());
        assertFalse(nonThrowing.isThrowing());
    }

    /**
     * Tests the throw interval reset mechanism.
     */
    @Test
    void testTimerResetAfterThrow() {
        donkeyKong.tryThrowBarrel(OVER_INTERVAL);

        assertFalse(donkeyKong.tryThrowBarrel(SMALL_DELTA).isPresent());
        assertFalse(donkeyKong.isThrowing());

        assertTrue(donkeyKong.tryThrowBarrel(OVER_INTERVAL).isPresent());
        assertTrue(donkeyKong.isThrowing());
    }

    /**
     * Verifies the state machine of the isTrowing flag:
     * <ul>
     *   <li>Initial state: false</li>
     *   <li>During throw: true</li>
     *   <li>After throw: false</li>
     * </ul>
     * Tests the complete lifecycle of the throwing state.
     */
    @Test
    void testIsTrowingFlagBehavior() {
        assertFalse(donkeyKong.isThrowing());

        donkeyKong.tryThrowBarrel(OVER_INTERVAL);
        assertTrue(donkeyKong.isThrowing());

        donkeyKong.tryThrowBarrel(SMALL_DELTA);
        assertFalse(donkeyKong.isThrowing());
    }
}
