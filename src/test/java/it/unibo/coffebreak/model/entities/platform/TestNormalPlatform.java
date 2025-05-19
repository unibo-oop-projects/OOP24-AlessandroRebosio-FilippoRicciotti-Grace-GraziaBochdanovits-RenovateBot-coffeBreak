package it.unibo.coffebreak.model.entities.platform;


import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.api.entities.platform.Platform.Slope;
import it.unibo.coffebreak.model.impl.common.Dimension2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.platform.normal.NormalPlatform;

/**
 * Test class for {@link NormalPlatform} implementation.
 * 
 * <p>Verifies the specific behavior of normal platforms including:
 * <ul>
 *   <li>Proper construction through superclass</li>
 *   <li>Unbreakable nature</li>
 * </ul>
 * @author Grazia Bochdanovits de Kavna
 */
class TestNormalPlatform {

    private static final Position2D TEST_POSITION = new Position2D(10.0f, 20.0f);
    private static final Dimension2D TEST_DIMENSION = new Dimension2D(30.0f, 5.0f);

    /**
     * Tests that NormalPlatform correctly reports it cannot break.
     */
    @Test
    void testCanBreak() {
        final NormalPlatform platform = new NormalPlatform(TEST_POSITION, TEST_DIMENSION, Slope.FLAT);
        assertFalse(platform.canBreak(), "Normal platforms should not be breakable");
    }

}
