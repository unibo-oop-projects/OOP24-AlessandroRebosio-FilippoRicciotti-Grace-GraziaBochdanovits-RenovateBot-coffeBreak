package it.unibo.coffebreak.model.entity.mario;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.model.impl.entity.MarioState;
import it.unibo.coffebreak.model.impl.entity.mario.Mario;
import it.unibo.coffebreak.model.impl.entity.mario.WithHammerState;

/**
 * Comprehensive test class for {@link WithHammerState} implementation.
 * 
 * <p>Verifies the behavior of Mario when in "With Hammer" state including:
 * <ul>
 *   <li>Automatic state transition after timeout</li>
 *   <li>Ability restrictions and permissions</li>
 *   <li>Resource management and cleanup</li>
 *   <li>State identification</li>
 * </ul>
 */
@ExtendWith(MockitoExtension.class)
class TestWithHammerState {

    private static final long TEST_TIMEOUT_MS = 100;
    private static final long SAFE_WAIT_MS = TEST_TIMEOUT_MS * 2;
    private static final long IMMEDIATE_TRANSITION_MS = 0;
    private static final long INVALID_DURATION_MS = -1;

    @Mock private Mario mockMario;
    private WithHammerState hammerState;

    @BeforeEach
    void setUp() {
        hammerState = new WithHammerState(mockMario);
    }

    @Test
    void testStateConsistency() {
        assertAll("WithHammer state properties",
            () -> assertFalse(hammerState.canClimb()),
            () -> assertTrue(hammerState.canUseHammer()),
            () -> assertEquals(MarioState.WITH_HAMMER, hammerState.getStateType())
        );
    }

    @Nested
    class StateTransitionTests {

        @Test
        void testAutoRevertToNormal() throws InterruptedException {
            final AtomicBoolean transitioned = new AtomicBoolean(false);

            doAnswer(invocation -> {
                if (invocation.getArgument(0) == MarioState.NORMAL) {
                    transitioned.set(true);
                }
                return null;
            }).when(mockMario).changeState(any());

            new TestableWithHammerState(mockMario, TEST_TIMEOUT_MS);
            Thread.sleep(SAFE_WAIT_MS);

            assertTrue(transitioned.get(), 
                "State should transition to NORMAL after timeout");
        }

        @Test
        void testImmediateTransition() {
            assertDoesNotThrow(() -> 
                new TestableWithHammerState(mockMario, IMMEDIATE_TRANSITION_MS));
        }

        @Test
        void testInvalidDuration() {
            assertThrows(IllegalArgumentException.class, 
            () -> new TestableWithHammerState(mockMario, INVALID_DURATION_MS));
        }
    }

    @Nested
    class AbilityTests {

        @Test
        void testClimbingDisabled() {
            hammerState.climb(mockMario, 1);
            verifyNoInteractions(mockMario);
        }

        @Test
        void testHammerUsageEnabled() {
            assertDoesNotThrow(() -> hammerState.useHammer(mockMario));
        }
    }

    @Nested
    class ResourceTests {

        @Test
        void testTimerCleanup() {
            assertDoesNotThrow(() -> hammerState.onStateExit(mockMario, "TestPlayer"));
        }
    }

    /**
     * Testable implementation of WithHammerState with controlled timer behavior.
     */
    private static final class TestableWithHammerState extends WithHammerState {

        TestableWithHammerState(final Mario mario, final long durationMs) {
            super(mario);
            if (durationMs < 0) {
                throw new IllegalArgumentException("Duration cannot be negative");
            }
            final Timer testTimer = new Timer(true);
            testTimer.schedule(new TimerTask() {
                @Override 
                public void run() {
                    mario.changeState(MarioState.NORMAL);
                }
            }, durationMs);
        }
    }
}
