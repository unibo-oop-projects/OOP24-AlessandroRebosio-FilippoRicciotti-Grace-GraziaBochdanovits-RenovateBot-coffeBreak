package it.unibo.coffebreak.model.entities.structure.platform.breakable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.structure.platform.breakable.BreakablePlatform;

/**
 * Test class for {@link BreakablePlatform} implementation.
 * 
 * <p>Verifies the behavior of breakable platforms including:
 * <ul>
 *   <li>Initial unbroken state</li>
 *   <li>State transition when destroyed</li> 
 *   <li>Idempotency of destroy operations</li>
 *   <li>Proper initialization of platform properties</li>
 * </ul>
 * 
 * @author Grazia Bochdanovits de Kavna
 */
class TestBreakablePlatform {

    private static final Position TEST_POSITION = new Position(5, 10);
    private static final BoundigBox TEST_DIMENSION = new BoundigBox(8, 1);

    private BreakablePlatform platform;

    /**
     * Initializes a fresh breakable platform before each test.
     */
    @BeforeEach
    void setUp() {
        platform = new BreakablePlatform(TEST_POSITION, TEST_DIMENSION, false, false);
    }

    /**
     * Tests the initial state of a breakable platform.
     */
    @Test
    void testInitialState() {
        assertFalse(platform.isBroken());
    }

     /**
     * Tests the platform destruction mechanism.
     */
    @Test
    void testDestroy() {
        platform.destroy();
        assertTrue(platform.isBroken());
    }

    /**
     * Tests constructor behavior and property initialization.
     */
    @Test
    void testConstructorBehavior() {
        final BreakablePlatform customPlatform = new BreakablePlatform(TEST_POSITION, TEST_DIMENSION, true, true);

        assertEquals(TEST_POSITION, customPlatform.getPosition());
        assertEquals(TEST_DIMENSION, customPlatform.getDimension());
        assertTrue(customPlatform.canPassThrough());
        assertTrue(customPlatform.reversesDirection());
        assertFalse(customPlatform.isBroken());
    }
}
