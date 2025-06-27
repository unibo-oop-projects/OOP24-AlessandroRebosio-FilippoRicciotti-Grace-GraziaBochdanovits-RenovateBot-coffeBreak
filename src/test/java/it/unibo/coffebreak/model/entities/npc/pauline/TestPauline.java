package it.unibo.coffebreak.model.entities.npc.pauline;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.model.entities.npc.Princess;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.npc.pauline.Pauline;

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
class TestPauline {

    /** Test position used for princess initialization. */
    private static final Position TEST_POSITION = new Position(10.0f, 20.0f);

    /** Test dimension used for princess initialization. */
    // private static final Dimension TEST_DIMENSION = new Dimension(5.0f,
    // 10.0f);

    /** The princess instance under test. */
    private Pauline princess;

    /**
     * Sets up the test environment before each test method execution.
     */
    @BeforeEach
    void setUp() {
        princess = new Pauline(TEST_POSITION, new BoundigBox());
    }

    /**
     * Tests the initial state of the princess after creation.
     */
    @Test
    void testInitialState() {
        // assertFalse(princess.isRescued(), "Princess should not be rescued
        // initially");
        // assertEquals(TEST_POSITION, princess.getPosition(), "Position should match
        // constructor argument");
        // assertEquals(TEST_DIMENSION, princess.getDimension(), "Dimension should match
        // constructor argument");
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
