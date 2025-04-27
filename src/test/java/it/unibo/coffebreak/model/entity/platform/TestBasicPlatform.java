package it.unibo.coffebreak.model.entity.platform;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.impl.entity.platform.BasicPlatform;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;

/**
 * Test class for {@link BasicPlatform}.
 * This class verifies the behavior and properties of the BasicPlatform implementation.
 */
class TestBasicPlatform {

    private static final Position TEST_POSITION = new Position(5, 10);
    private static final Dimension TEST_DIMENSION = new Dimension(15, 20);

    /**
     * Tests the properties of the BasicPlatform.
     * Verifies that:
     * - The platform is initialized with the correct position and dimension
     * - The default friction value is 0.5
     * - The platform cannot be broken (canBreak returns false)
     */
    @Test
    void testBasicPlatformProperties() {
        final BasicPlatform platform = new BasicPlatform(TEST_POSITION, TEST_DIMENSION);

        assertEquals(TEST_POSITION, platform.getPosition());
        assertEquals(TEST_DIMENSION, platform.getDimension());
        assertEquals(0.5f, platform.getFriction());
        assertFalse(platform.canBreak());
    }
}
