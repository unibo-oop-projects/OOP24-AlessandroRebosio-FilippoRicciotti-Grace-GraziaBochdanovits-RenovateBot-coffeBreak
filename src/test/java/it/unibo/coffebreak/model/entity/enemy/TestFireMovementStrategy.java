package it.unibo.coffebreak.model.entity.enemy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.impl.entity.enemy.FireMovementStrategy;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;

/**
 * Test class for {@link FireMovementStrategy}.
 * <p>
 * Verifies the movement behavior specific to fire-type enemies including:
 * </p>
 * <ul>
 *   <li>Basic movement in cardinal directions</li>
 *   <li>Diagonal movement</li>
 *   <li>Input validation and null checks</li>
 * </ul>
 * 
 * @see FireMovementStrategy
 */
class TestFireMovementStrategy {

    @Test
    void testMove() {
        final FireMovementStrategy strategy = new FireMovementStrategy();
        final Position start = new Position(0, 0);
        final Vector2D direction = new Vector2D(1, 0);

        final Position result = strategy.move(start, direction);
        assertEquals(new Position(1, 0), result);
    }

    @Test
    void testMoveNullChecks() {
        final FireMovementStrategy strategy = new FireMovementStrategy();
        assertThrows(NullPointerException.class, () -> 
            strategy.move(null, new Vector2D(1, 0)));
        assertThrows(NullPointerException.class, () -> 
            strategy.move(new Position(0, 0), null));
    }

    @Test
    void testNegativeMovement() {
        final FireMovementStrategy strategy = new FireMovementStrategy();
        final Position start = new Position(5, 5);
        final Vector2D direction = new Vector2D(-2, -3);

        final Position result = strategy.move(start, direction);
        assertEquals(new Position(3, 2), result);
    }
}
