package it.unibo.coffebreak.model.entities.structure.platform;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.structure.platform.AbstractPlatform;
import it.unibo.coffebreak.impl.model.entities.structure.platform.normal.NormalPlatform;

/**
 * Test class for {@link AbstractPlatform} base functionality.
 * 
 * <p>Verifies the common behavior of all platform implementations including:
 * <ul>
 *   <li>Proper initialization of platform properties</li>
 *   <li>Default method implementations</li>
 *   <li>Pass-through behavior configuration</li>
 *   <li>Direction reversal capability</li>
 * </ul>
 * 
 * @author Grazia Bochdanovits de Kavna
 */
class TestAbstractPlatform {

    private static final Position TEST_POSITION = new Position(10, 20);
    private static final BoundigBox TEST_DIMENSION = new BoundigBox(5, 1);

    private AbstractPlatform platform;

    /**
     * Initializes a fresh platform instance before each test.
     */
    @BeforeEach
    void setUp() {
        platform = new NormalPlatform(TEST_POSITION, TEST_DIMENSION, true, false);
    }

    /**
     * Tests proper initialization of platform properties.
     */
    @Test
    void testInitialization() {
        assertEquals(TEST_POSITION, platform.getPosition());
        assertEquals(TEST_DIMENSION, platform.getDimension());
        assertTrue(platform.canPassThrough());
        assertFalse(platform.reversesDirection());
    }

    /**
     * Tests the default collision behavior.
     */
    @Test
    void testDefaultOnCollision() {
        final Entity mockEntity = mock(Entity.class);
        assertDoesNotThrow(() -> platform.onCollision(mockEntity));
    }

    /**
     * Tests the default destroy implementation.
     */
    @Test
    void testDefaultDestroy() {
        assertDoesNotThrow(platform::destroy);
    }

    /**
     * Tests the pass-through behavior configuration.
     */
    @Test
    void testPassThroughBehavior() {
        final AbstractPlatform solidPlatform = new NormalPlatform(TEST_POSITION, TEST_DIMENSION, false, false);
        assertFalse(solidPlatform.canPassThrough());
    }

    /**
     * Tests the direction reversal capability.
     */
    @Test
    void testDirectionReverseBehavior() {
        final AbstractPlatform reversingPlatform = new NormalPlatform(TEST_POSITION, TEST_DIMENSION, true, true);
        assertTrue(reversingPlatform.reversesDirection());
    }
}
