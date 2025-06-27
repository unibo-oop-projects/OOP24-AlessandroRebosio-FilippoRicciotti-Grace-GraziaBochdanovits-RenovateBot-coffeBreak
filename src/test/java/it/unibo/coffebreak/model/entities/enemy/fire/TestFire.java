package it.unibo.coffebreak.model.entities.enemy.fire;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.enemy.fire.GameFire;

/**
 * Test class for {@link GameFire} implementation.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
class TestFire {

    private static final Position TEST_POSITION = new Position(0, 0);
    private static final BoundigBox TEST_DIMENSION = new BoundigBox(2, 2);

    private GameFire fire;

    /**
     * Initializes a fresh fire instance before each test.
     */
    @BeforeEach
    void setUp() {
        fire = new GameFire(TEST_POSITION, TEST_DIMENSION);
    }

    /**
     * Tests the initialization of fire properties.
     */
    @Test
    void testInitialization() {
        assertAll(
            () -> assertEquals(TEST_POSITION, fire.getPosition()),
            () -> assertEquals(TEST_DIMENSION, fire.getDimension()),
            () -> assertFalse(fire.isDestroyed())
        );
    }

    /**
     * Tests collision behavior with other entities.
     * Verifies that:
     * <ul>
     *   <li>Collision method can be called with any Entity</li>
     *   <li>No exceptions are thrown during collision</li>
     * </ul>
     */
    @Test
    void testOnCollision() {
        final Entity mockEntity = mock(Entity.class);
        assertDoesNotThrow(() -> fire.onCollision(mockEntity));
    }
}
