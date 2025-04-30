package it.unibo.coffebreak.model.entity.enemy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.impl.entity.enemy.EnemyType;
import it.unibo.coffebreak.model.impl.entity.enemy.Fire;
import it.unibo.coffebreak.model.impl.entity.enemy.FireMovementStrategy;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;

/**
 * Test class for {@link Fire} enemy implementation.
 * <p>
 * Verifies the specific behavior of fire-type enemies including:
 * </p>
 * <ul>
 *   <li>Proper initialization of fire enemy properties</li>
 *   <li>Correct movement behavior according to fire enemy mechanics</li>
 *   <li>Appropriate movement strategy assignment</li>
 * </ul>
 * 
 * @see Fire
 * @see FireMovementStrategy
 */
class TestFire {

    private static final Position TEST_POSITION = new Position(0, 0);
    private static final Dimension TEST_DIMENSION =  new Dimension(10, 10);
    private static final Vector2D TEST_VELOCITY = new Vector2D(1, 0);

    @Test
    void testFireCreation() {
        final Fire fire = new Fire(TEST_POSITION, TEST_DIMENSION, TEST_VELOCITY);

        assertEquals(TEST_POSITION, fire.getPosition());
        assertEquals(TEST_DIMENSION, fire.getDimension());
        assertEquals(EnemyType.FIRE, fire.getState());
        assertEquals(TEST_VELOCITY.getX(), fire.getVelocity().getX());
        assertEquals(TEST_VELOCITY.getY(), fire.getVelocity().getY());
        assertTrue(fire.getMovementStrategy() instanceof FireMovementStrategy);
    }

    @Test
    void testFireMovement() {
        final Fire fire = new Fire(TEST_POSITION, TEST_DIMENSION, TEST_VELOCITY);
        fire.update(1000);
        assertEquals(new Position(1, 0), fire.getPosition());
    }
}
