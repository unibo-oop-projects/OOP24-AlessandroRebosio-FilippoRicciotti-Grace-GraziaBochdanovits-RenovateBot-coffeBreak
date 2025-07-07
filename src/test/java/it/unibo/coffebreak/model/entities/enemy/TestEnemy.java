package it.unibo.coffebreak.model.entities.enemy;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.model.entities.enemy.Enemy;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.enemy.AbstractEnemy;
import it.unibo.coffebreak.impl.model.entities.enemy.barrel.GameBarrel;

/**
 * Test class for {@link AbstractEnemy} base functionality.
 * 
 * @author Grazi aBochdanovits de Kavna
 */
class TestEnemy {

    private static final Position TEST_POSITION = new Position(0, 0);
    private static final BoundigBox TEST_DIMENSION = new BoundigBox(2, 2);

    private Enemy enemy;

    @BeforeEach
    void setUp() {
        this.enemy = new GameBarrel(TEST_POSITION, TEST_DIMENSION, false);
    }

    @Test
    void testInitialState() {
        assertAll(
            () -> assertEquals(TEST_POSITION, enemy.getPosition()),
            () -> assertEquals(TEST_DIMENSION, enemy.getDimension()),
            () -> assertFalse(enemy.isDestroyed())
        );
    }

    @Test
    void testDestroy() {
        enemy.destroy();
        assertTrue(enemy.isDestroyed());
    }

    @Test
    void testMultipleDestroyCalls() {
        enemy.destroy();
        enemy.destroy();
        assertTrue(enemy.isDestroyed());
    }
}
