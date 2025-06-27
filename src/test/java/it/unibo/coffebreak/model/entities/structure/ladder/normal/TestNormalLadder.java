package it.unibo.coffebreak.model.entities.structure.ladder.normal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.structure.ladder.AbstractLadder;
import it.unibo.coffebreak.impl.model.entities.structure.ladder.normal.NormalLadder;

/**
 * Test class for {@link NormalLadder} implementation.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
class TestNormalLadder {

    private static final Position TEST_POSITION = new Position(10, 20);
    private static final BoundigBox TEST_DIMENSION = new BoundigBox(1, 5);

    private AbstractLadder ladder;

    /**
     * Initializes a fresh NormalLadder instance before each test.
     */
    @BeforeEach
    void setUp() {
        ladder = new NormalLadder(TEST_POSITION, TEST_DIMENSION);
    }

    /**
     * Tests proper initialization of ladder properties.
     */
    @Test
    void testInitialization() {
        assertAll(
            () -> assertEquals(TEST_POSITION, ladder.getPosition()),
            () -> assertEquals(TEST_DIMENSION, ladder.getDimension())
        );
    }

    /**
     * Tests the default collision behavior inherited from AbstractLadder.
     * Verifies that:
     * <ul>
     *   <li>Method can be called with any Entity parameter</li>
     *   <li>No exceptions are thrown</li>
     * </ul>
     */
    @Test
    void testDefaultOnCollision() {
        final Entity mockEntity = mock(Entity.class);
        assertDoesNotThrow(() -> ladder.onCollision(mockEntity));
    }

    /**
     * Tests the constructor behavior.
     */
    @Test
    void testConstructorBehavior() {
        final NormalLadder ladder = new NormalLadder(TEST_POSITION, TEST_DIMENSION);

        assertAll(
            () -> assertNotNull(ladder),
            () -> assertEquals(TEST_POSITION, ladder.getPosition()),
            () -> assertEquals(TEST_DIMENSION, ladder.getDimension())
        );
    }
}
