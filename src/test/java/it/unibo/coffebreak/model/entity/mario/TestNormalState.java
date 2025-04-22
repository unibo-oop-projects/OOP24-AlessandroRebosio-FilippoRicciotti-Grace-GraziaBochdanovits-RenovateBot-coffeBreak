package it.unibo.coffebreak.model.entity.mario;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.model.impl.entity.MarioState;
import it.unibo.coffebreak.model.impl.entity.mario.Mario;
import it.unibo.coffebreak.model.impl.entity.mario.NormalState;

/**
 * Unit tests for {@link NormalState} implementation.
 * 
 * <p>Validates the behavior of Mario's normal state including:
 * <ul>
 *   <li>Movement and interaction capabilities</li>
 *   <li>State transitions and validation</li>
 *   <li>Edge case handling</li>
 * </ul>
 * 
 * <p>Testing approach uses Mockito to isolate the state from Mario's implementation.
 */
@ExtendWith(MockitoExtension.class)
class TestNormalState {

    @Mock private Mario mario;
    private NormalState state;

    @BeforeEach
    void setUp() {
        state = new NormalState(mario);
    }

    /**
     * Verifies that Mario in normal state can climb ladders/vines.
     */
    @Test
    void testCanClimb() {
        assertTrue(state.canClimb());
    }

    /**
     * Verifies that Mario in normal state cannot use hammer.
     */
    @Test
    void testCannotUseHammer() {
        assertFalse(state.canUseHammer());
    }

    /**
     * Verifies that state correctly identifies itself as NORMAL.
     */
    @Test
    void testStateTypeIsNormal() {
        assertEquals(MarioState.NORMAL, state.getStateType());
    }

    /**
     * Verifies that constructor rejects null Mario reference.
     */
    @Test
    void testConstructorWithNullMario() {
        assertThrows(NullPointerException.class,
            () -> new NormalState(null));
    }

    /**
     * Comprehensive test of all normal state properties.
     */
    @Test
    void testStateConsistency() {
        assertAll("Normal state properties",
            () -> assertTrue(state.canClimb()),
            () -> assertFalse(state.canUseHammer()),
            () -> assertEquals(MarioState.NORMAL, state.getStateType())
        );
    }
}
