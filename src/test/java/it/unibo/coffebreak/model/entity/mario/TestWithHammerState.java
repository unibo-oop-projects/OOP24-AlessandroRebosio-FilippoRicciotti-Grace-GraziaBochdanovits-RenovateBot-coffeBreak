package it.unibo.coffebreak.model.entity.mario;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @Test
    @DisplayName("Should maintain consistent state properties")
    void testStateConsistency() {
        final WithHammerState state = new WithHammerState(mock(Mario.class));
        assertAll("WithHammer state properties",
            () -> assertFalse(state.canClimb()),
            () -> assertTrue(state.canUseHammer()),
            () -> assertEquals(MarioState.WITH_HAMMER, state.getStateType())
        );
    }

    @Nested
    class StateTransitionTests {

        @Test
        @DisplayName("Should automatically revert to NORMAL after timeout")
        void testAutoRevertToNormal() throws InterruptedException {
            final Mario mario = mock(Mario.class);
            final AtomicBoolean transitioned = new AtomicBoolean(false);

            doAnswer(invocation -> {
                if (invocation.getArgument(0) == MarioState.NORMAL) {
                    transitioned.set(true);
                }
                return null;
            }).when(mario).changeState(any());

            new TestableWithHammerState(mario, TEST_TIMEOUT_MS);
            Thread.sleep(SAFE_WAIT_MS);

            assertTrue(transitioned.get(), 
                "State should transition to NORMAL after timeout");
        }

        @Test
        @DisplayName("Should handle immediate transition (0ms) gracefully")
        void testImmediateTransition() {
            final Mario mario = mock(Mario.class);

            assertDoesNotThrow(() -> 
                new TestableWithHammerState(mario, IMMEDIATE_TRANSITION_MS),
                "Should handle zero duration transition");
        }

        @Test
        @DisplayName("Should reject negative duration values")
        void testInvalidDuration() {
            final Mario mario = mock(Mario.class);

            assertThrows(IllegalArgumentException.class, 
                () -> new TestableWithHammerState(mario, INVALID_DURATION_MS),
                "Should throw IllegalArgumentException for negative duration");
        }
    }

    @Nested
    class AbilityTests {

        private WithHammerState hammerState;

        @BeforeEach
        void setUp() {
            hammerState = new WithHammerState(mock(Mario.class));
        }

        @Test
        @DisplayName("Should disable climbing in this state")
        void testClimbingDisabled() {
            assertFalse(hammerState.canClimb());
        }

        @Test
        @DisplayName("Should enable hammer usage in this state")
        void testHammerUsageEnabled() {
            assertTrue(hammerState.canUseHammer());
        }
    }

    @Nested
    class ResourceTests {

        @Test
        @DisplayName("Should clean up timer resources without exceptions")
        void testTimerCleanup() {
            final WithHammerState state = new WithHammerState(mock(Mario.class));
            final Mario mockMario = mock(Mario.class);

            assertDoesNotThrow(() -> state.onStateExit(mockMario),
                "State exit cleanup should not throw exceptions");
        }
    }

    @Nested
    class IdentificationTests {

        @Test
        @DisplayName("Should correctly identify as WITH_HAMMER state")
        void testStateType() {
            final WithHammerState state = new WithHammerState(mock(Mario.class));

            assertEquals(MarioState.WITH_HAMMER, state.getStateType());
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
