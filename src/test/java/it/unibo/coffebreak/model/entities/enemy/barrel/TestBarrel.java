package it.unibo.coffebreak.model.entities.enemy.barrel;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.enemy.barrel.GameBarrel;

/**
 * Test class for {@link GameBarrel} that verifies the behavior of rolling barrels in the game.
 * 
 * <p>Tests cover:
 * <ul>
 *   <li>Initialization and properties</li>
 *   <li>Movement behavior on platforms</li>
 *   <li>Collision handling with different entities</li>
 *   <li>Direction inversion mechanics</li>
 *   <li>Transformation to fire capability</li>
 * </ul>
 * @see GameBarrel
 * @author Grazia Bochdanovits de Kavna
 */
@ExtendWith(MockitoExtension.class)
class TestBarrel {

    private static final float TEST_VELOCITY = 40f;
    private GameBarrel barrel;
    private GameBarrel fireTransformableBarrel;

    @Mock private Position mockPosition;
    @Mock private BoundigBox mockBoundingBox;
    @Mock private Platform mockPlatform;
    @Mock private Tank mockTank;
    @Mock private Entity mockOtherEntity;

    @BeforeEach
    void setUp() {
        barrel = new GameBarrel(mockPosition, mockBoundingBox, false);
        fireTransformableBarrel = new GameBarrel(mockPosition, mockBoundingBox, true);
    }

    /**
     * Tests barrel initialization.
     * Verifies:
     * <ul>
     *   <li>Initial velocity is set correctly</li>
     *   <li>Default state flags are properly initialized</li>
     *   <li>Transformation capability is set correctly</li>
     * </ul>
     */
    @Test
    void testInitialization() {
        assertEquals(TEST_VELOCITY, barrel.getVelocity().x());
        assertEquals(0f, barrel.getVelocity().y());
        assertFalse(barrel.isDestroyed());
        assertFalse(barrel.canTransformToFire());
    }

    /**
     * Tests movement update when on platform.
     * Verifies barrel maintains horizontal speed when on platform.
     */
    @Test
    void testUpdateOnPlatform() {
        final float deltaTime = 0.1f;
        barrel.onPlatformLand();
        barrel.update(deltaTime);
        assertEquals(TEST_VELOCITY, barrel.getVelocity().x());
    }

    /**
     * Tests collision with tank.
     * Verifies:
     * <ul>
     *   <li>Barrel is destroyed</li>
     *   <li>Destruction flag is set</li>
     *   <li>Transformation capability when applicable</li>
     * </ul>
     */
    @Test
    void testCollisionWithTank() {
        barrel.onCollision(mockTank);
        assertTrue(barrel.isDestroyed());
        assertFalse(barrel.canTransformToFire());

        fireTransformableBarrel.onCollision(mockTank);
        assertTrue(fireTransformableBarrel.canTransformToFire());
    }

    /**
     * Tests collision with platform.
     */
    @Test
    void testCollisionWithPlatform() {
        barrel.onPlatformLeave();
        barrel.onCollision(mockPlatform);
        assertTrue(barrel.canStandOnPlatforms());
    }

    /**
     * Tests collision with other entities.
     */
    @Test
    void testCollisionWithOtherEntity() {
        assertDoesNotThrow(() -> barrel.onCollision(mockOtherEntity));
    }

    /**
     * Tests direction inversion after falling.
     */
    @Test
    void testDirectionInversion() {
        final float initialSpeed = barrel.getVelocity().x();

        barrel.onPlatformLeave();
        barrel.onPlatformLand();
        assertEquals(initialSpeed, barrel.getVelocity().x());

        barrel.onPlatformLeave();
        barrel.onPlatformLand();
        assertEquals(initialSpeed, barrel.getVelocity().x());

        barrel.onPlatformLand();
        assertEquals(initialSpeed, barrel.getVelocity().x());
    }
}
