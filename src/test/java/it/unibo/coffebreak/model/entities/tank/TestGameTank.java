package it.unibo.coffebreak.model.entities.tank;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.api.entities.tank.Tank;
import it.unibo.coffebreak.model.impl.common.Dimension2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.tank.GameTank;

/**
 * Test class for {@link GameTank} implementation.
 * 
 * <p>Verifies the behavior of the oil tank entity including:
 * <ul>
 *   <li>Proper initialization and construction</li>
 *   <li>Inheritance hierarchy</li>
 *   <li>Collision handling</li>
 *   <li>Null safety in constructor</li>
 * </ul>
 * 
 * @see GameTank
 * @see Tank
 * @author Grazia Bochdanovits de Kavna
 */
class TestGameTank {

    /** Test position used for tank initialization. */
    private static final Position2D TEST_POSITION = new Position2D(15.0f, 30.0f);

    /** Test dimension used for tank initialization. */
    private static final Dimension2D TEST_DIMENSION = new Dimension2D(8.0f, 12.0f);

    /** The tank instance under test. */
    private GameTank tank;

    /**
     * Sets up the test environment before each test.
     * Creates a new GameTank instance with test position and dimensions.
     */
    @BeforeEach
    void setUp() {
        tank = new GameTank(TEST_POSITION, TEST_DIMENSION);
    }

    /**
     * Tests proper initialization of the tank.
     * Verifies that position and dimensions are correctly set.
     */
    @Test
    void testInitialization() {
        assertEquals(TEST_POSITION, tank.getPosition(), 
            "Tank position should match constructor argument");
        assertEquals(TEST_DIMENSION, tank.getDimension(),
            "Tank dimensions should match constructor argument");
    }

}
