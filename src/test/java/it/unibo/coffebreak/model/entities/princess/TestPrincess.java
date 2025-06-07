package it.unibo.coffebreak.model.entities.princess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.model.entities.npc.Princess;
import it.unibo.coffebreak.model.impl.common.BoundingBox2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.npc.princess.Pauline;

/**
 * Test class for {@link Pauline} implementation.
 * 
 * <p>
 * This class verifies the behavior of the {@code GamePrincess} class,
 * including:
 * <ul>
 * <li>Initial state correctness</li>
 * <li>Rescue functionality</li>
 * </ul>
 * 
 * @see Pauline
 * @see Princess
 * @author Grazia Bochdanovits de Kavna
 */
class TestPrincess {

    /** Test position used for princess initialization. */
    private static final Position2D TEST_POSITION = new Position2D(10.0f, 20.0f);

    /** Test dimension used for princess initialization. */
    private static final BoundingBox2D TEST_DIMENSION = new BoundingBox2D(5.0f, 10.0f);

    /** The princess instance under test. */
    private Pauline princess;

    /**
     * Sets up the test environment before each test method execution.
     */
    @BeforeEach
    void setUp() {
        princess = new Pauline(TEST_POSITION, TEST_DIMENSION);
    }

    /**
     * Tests the initial state of the princess after creation.
     */
    @Test
    void testInitialState() {
        assertFalse(princess.isRescued(), "Princess should not be rescued initially");
        assertEquals(TEST_POSITION, princess.getPosition(), "Position should match constructor argument");
        assertEquals(TEST_DIMENSION, princess.getDimension(), "Dimension should match constructor argument");
    }

    /**
     * Tests the rescue functionality.
     */
    @Test
    void testRescue() {
        princess.rescue();
        assertTrue(princess.isRescued(), "Princess should be rescued after calling rescue()");
    }

}
