package it.unibo.coffebreak.model.entity.mario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.model.impl.entity.MarioState;
import it.unibo.coffebreak.model.impl.entity.mario.DeadState;
import it.unibo.coffebreak.model.impl.entity.mario.Mario;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Unit tests for {@link DeadState} implementation.
 * 
 * <p>Verifies the behavior of Mario when in DEAD state including:
 * <ul>
 *   <li>Movement restrictions</li>
 *   <li>Interaction limitations</li> 
 *   <li>State validation</li>
 *   <li>Edge case handling</li>
 * </ul>
 * 
 * <p>Testing approach uses Mockito to isolate the state from Mario's implementation.
 */
@ExtendWith(MockitoExtension.class)
class TestDeadState {

    private static final Position TEST_POSITION = new Position(5, 5);
    private static final Vector2D TEST_DIRECTION = new Vector2D(1, 1);

    @Mock private Mario mario;
    private DeadState deadState;

    @BeforeEach
    void setUp() {
        deadState = new DeadState(mario);
    }

    @Test
    void testMoveReturnsCurrentPosition() {
        final Position deathPosition = TEST_POSITION;

        when(mario.getPosition()).thenReturn(deathPosition);

        final Vector2D direction = TEST_DIRECTION; 
        final Position result = deadState.move(mario, direction);

        assertEquals(deathPosition, result);
    }

    @Test
    void testClimbDoesNothing() {
        assertDoesNotThrow(() -> deadState.climb(mario, 1));
    }

    @Test
    void testCannotClimb() {
        assertFalse(deadState.canClimb());
    }

    @Test
    void testCannotUseHammer() {
        assertFalse(deadState.canUseHammer());
    }

    @Test
    void testStateTypeIsDead() {
        assertEquals(MarioState.DEAD, deadState.getStateType());
    }

    @Test
    void testOnStateExitDoesNotThrow() {
        assertDoesNotThrow(() -> deadState.onStateExit(mario), "onStateExit should not throw any exception");
    }
}
