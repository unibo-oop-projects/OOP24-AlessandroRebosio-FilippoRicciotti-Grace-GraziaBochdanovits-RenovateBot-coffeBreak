package it.unibo.coffebreak.model.mario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.entities.character.CharacterState;
import it.unibo.coffebreak.model.api.entities.collectible.Collectible;
import it.unibo.coffebreak.model.api.entities.platform.Platform;
import it.unibo.coffebreak.model.api.physics.Physics;
import it.unibo.coffebreak.model.impl.common.Dimension2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.common.Vector2D;
import it.unibo.coffebreak.model.impl.entities.mario.Mario;
import it.unibo.coffebreak.model.impl.entities.mario.states.NormalState;
import it.unibo.coffebreak.model.impl.score.GameScoreManager;

/**
 * Test class for {@link Mario} implementation.
 * This class verifies the behavior of Mario's movement, collisions, state changes,
 * and interactions with game entities.
 * 
 * <p>Uses Mockito for mocking dependencies like {@link Physics}, {@link GameScoreManager},
 * and various game entities.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
@ExtendWith(MockitoExtension.class)
class TestMario {

    private static final float TEST_DELTA_TIME = 0.1f;
    private static final float JUMP_VELOCITY_Y = 10f;
    private static final float MOVE_VELOCITY_X = 5f;
    private static final int COLLECTIBLE_POINTS = 100;
    private static final int TEST_VELOCITY = 10;

    @Mock private GameScoreManager scoreManager;
    @Mock private Physics physics;
    @Mock private Platform platform;
    @Mock private Collectible collectible;
    @Mock private CharacterState state;

    private Mario mario;
    private final Position2D initialPosition = new Position2D(0, 0);
    private final Dimension2D dimension = new Dimension2D(1, 1);

    /**
     * Initializes test environment before each test.
     * Creates a new Mario instance with mock dependencies.
     */
    @BeforeEach
    void setUp() {
        mario = new Mario(initialPosition, dimension, scoreManager, physics);
    }

    /**
     * Tests Mario's initial state after creation.
     */
    @Test
    void testInitialState() {
        assertTrue(mario.isOnGround());
        assertEquals(initialPosition, mario.getPosition());
        assertInstanceOf(NormalState.class, mario.getCurrentState());
    }

    /**
     * Tests jump mechanics.
     */
    @Test
    void testJump() {
        when(physics.calculateY(anyFloat(), eq(Command.JUMP)))
            .thenReturn(new Vector2D(0, JUMP_VELOCITY_Y));

        mario.jump(TEST_DELTA_TIME);
        assertFalse(mario.isOnGround());
        assertEquals(JUMP_VELOCITY_Y, mario.getVelocity().y());
    }

    /**
     * Tests left movement.
     */
    @Test
    void testMoveLeft() {
        when(physics.calculateX(anyFloat(), eq(Command.MOVE_LEFT)))
            .thenReturn(new Vector2D(-MOVE_VELOCITY_X, 0));

        mario.moveLeft(TEST_DELTA_TIME);
        assertFalse(mario.isFacingRight());
        assertEquals(-MOVE_VELOCITY_X, mario.getVelocity().x());
    }

    /**
     * Tests right movement.
     */
    @Test
    void testMoveRight() {
        when(physics.calculateX(anyFloat(), eq(Command.MOVE_RIGHT)))
            .thenReturn(new Vector2D(MOVE_VELOCITY_X, 0));

        mario.moveRight(TEST_DELTA_TIME);
        assertTrue(mario.isFacingRight());
        assertEquals(MOVE_VELOCITY_X, mario.getVelocity().x());
    }

    /**
     * Tests collision with platform.
     */
    @Test
    void testOnCollisionWithPlatform() {
        mario.onCollision(platform);
        assertTrue(mario.isOnGround());
    }

    /**
     * Tests collision with collectible item.
     */
    @Test
    void testOnCollisionWithCollectible() {
        when(collectible.getPointsValue()).thenReturn(COLLECTIBLE_POINTS);
        mario.onCollision(collectible);
        verify(scoreManager).earnPoints(COLLECTIBLE_POINTS);
        verify(collectible).collect(mario);
    }

    /**
     * Tests life loss mechanics.
     */
    @Test
    void testLoseLife() {
        final int initialLives = mario.getLives();
        mario.loseLife();
        assertEquals(initialLives - 1, mario.getLives());
    }

    /**
     * Tests state change mechanics.
     */
    @Test
    void testChangeState() {
        mario.changeState(state);
        verify(state).onEnter(mario);
        assertEquals(state, mario.getCurrentState());
    }

    /**
     * Tests reset to initial state.
     */
    @Test
    void testResetToInitialState() {
        mario.setVelocity(new Vector2D(TEST_VELOCITY, TEST_VELOCITY));
        mario.resetToInitialState();
        assertEquals(0, mario.getVelocity().x());
        assertEquals(0, mario.getVelocity().y());
        assertEquals(initialPosition, mario.getPosition());
    }
}
