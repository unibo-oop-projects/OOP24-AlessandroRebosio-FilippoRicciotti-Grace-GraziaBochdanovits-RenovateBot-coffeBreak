package it.unibo.coffebreak.model.entity.enemy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.api.entity.enemy.BarrelTransformationObserver;
import it.unibo.coffebreak.model.impl.entity.enemy.Barrel;
import it.unibo.coffebreak.model.impl.entity.enemy.BarrelMovementStrategy;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;

/**
 * Test class for {@link Barrel} enemy implementation.
 * <p>
 * Verifies both core functionality and edge cases of barrel-type enemies including:
 * </p>
 * 
 * <ul>
 *   <li><b>Movement mechanics</b>: Position updates according to velocity and time</li>
 *   <li><b>Transformation logic</b>: Conversion to fire when conditions are met</li>
 *   <li><b>Observer management</b>: Notification handling and null checks</li>
 * </ul>
 * 
 * <p>Test cases cover:
 * <ol>
 *   <li>Standard movement with positive velocity</li>
 *   <li>Transformation triggering at threshold position (x ≥ 50)</li>
 *   <li>Observer registration and notification protocols</li>
 * </ol>
 * 
 * @see Barrel
 * @see BarrelMovementStrategy
 */
class TestBarrel {

    private static final Position TEST_POSITION = new Position(0, 0);
    private static final Dimension TEST_DIMENSION =  new Dimension(10, 10);
    private static final Vector2D TEST_VELOCITY = new Vector2D(5, 0);
    private static final int START_COORDINATE_X = 50;

    private Barrel barrel;
    private BarrelTransformationObserver mockObserver;

    @BeforeEach
    void setUp() {
        mockObserver = mock(BarrelTransformationObserver.class);
        barrel = new Barrel(TEST_POSITION, TEST_DIMENSION, TEST_VELOCITY, true);
        barrel.addTransformationObserver(mockObserver);
    }

    @Test
    void testBarrelMovement() {
        final Barrel barrel = new Barrel(TEST_POSITION, TEST_DIMENSION, 
                                            TEST_VELOCITY, false);
        barrel.update(1000);
        assertEquals(new Position(TEST_POSITION.x() + TEST_VELOCITY.getX(), 0), barrel.getPosition());
    }

    @Test
    void testMovementWithZeroDeltaTime() {
        barrel.update(0);
        assertEquals(TEST_POSITION, barrel.getPosition());
    }

    @Test
    void testTransformableBarrel() {
        final Barrel transformableBarrel = new Barrel(new Position(START_COORDINATE_X, 0), 
                                            TEST_DIMENSION, TEST_VELOCITY, true);
        assertTrue(transformableBarrel.canTransformToFire());
    }

    @Test
    void testTransformationOnlyIfEnabled() {
        final Barrel nonTransformableBarrel = new Barrel(new Position(START_COORDINATE_X, 0), 
                                            TEST_DIMENSION, TEST_VELOCITY, false);
        nonTransformableBarrel.addTransformationObserver(mockObserver);
        nonTransformableBarrel.update(1000);
        verify(mockObserver, never()).onBarrelTransformedToFire(any(), any());
    }

    @Test
    void testAddingNullObserverThrowsException() {
        assertThrows(NullPointerException.class, () ->
            barrel.addTransformationObserver(null));
    }
}
