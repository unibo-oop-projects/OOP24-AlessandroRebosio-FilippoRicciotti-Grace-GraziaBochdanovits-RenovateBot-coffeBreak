package it.unibo.coffebreak.model.entity.enemy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import it.unibo.coffebreak.model.impl.entity.enemy.Barrel;
import it.unibo.coffebreak.model.impl.entity.enemy.BarrelManager;
import it.unibo.coffebreak.model.impl.entity.enemy.Enemy;
import it.unibo.coffebreak.model.impl.entity.enemy.Fire;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link BarrelManager}, which handles the transformation of barrels into fire entities.
 * <p>
 * Verifies the following behavior:
 * </p>
 * <ul>
 *   <li>Proper management of the active enemies list during transformations</li>
 *   <li>Null-checking for method parameters</li>
 *   <li>Immutability of the returned enemies list</li>
 * </ul>
 */
class TestBarrelManager {

    private static final Position TEST_POSITION = new Position(0, 0);
    private static final Dimension TEST_DIMENSION =  new Dimension(10, 10);
    private static final Vector2D TEST_VELOCITY = new Vector2D(5, 0);
    private BarrelManager barrelManager;
    private List<Enemy> activeEnemies;
    private Barrel barrel;

    @BeforeEach
    void setUp() {
        activeEnemies = new ArrayList<>();
        barrelManager = new BarrelManager(activeEnemies);
        barrel = new Barrel(TEST_POSITION, TEST_DIMENSION, TEST_VELOCITY, true);
    }

    @Test
    void testConstructorThrowsOnNullList() {
        assertThrows(NullPointerException.class, () -> new BarrelManager(null));
    }

    @Test
    void testTransformationRemovesBarrelFromList() {
        activeEnemies.add(barrel);
        barrelManager.onBarrelTransformedToFire(barrel, TEST_VELOCITY);
        assertFalse(activeEnemies.contains(barrel));
    }

    @Test
    void testTransformationAddsFireWithCorrectProperties() {
        activeEnemies.add(barrel);
        barrelManager.onBarrelTransformedToFire(barrel, barrel.getVelocity());
 
        assertEquals(1, activeEnemies.size());
        assertTrue(activeEnemies.get(0) instanceof Fire);

        final Fire fire = (Fire) activeEnemies.getLast();
        final Vector2D expectedVel = fire.getVelocity();
        assertEquals(barrel.getPosition(), fire.getPosition());
        assertEquals(barrel.getDimension(), fire.getDimension());
        assertEquals(expectedVel, fire.getVelocity());
    }

    @Test
    void testTransformationMaintainsListSize() {
        activeEnemies.add(barrel);
        final int initialSize = activeEnemies.size();
        barrelManager.onBarrelTransformedToFire(barrel, TEST_VELOCITY);
        assertEquals(initialSize, activeEnemies.size());
    }

    @Test
    void testTransformNonExistentBarrel() {
        assertDoesNotThrow(() -> 
            barrelManager.onBarrelTransformedToFire(barrel, TEST_VELOCITY));
        assertFalse(activeEnemies.isEmpty());
    }

    @Test
    void testOnBarrelTransformedToFireThrowsOnNullBarrel() {
        assertThrows(NullPointerException.class, () ->
            barrelManager.onBarrelTransformedToFire(null, new Vector2D(1, 0)));
    }

    @Test
    void testOnBarrelTransformedToFireThrowsOnNullVelocity() {
        assertThrows(NullPointerException.class, () ->
            barrelManager.onBarrelTransformedToFire(barrel, null));
    }

    @Test
    void testGetActiveEnemiesReturnsUnmodifiableList() {
        final List<Enemy> enemies = barrelManager.getActiveEnemies();
        assertThrows(UnsupportedOperationException.class, () ->
            enemies.add(new Fire(TEST_POSITION, TEST_DIMENSION, TEST_VELOCITY)));
    }
}
