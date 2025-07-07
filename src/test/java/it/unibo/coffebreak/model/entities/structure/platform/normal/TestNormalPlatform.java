package it.unibo.coffebreak.model.entities.structure.platform.normal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.structure.platform.normal.NormalPlatform;

/**
 * Test class for {@link NormalPlatform} implementation.
 * 
 * <p>Verifies the specific behavior of normal platforms including:
 * <ul>
 *   <li>Unbreakable nature of normal platforms</li>
 *   <li>Proper initialization of platform properties</li>
 *   <li>Inherited behavior from AbstractPlatform</li>
 * </ul>
 * 
 * @author Grazia Bochdanovits de Kavna
 */
class TestNormalPlatform {

    private static final Position TEST_POSITION = new Position(0, 0);
    private static final BoundigBox TEST_DIMENSION = new BoundigBox(10, 1);

    /**
     * Tests the unbreakable nature of normal platforms.
     */
    @Test
    void testIsBrokenAlwaysFalse() {
        final NormalPlatform platform = new NormalPlatform(TEST_POSITION, TEST_DIMENSION, false, false);
        assertFalse(platform.isBroken());

        platform.destroy();
        assertFalse(platform.isBroken());
    }

    /**
     * Tests the constructor behavior and property initialization.
     */
    @Test
    void testConstructorBehavior() {
        final NormalPlatform platform = new NormalPlatform(TEST_POSITION, TEST_DIMENSION, true, true);

        assertEquals(TEST_POSITION, platform.getPosition());
        assertEquals(TEST_DIMENSION, platform.getDimension());
        assertTrue(platform.canPassThrough());
        assertTrue(platform.reversesDirection());
    }
}
