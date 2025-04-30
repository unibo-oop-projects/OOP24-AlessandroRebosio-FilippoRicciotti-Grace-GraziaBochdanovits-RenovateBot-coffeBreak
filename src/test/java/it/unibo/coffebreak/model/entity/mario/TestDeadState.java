package it.unibo.coffebreak.model.entity.mario;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
import it.unibo.coffebreak.model.impl.score.GameScoreManager;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

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
    private static final String PLAYER_NAME = "TestPlayer";

    @Mock private Mario mario;
    @Mock private GameScoreManager mockScoreManager;
    private DeadState deadState;

    @BeforeEach
    void setUp() {
        deadState = new DeadState(mario);
    }

    @Test
    void testMoveReturnsCurrentPosition() {
        when(mario.getPosition()).thenReturn(TEST_POSITION);
        final Position result = deadState.move(mario, TEST_DIRECTION);
        assertEquals(TEST_POSITION, result);
        verify(mario, never()).setPosition(any());
    }

    @Test
    void testClimbDoesNothing() {
        assertDoesNotThrow(() -> deadState.climb(mario, 1));
        verifyNoInteractions(mario);
    }

    @Test
    void testOnStateExitTriggersGameOver() {
        when(mario.getScoreManager()).thenReturn(mockScoreManager);
        deadState.onStateExit(mario, PLAYER_NAME);
        verify(mockScoreManager).endGame(PLAYER_NAME);
        verify(mario).resetToInitialState();
    }

    @Test
    void testStateTypeIsDead() {
        assertEquals(MarioState.DEAD, deadState.getStateType());
    }

    @Test
    void testCannotClimb() {
        assertFalse(deadState.canClimb());
    }

    @Test
    void testCannotUseHammer() {
        assertFalse(deadState.canUseHammer());
    }
}
