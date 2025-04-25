package it.unibo.coffebreak.model.entity.enemy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.impl.entity.EnemyType;
import it.unibo.coffebreak.model.impl.entity.enemy.Enemy;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;

/**
 * Test class for {@link Enemy} abstract base class.
 * <p>
 * This class tests the core functionality of the Enemy class including:
 * </p>
 * <ul>
 *   <li>Constructor initialization</li>
 *   <li>Position updates and movement</li>
 *   <li>State management</li>
 *   <li>Velocity handling</li>
 *   <li>Input validation</li>
 * </ul>
 */
class TestEnemy {

    private static final Position TEST_POSITION = new Position(10, 10);
    private static final Position UPDATED_POSITION = new Position(11, 10);
    private static final Dimension TEST_DIMENSION = new Dimension(20, 20);
    private static final Vector2D TEST_VELOCITY = new Vector2D(1, 0);
    private Enemy enemy;

    @BeforeEach
    void setUp() {
        enemy = new Enemy(TEST_POSITION, TEST_DIMENSION, EnemyType.BARREL, TEST_VELOCITY) {
            @Override
            protected void specificUpdate(final long deltaTime) { }
        };
    }

    @Test
    void testConstructor() {
        assertNotNull(enemy);
        assertEquals(TEST_POSITION, enemy.getPosition());
        assertEquals(TEST_DIMENSION, enemy.getDimension());
        assertEquals(EnemyType.BARREL, enemy.getState());
        assertEquals(TEST_VELOCITY.getX(), enemy.getVelocity().getX());
        assertEquals(TEST_VELOCITY.getY(), enemy.getVelocity().getY());
    }

    @Test
    void testConstructorNullChecks() {
        assertThrows(NullPointerException.class, () -> 
            new Enemy(null, TEST_DIMENSION, EnemyType.BARREL, TEST_VELOCITY) {
                @Override protected void specificUpdate(final long deltaTime) { }
            });
    }

    @Test
    void testUpdate() {
        final long deltaTime = 1000;
        enemy.update(deltaTime);
        assertEquals(UPDATED_POSITION, enemy.getPosition());
    }

    @Test
    void testNegativeDeltaTime() {
        assertThrows(IllegalArgumentException.class, () -> enemy.update(-1));
    }

    @Test
    void testSetState() {
        enemy.setState(EnemyType.FIRE);
        assertEquals(EnemyType.FIRE, enemy.getState());
        assertThrows(NullPointerException.class, () -> enemy.setState(null));
    }

    @Test
    void testSetVelocity() {
        final Vector2D newVelocity = new Vector2D(2, 3);
        enemy.setVelocity(newVelocity);
        assertEquals(newVelocity.getX(), enemy.getVelocity().getX());
        assertEquals(newVelocity.getY(), enemy.getVelocity().getY());
        assertThrows(NullPointerException.class, () -> enemy.setVelocity(null));
    }
}
