package it.unibo.coffebreak.model.entity.platform;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.api.entity.platform.Platform;
import it.unibo.coffebreak.model.impl.entity.platform.BasicPlatform;
import it.unibo.coffebreak.model.impl.entity.platform.FragilePlatform;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;

/**
 * Test class for {@link FragilePlatform}.
 * Verifies the behavior of a fragile platform decorator that can be broken,
 * while maintaining all other platform properties from the base platform.
 */
class TestFragilePlatform {

    private static final Position TEST_POS = new Position(5, 5);
    private static final Dimension TEST_DIM = new Dimension(10, 10);

    private FragilePlatform fragilePlatform;

    @BeforeEach
    void setUp() {
        final Platform basicPlatform = new BasicPlatform(TEST_POS, TEST_DIM);
        fragilePlatform = new FragilePlatform(basicPlatform);
    }

    /**
     * Tests that FragilePlatform correctly overrides the break behavior.
     * Verifies that:
     * - A FragilePlatform can be broken (canBreak() returns true)
     * - This behavior differs from the basic platform implementation
     */
    @Test
    void testFragilePlatformOverridesBreakBehavior() {
        assertTrue(fragilePlatform.canBreak());
    }

    /**
     * Tests that FragilePlatform delegates friction value to the base platform.
     * Verifies that:
     * - The friction value comes from the wrapped BasicPlatform
     * - The default BasicPlatform friction of 0.5 is maintained
     */
    @Test
    void testDelegatesFriction() {
        assertEquals(0.5f, fragilePlatform.getFriction());
    }

    /**
     * Tests that FragilePlatform delegates position and dimension to the base platform.
     * Verifies that:
     * - The position comes from the wrapped platform
     * - The dimension comes from the wrapped platform
     * - The decorator doesn't modify these properties
     */
    @Test
    void testDelegatesPositionAndDimension() {
        final Platform customPlatform = new BasicPlatform(TEST_POS, TEST_DIM);
        final FragilePlatform customFragilePlatform = new FragilePlatform(customPlatform);

        assertEquals(TEST_POS, customFragilePlatform.getPosition());
        assertEquals(TEST_DIM, customFragilePlatform.getDimension());
    }

    /**
     * Tests the full integration of FragilePlatform with BasicPlatform.
     * Verifies that:
     * - The break behavior is overridden (true)
     * - The friction is properly delegated (0.5)
     * - The position is properly delegated
     * - All features work together correctly
     */
    @Test
    void testIntegrationWithBasicPlatform() {
        assertTrue(fragilePlatform.canBreak());
        assertEquals(0.5f, fragilePlatform.getFriction());
        assertEquals(TEST_POS.x(), fragilePlatform.getPosition().x());
    }
}
