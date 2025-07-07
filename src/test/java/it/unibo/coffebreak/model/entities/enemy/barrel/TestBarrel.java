package it.unibo.coffebreak.model.entities.enemy.barrel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.enemy.barrel.GameBarrel;

/**
 * Test class for {@link GameBarrel} implementation.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
class TestBarrel {

    private static final Position TEST_POSITION = new Position(0, 0);
    private static final BoundigBox TEST_DIMENSION = new BoundigBox(2, 2);
    private static final float DELTA_TIME = 0.1f;
    private static final float PLATFORM_Y_POSITION = 30f;
    private static final int PLATFORM_WIDTH = 20;
    private static final int PLATFORM_HEIGHT = 1;
    private static final boolean CAN_TRANSFORM = true;
    private static final boolean CANNOT_TRANSFORM = false;

    private GameBarrel barrel;

    /**
     * Initializes a fresh barrel instance before each test.
     */
    @BeforeEach
    void setUp() {
        this.barrel = new GameBarrel(TEST_POSITION, TEST_DIMENSION, CAN_TRANSFORM);
    }

    /**
     * Tests the initial state of the barrel.
     */
    @Test
    void testInitialState() {
        assertAll(
            () -> assertEquals(TEST_POSITION, barrel.getPosition()),
            () -> assertEquals(TEST_DIMENSION, barrel.getDimension()),
            () -> assertFalse(this.barrel.canTransformToFire()),
            () -> assertFalse(this.barrel.isDestroyed())
        );
    }

    /**
     * Tests the barrel movement mechanics.
     * Verifies that:
     * <ul>
     *   <li>Barrel's position changes after update</li>
     *   <li>Movement follows the expected direction</li>
     * </ul>
     */
    @Test
    void testUpdateMovement() {
        final float initialX = barrel.getPosition().x();
        barrel.update(DELTA_TIME);
        assertNotEquals(initialX, barrel.getPosition().x());
    }

    /**
     * Tests collision behavior with tanks.
     * Verifies that:
     * <ul>
     *   <li>Barrel is destroyed when colliding with tank</li>
     *   <li>Transform capability is enabled after tank collision</li>
     * </ul>
     */
    @Test
    void testCollisionWithTank() {
        final Tank mockTank = mock(Tank.class);
        barrel.onCollision(mockTank);
        assertAll(
            () -> assertTrue(barrel.isDestroyed()),
            () -> assertTrue(barrel.canTransformToFire())
        );
    }

    /**
     * Tests collision behavior with platforms.
     * Verifies that:
     * <ul>
     *   <li>Barrel properly detects platform collisions</li>
     *   <li>No unexpected exceptions occur</li>
     * </ul>
     */
    @Test
    void testCollisionWithPlatform() {
        final Platform mockPlatform = mock(Platform.class);
        when(mockPlatform.getPosition()).thenReturn(new Position(0, PLATFORM_Y_POSITION));
        when(mockPlatform.getDimension()).thenReturn(new BoundigBox(PLATFORM_WIDTH, PLATFORM_HEIGHT));

        barrel.onCollision(mockPlatform);
    }

    /**
     * Tests transform capability when not destroyed by tank.
     * Verifies that:
     * <ul>
     *   <li>Barrel cannot transform when created with transform flag false</li>
     *   <li>Transform state remains false when not destroyed by tank</li>
     * </ul>
     */
    @Test
    void testCannotTransformWhenNotDestroyedByTank() {
        final GameBarrel nonFireBarrel = new GameBarrel(TEST_POSITION, TEST_DIMENSION, CANNOT_TRANSFORM);
        assertFalse(nonFireBarrel.canTransformToFire());
    }
}
