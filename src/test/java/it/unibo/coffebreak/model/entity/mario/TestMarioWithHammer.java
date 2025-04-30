package it.unibo.coffebreak.model.entity.mario;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.model.impl.entity.mario.Mario;
import it.unibo.coffebreak.model.impl.entity.mario.MarioState;
import it.unibo.coffebreak.model.impl.entity.mario.state.MarioWithHammerState;

/**
 * Test class for {@link MarioWithHammerState}.
 * Verifies the behavior of Mario when in the "with hammer" state.
 * (test for the class that doesn't use the Timer)
 */
@ExtendWith(MockitoExtension.class)
class TestMarioWithHammer {

    private static final String TEST_PLAYER_NAME = "TestPlayer";
    private static final long TEST_DELTA_TIME = 500;
    @Mock private Mario mario;
    private MarioWithHammerState hammerState;

    /**
     * Initializes the test environment before each test.
     * Creates a new MarioWithHammerState instance with a mocked Mario.
     */
    @BeforeEach
    void setUp() {
        hammerState = new MarioWithHammerState(mario);
    }

    /**
     * Tests that the constructor properly initializes the state.
     */
    @Test
    void testConstructor() {
        assertNotNull(hammerState);
    }

    /**
     * Tests that the constructor throws NullPointerException when Mario is null.
     */
    @Test
    void testConstructorWithNullMario() {
        assertThrows(NullPointerException.class, () -> new MarioWithHammerState(null));
    }

    /**
     * Tests that update() doesn't change state before the hammer duration expires.
     * @throws InterruptedException if the thread sleep is interrupted
     */
    @Test
    void testUpdateBeforeExpiration() throws InterruptedException {
        final MarioWithHammerState state = new MarioWithHammerState(mario, 
                System.currentTimeMillis() + 1000);
        state.update(mario, TEST_DELTA_TIME);
        verify(mario, never()).changeState(any());
    }

    /**
     * Tests that update() changes state to NORMAL after the hammer duration expires.
     */
    @Test
    void testUpdateAfterExpiration() {
        final MarioWithHammerState state = new MarioWithHammerState(mario, System.currentTimeMillis() - 1);
        state.update(mario, 100);
        verify(mario).changeState(MarioState.NORMAL);
    }

    /**
     * Tests that climb() does nothing in hammer state.
     */
    @Test
    void testClimb() {
        hammerState.climb(mario, 1);
        verify(mario, never()).setPosition(any());
    }

    /**
     * Tests that useHammer() can be called without exceptions.
     * Note: Actual hammer attack logic would be tested separately.
     */
    @Test
    void testUseHammer() {
        assertDoesNotThrow(() -> hammerState.useHammer(mario));
    }

    /**
     * Tests that onStateExit() can be called without exceptions.
     */
    @Test
    void testOnStateExit() {
        assertDoesNotThrow(() -> hammerState.onStateExit(mario, TEST_PLAYER_NAME));
    }

    /**
     * Tests that canClimb() returns false in hammer state.
     */
    @Test
    void testCanClimb() {
        assertFalse(hammerState.canClimb());
    }

    /**
     * Tests that canUseHammer() returns true in hammer state.
     */
    @Test
    void testCanUseHammer() {
        assertTrue(hammerState.canUseHammer());
    }

    /**
     * Tests that getStateType() returns WITH_HAMMER.
     */
    @Test
    void testGetStateType() {
        assertEquals(MarioState.WITH_HAMMER, hammerState.getStateType());
    }
}
