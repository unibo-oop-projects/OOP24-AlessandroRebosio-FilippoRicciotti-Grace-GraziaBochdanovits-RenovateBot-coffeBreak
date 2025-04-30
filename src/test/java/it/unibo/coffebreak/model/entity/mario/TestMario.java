package it.unibo.coffebreak.model.entity.mario;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.model.api.entity.item.Collectible;
import it.unibo.coffebreak.model.impl.entity.GameEntity;
import it.unibo.coffebreak.model.impl.entity.LivesManager;
import it.unibo.coffebreak.model.impl.entity.MarioState;
import it.unibo.coffebreak.model.impl.entity.mario.Mario;
import it.unibo.coffebreak.model.impl.score.GameScoreManager;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;

/**
 * Comprehensive test class for {@link Mario} entity behavior.
 * 
 * <p><b>Testing Approach:</b>
 * <ul>
 *   <li><b>Isolated testing:</b> Mocks allow testing Mario's behavior without 
 *       relying on real implementations of {@link GameScoreManager} or {@link Collectible}.</li>
 *   <li><b>Controlled scenarios:</b> We simulate specific responses from dependencies
 *       (e.g., forcing {@link Collectible#isCollected()} to return false).</li>
 *   <li><b>Interaction verification:</b> We verify that Mario correctly interacts
 *       with other components (e.g., checks {@code earnPoints()} is called).</li>
 * </ul>
 * 
 * <p>The tests are organized into logical groups using nested test classes:
 * <ul>
 *   <li>{@link StateTests}: State transitions and validation</li>
 *   <li>{@link MovementTests}: Position and velocity handling</li>
 *   <li>{@link ItemTests}: Item collection and power-up effects</li>
 *   <li>{@link LifeTests}: Life management and game over conditions</li>
 *   <li>{@link EdgeCases}: Boundary conditions and exception handling</li>
 * </ul>
 */
@ExtendWith(MockitoExtension.class)
class TestMario {
    /** Default number of lives for Mario. */
    private static final int DEFAULT_LIVES = 3;

    /** Time delta used for movement tests (ms). */
    private static final int TEST_DELTA_TIME = 500;

    /** Test velocity value for movement tests. */
    private static final int TEST_VELOCITY = 5;

    private static final Position INITIAL_POSITION = new Position(0, 0);
    private static final Dimension TEST_DIMENSION = new Dimension(1, 1);
    private static final String PLAYER_NAME = "TestPlayer";

    @Mock private GameScoreManager mockScoreManager;
    @Mock private Collectible mockHammer;
    @Mock private Collectible mockCoin;
    @Mock private Collectible mockCollectedItem;
    @Mock private GameEntity mockGameEntityItem;

    private Mario mario;

    /**
     * Initializes test environment before each test case.
     * Creates a new Mario instance with:
     * <ul>
     *   <li>Default position at origin (0,0)</li>
     *   <li>Unit dimensions (1x1)</li>
     *   <li>Mock score manager</li>
     *   <li>Fresh lives manager with default lives</li>
     * </ul>
     */
    @BeforeEach
    void setUp() {
        final LivesManager livesManager = new LivesManager();
        mario = new Mario(INITIAL_POSITION, TEST_DIMENSION, mockScoreManager, 
                            livesManager, PLAYER_NAME);
    }

    /**
     * Helper method to simulate Mario losing all lives.
     * <p>Repeatedly calls {@link Mario#loseLife()} until all lives are exhausted.
     */
    private void killMario() {
        for (int i = 0; i < DEFAULT_LIVES; i++) {
            mario.loseLife();
        }
    }

    /**
     * Tests for Mario state management and transitions.
     */
    @Nested
    class StateTests {
        /**
         * Verifies Mario's initial state conditions.
         */
        @Test
        void testInitialConditions() {
            assertAll(
                () -> assertEquals(INITIAL_POSITION, mario.getPosition()),
                () -> assertEquals(TEST_DIMENSION, mario.getDimension()),
                () -> assertEquals(DEFAULT_LIVES, mario.getLives()),
                () -> assertEquals(MarioState.NORMAL, mario.getCurrentStateType()),
                () -> assertTrue(mario.isAlive()),
                () -> assertTrue(mario.isOnGround()),
                () -> assertEquals(new Vector2D(0, 0), mario.getVelocity()),
                () -> assertEquals(PLAYER_NAME, mario.getPlayerName())
            );
        }

        /**
         * Tests valid state transition to WITH_HAMMER state.
         * <p>Verifies that Mario's state properly updates when changing to hammer state.
         */
        @Test
        void testWithHammerTransition() {
            mario.changeState(MarioState.WITH_HAMMER);
            assertEquals(MarioState.WITH_HAMMER, mario.getCurrentStateType());
        }

        /**
         * Tests state transition constraints from DEAD state.
         * <p>Verifies that:
         * <ul>
         *   <li>Transition to NORMAL state is allowed</li>
         *   <li>Transition to WITH_HAMMER state is blocked</li>
         * </ul>
         */
        @Test
        void testDeadStateTransitions() {
            killMario();
            assertAll(
                () -> assertEquals(MarioState.DEAD, mario.getCurrentStateType()),
                () -> assertFalse(mario.isAlive()),
                () -> assertTrue(mario.getCurrentStateType().canTransitionTo(MarioState.NORMAL)),
                () -> assertFalse(mario.getCurrentStateType().canTransitionTo(MarioState.WITH_HAMMER))
            );
        }
    }

    /**
     * Tests for Mario's movement physics and position updates.
     */
    @Nested
    class MovementTests {
        /**
         * Tests position updates based on velocity and time.
         * <p>Verifies that:
         * <ul>
         *   <li>Position updates correctly according to velocity</li>
         *   <li>Movement doesn't trigger score changes</li>
         * </ul>
         */
        @Test
        void testMovementUpdate() {
            mario.setVelocity(new Vector2D(1, 0)); 
            mario.update(TEST_DELTA_TIME);

            assertAll(
                () -> assertEquals(0.5f, mario.getPosition().x()),
                () -> verify(mockScoreManager, never()).earnPoints(anyInt())
            );
        }

        /**
         * Tests that zero delta time results in no movement.
         * <p>Ensures position remains unchanged when time delta is zero.
         */
        @Test
        void testZeroDeltaTime() {
            mario.update(0);
            assertEquals(INITIAL_POSITION, mario.getPosition());
        }
    }

    /**
     * Tests for item collection mechanics.
     */
    @Nested
    class ItemTests {
        @Test
        void testWithMockDelegateApproach() {
            final GameEntity gameEntity = mock(GameEntity.class);
            when(gameEntity.getPosition()).thenReturn(new Position(0, 0));
            when(gameEntity.getDimension()).thenReturn(new Dimension(1, 1));

            final Collectible collectible = mock(Collectible.class, AdditionalAnswers.delegatesTo(new Object()));

            assertTrue(mario.collidesWith(gameEntity));

            final List<Collectible> items = new ArrayList<>();
            items.add(collectible);
            mario.checkItemCollisions(items);
    }
}
    /**
     * Tests for life management system.
     */
    @Nested
    class LifeTests {
        /**
         * Tests life loss mechanics.
         * <p>Verifies that losing a life:
         * <ul>
         *   <li>Decrements life count</li>
         *   <li>Resets position to origin</li>
         *   <li>Maintains NORMAL state</li>
         * </ul>
         */
        @Test
        void testLifeLossMechanics() {
            mario.setVelocity(new Vector2D(TEST_VELOCITY, TEST_VELOCITY));
            mario.setOnGround(false);
            final int initialLives = mario.getLives();

            mario.loseLife();

            assertAll(
                () -> assertEquals(initialLives - 1, mario.getLives()),
                () -> assertEquals(INITIAL_POSITION, mario.getPosition()),
                () -> assertEquals(MarioState.NORMAL, mario.getCurrentStateType()),
                () -> assertEquals(new Vector2D(0, 0), mario.getVelocity())
            );
        }

        /**
         * Tests game over sequence.
         * <p>Verifies that when all lives are lost:
         * <ul>
         *   <li>Life counter reaches zero</li>
         *   <li>State changes to DEAD</li>
         *   <li>Game over is triggered</li>
         * </ul>
         */
        @Test
        void testGameOverSequence() {
            killMario();
            assertAll(
                () -> assertEquals(0, mario.getLives()),
                () -> assertEquals(MarioState.DEAD, mario.getCurrentStateType()),
                () -> assertFalse(mario.isAlive())
            );
        }
    }

    /**
     * Tests for edge cases and boundary conditions.
     */
    @Nested
    class EdgeCases {
        /**
         * Tests jump safety in different states.
         * <p>Verifies that jump command:
         * <ul>
         *   <li>Doesn't throw exceptions in NORMAL state</li>
         *   <li>Doesn't throw exceptions in WITH_HAMMER state</li>
         * </ul>
         */
        @Test
        void testJumpSafety() {
            assertAll(
                () -> assertDoesNotThrow(mario::jump),
                () -> {
                    mario.changeState(MarioState.WITH_HAMMER);
                    assertDoesNotThrow(mario::jump);
                }
            );
        }

        /**
         * Tests zero climb input handling.
         * <p>Verifies that zero climb direction:
         * <ul>
         *   <li>Doesn't change Mario's position</li>
         * </ul>
         */
        @Test
        void testZeroClimb() {
            mario.climb(0);
            assertEquals(INITIAL_POSITION, mario.getPosition());
        }
    }
}
