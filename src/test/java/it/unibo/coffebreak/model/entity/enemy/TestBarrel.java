package it.unibo.coffebreak.model.entity.enemy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.impl.entity.EnemyType;
import it.unibo.coffebreak.model.impl.entity.enemy.Barrel;
import it.unibo.coffebreak.model.impl.entity.enemy.BarrelMovementStrategy;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;

/**
 * Test class for {@link Barrel} enemy implementation.
 * <p>
 * Verifies the specific behavior of barrel-type enemies including:
 * </p>
 * <ul>
 *   <li>Proper initialization of barrel properties</li>
 *   <li>Correct movement behavior according to barrel mechanics</li>
 *   <li>Appropriate movement strategy assignment</li>
 * </ul>
 * 
 * @see Barrel
 * @see BarrelMovementStrategy
 */
class TestBarrel {

    private static final Position TEST_POSITION = new Position(0, 0);
    private static final Dimension TEST_DIMENSION =  new Dimension(10, 10);
    private static final Vector2D TEST_VELOCITY = new Vector2D(1, 0);

    @Test
    void testBarrelCreation() {
        final Barrel barrel = new Barrel(TEST_POSITION, TEST_DIMENSION, TEST_VELOCITY);

        assertEquals(TEST_POSITION, barrel.getPosition());
        assertEquals(TEST_DIMENSION, barrel.getDimension());
        assertEquals(EnemyType.BARREL, barrel.getState());
        assertEquals(TEST_VELOCITY.getX(), barrel.getVelocity().getX());
        assertEquals(TEST_VELOCITY.getY(), barrel.getVelocity().getY());
        assertTrue(barrel.getMovementStrategy() instanceof BarrelMovementStrategy);
    }

    @Test
    void testBarrelMovement() {
        final Barrel barrel = new Barrel(TEST_POSITION, TEST_DIMENSION, TEST_VELOCITY);
        barrel.update(1000);
        assertEquals(new Position(1, 0), barrel.getPosition());
    }
}
