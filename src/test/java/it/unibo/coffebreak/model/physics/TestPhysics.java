package it.unibo.coffebreak.model.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.physics.Physics;
import it.unibo.coffebreak.model.impl.common.Vector2D;
import it.unibo.coffebreak.model.impl.physics.GamePhysics;

/**
 * Test class for {@link GamePhysics} implementation of the {@link Physics}
 * interface. This class verifies the correctness of physical calculations for
 * game movement.
 * 
 * <p>
 * The tests use a standard delta time value approximating 60 FPS (0.016
 * seconds)
 * but also verify behavior with different time values.
 * 
 * @author Alessandro Rebosio
 */
class TestPhysics {

    /** Standard delta time value approximating 60 FPS frame time. */
    private static final float DELTA_TIME = 0.016f;

    /** The physics system under test. */
    private Physics gamePhysics;

    /**
     * Sets up the test fixture before each test method.
     * Initializes a new instance of {@link GamePhysics}.
     */
    @BeforeEach
    void setUp() {
        gamePhysics = new GamePhysics();
    }

    /**
     * Tests horizontal movement calculation for right direction.
     * Verifies that MOVE_RIGHT command produces positive X velocity
     * with magnitude equal to BASE_SPEED * deltaTime.
     */
    @Test
    void testCalculateXMoveRight() {
        final Vector2D result = gamePhysics.calculateX(DELTA_TIME, Command.MOVE_RIGHT);
        final Vector2D expected = new Vector2D(GamePhysics.BASE_SPEED * DELTA_TIME, 0f);
        assertEquals(expected, result);
    }

    /**
     * Tests horizontal movement calculation for left direction.
     * Verifies that MOVE_LEFT command produces negative X velocity
     * with magnitude equal to BASE_SPEED * deltaTime.
     */
    @Test
    void testCalculateXMoveLeft() {
        final Vector2D result = gamePhysics.calculateX(DELTA_TIME, Command.MOVE_LEFT);
        final Vector2D expected = new Vector2D(-GamePhysics.BASE_SPEED * DELTA_TIME, 0f);
        assertEquals(expected, result);
    }

    /**
     * Tests horizontal movement calculation for non-movement commands.
     * Verifies that commands not related to horizontal movement (like JUMP)
     * return the ZERO_VECTOR.
     */
    @Test
    void testCalculateXOtherCommand() {
        final Vector2D result = gamePhysics.calculateX(DELTA_TIME, Command.JUMP);
        assertEquals(GamePhysics.ZERO_VECTOR, result);
    }

    /**
     * Tests vertical movement calculation for upward direction.
     * Verifies that MOVE_UP command produces positive Y velocity
     * with magnitude equal to BASE_SPEED * deltaTime.
     */
    @Test
    void testCalculateYMoveUp() {
        final Vector2D result = gamePhysics.calculateY(DELTA_TIME, Command.MOVE_UP);
        final Vector2D expected = new Vector2D(0f, GamePhysics.BASE_SPEED * DELTA_TIME);
        assertEquals(expected, result);
    }

    /**
     * Tests vertical movement calculation for downward direction.
     * Verifies that MOVE_DOWN command produces negative Y velocity
     * with magnitude equal to BASE_SPEED * deltaTime.
     */
    @Test
    void testCalculateYMoveDown() {
        final Vector2D result = gamePhysics.calculateY(DELTA_TIME, Command.MOVE_DOWN);
        final Vector2D expected = new Vector2D(0f, -GamePhysics.BASE_SPEED * DELTA_TIME);
        assertEquals(expected, result);
    }

    /**
     * Tests jump physics calculation.
     * Verifies that JUMP command produces positive Y velocity
     * with magnitude equal to JUMP_FORCE * deltaTime.
     */
    @Test
    void testCalculateYJump() {
        final Vector2D result = gamePhysics.calculateY(DELTA_TIME, Command.JUMP);
        final Vector2D expected = new Vector2D(0f, GamePhysics.JUMP_FORCE * DELTA_TIME);
        assertEquals(expected, result);
    }

    /**
     * Tests default gravity behavior.
     * Verifies that when no vertical movement command is given,
     * the system applies a downward velocity (simulating gravity)
     * with magnitude equal to BASE_SPEED * deltaTime.
     */
    @Test
    void testCalculateYDefaultGravity() {
        final Vector2D result = gamePhysics.calculateY(DELTA_TIME, Command.MOVE_RIGHT);
        final Vector2D expected = new Vector2D(0f, -GamePhysics.BASE_SPEED * DELTA_TIME);
        assertEquals(expected, result);
    }

    /**
     * Tests immutability of the ZERO_VECTOR constant.
     * Verifies that modifications to a copy of ZERO_VECTOR
     * do not affect the original constant.
     */
    @Test
    void testZeroVectorIsImmutable() {
        final Vector2D modified = new Vector2D(GamePhysics.ZERO_VECTOR.x() + 1, GamePhysics.ZERO_VECTOR.y() + 1);
        assertNotEquals(GamePhysics.ZERO_VECTOR, modified);
    }

    /**
     * Tests physics calculations with different time values.
     * Verifies that movement calculations scale correctly
     * with varying deltaTime values.
     */
    @Test
    void testDifferentDeltaTimes() {
        final float customDeltaTime = 0.5f;
        final Vector2D result = gamePhysics.calculateX(customDeltaTime, Command.MOVE_RIGHT);
        final Vector2D expected = new Vector2D(GamePhysics.BASE_SPEED * customDeltaTime, 0f);
        assertEquals(expected, result);
    }
}
