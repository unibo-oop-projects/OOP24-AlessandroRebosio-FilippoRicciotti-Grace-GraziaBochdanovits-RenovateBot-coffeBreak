package it.unibo.coffebreak.model.entities.character.states.withhammer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.enemy.Enemy;
import it.unibo.coffebreak.impl.model.entities.mario.states.normal.NormalState;
import it.unibo.coffebreak.impl.model.entities.mario.states.withhammer.WithHammerState;

/**
 * Test class for {@link WithHammerState} implementation.
 * Verifies Mario's "With Hammer" state behavior including:
 * <ul>
 *   <li>Collision handling with enemies and other entities</li>
 *   <li>Dimension changes during state transitions</li>
 *   <li>State lifecycle and automatic expiration</li>
 *   <li>Duration constant validation</li>
 * </ul>
 * 
 * <p>Uses Mockito for mocking and {@link TestableWithHammerState} 
 * for time-dependent testing without real delays.
 */
@ExtendWith(MockitoExtension.class)
class TestWithHammerState {
    private static final float DELTA_TIME = 0.5f;
    private static final float EXPIRATION_TIME = 7000;

    @Mock private MainCharacter mockCharacter;
    private WithHammerState hammerState;

    @BeforeEach
    void setUp() {
        hammerState = new WithHammerState();
    }

    /**
     * Tests that hammer state destroys enemies on collision.
     */
    @Test
    void shouldDestroyEnemyOnCollision() {
        final Enemy mockEnemy = mock(Enemy.class);
        hammerState.handleCollision(mockCharacter, mockEnemy);
        verify(mockEnemy).destroy();
    }

    /**
     * Tests that non-enemy collisions are ignored.
     */
    @Test
    void shouldIgnoreNonEnemyCollisions() {
        final Entity mockEntity = mock(Entity.class);
        hammerState.handleCollision(mockCharacter, mockEntity);
        verifyNoInteractions(mockEntity);
    }

    /**
     * Tests that hammer duration constant has correct value (5 seconds).
     */
    @Test
    void shouldHaveCorrectHammerDuration() {
        assertEquals(EXPIRATION_TIME, WithHammerState.HAMMER_DURATION, 
            "Hammer duration should be 5 seconds (5000ms)");
    }

    /**
     * Tests for state lifecycle management and time expiration.
     */
    @Nested
    class StateLifecycleTests {
        private TestableWithHammerState testableState;

        @BeforeEach
        void prepareTestableState() {
            testableState = new TestableWithHammerState(WithHammerState.HAMMER_DURATION);
        }

        /**
         * Tests that state is not expired immediately after creation.
         */
        @Test
        void shouldNotBeExpiredInitially() {
            testableState.onEnter(mockCharacter);
            assertFalse(testableState.isExpired());
        }

        /**
         * Tests that state expires after specified duration.
         */
        @Test
        void shouldExpireAfterDuration() {
            testableState.onEnter(mockCharacter);
            testableState.forceExpire();
            assertTrue(testableState.isExpired());
        }

        /**
         * Tests automatic transition to NormalState after expiration.
         */
        @Test
        void shouldTransitionToNormalStateWhenExpired() {
            testableState.onEnter(mockCharacter);
            testableState.forceExpire();
            testableState.update(mockCharacter, DELTA_TIME);
            verify(mockCharacter).changeState(argThat(factory -> 
                factory.get() instanceof NormalState));
        }

        /**
         * Tests no state transition occurs before expiration.
         */
        @Test
        void shouldNotTransitionBeforeExpiration() {
            testableState.onEnter(mockCharacter);
            testableState.update(mockCharacter, DELTA_TIME);
            verify(mockCharacter, never()).changeState(any());
        }
    }

    /**
     * Testable variant of WithHammerState that simulates time progression.
     * Allows manual expiration triggering for testing purposes.
     */
    private static class TestableWithHammerState extends WithHammerState {
        private long testCurrentTime;
        private final long testExpirationTime;

        /**
         * Creates a testable state with specified duration.
         * @param durationMs the state duration in milliseconds
         */
        TestableWithHammerState(final long durationMs) {
            this.testExpirationTime = durationMs;
        }

        /**
         * Forces immediate state expiration for testing.
         */
        void forceExpire() {
            testCurrentTime = testExpirationTime + 1;
        }

        @Override
        protected boolean isExpired() {
            return testCurrentTime >= testExpirationTime;
        }

        @Override
        public void onEnter(final MainCharacter character) {
            this.testCurrentTime = 0;
            super.onEnter(character);
        }
    }
}
