package it.unibo.coffebreak.model.entity.enemy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.impl.entity.enemy.BarrelMovementStrategy;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;

/**
 * Test class for {@link BarrelMovementStrategy}.
 * <p>
 * Verifies the movement behavior specific to barrel-type enemies including:
 * </p>
 * <ul>
 *   <li>Basic movement in cardinal directions</li>
 *   <li>Diagonal movement</li>
 *   <li>Input validation and null checks</li>
 * </ul>
 * 
 * @see BarrelMovementStrategy
 */
class TestBarrelMovementStrategy {

    @Test
    void testMove() {
        final BarrelMovementStrategy strategy = new BarrelMovementStrategy();
        final Position start = new Position(0, 0);
        final Vector2D direction = new Vector2D(1, 0);

        final Position result = strategy.move(start, direction);
        assertEquals(new Position(1, 0), result);
    }

    @Test
    void testMoveNullChecks() {
        final BarrelMovementStrategy strategy = new BarrelMovementStrategy();
        assertThrows(NullPointerException.class, () -> 
            strategy.move(null, new Vector2D(1, 0)));
        assertThrows(NullPointerException.class, () -> 
            strategy.move(new Position(0, 0), null));
    }

    @Test
    void testDiagonalMovement() {
        final BarrelMovementStrategy strategy = new BarrelMovementStrategy();
        final Position start = new Position(0, 0);
        final Vector2D direction = new Vector2D(1, 1);

        final Position result = strategy.move(start, direction);
        assertEquals(new Position(1, 1), result);
    }
}
