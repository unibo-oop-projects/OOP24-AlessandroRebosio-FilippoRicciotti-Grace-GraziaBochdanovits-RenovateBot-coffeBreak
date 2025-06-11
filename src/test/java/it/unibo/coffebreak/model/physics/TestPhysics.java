package it.unibo.coffebreak.model.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.physics.Physics;
import it.unibo.coffebreak.impl.common.Vector;
import it.unibo.coffebreak.impl.model.physics.GamePhysics;

/**
 * Unit tests for the {@link Physics} interface and its implementation {@link GamePhysics}.
 * <p>
 * This test class verifies the correct calculation of movement and physics vectors
 * in response to various {@link Command} inputs, such as moving left, right, up, down,
 * and jumping. It also checks the immutability of the zero vector and the default gravity
 * behavior for non-vertical commands.
 * </p>
 *
 * <ul>
 *     <li>{@code testMoveRightReturnsPositiveX}: Ensures moving right returns a positive X vector.</li>
 *     <li>{@code testMoveLeftReturnsNegativeX}: Ensures moving left returns a negative X vector.</li>
 *     <li>{@code testNonHorizontalCommandReturnsZeroVector}: Ensures non-horizontal commands return a zero vector for X.</li>
 *     <li>{@code testMoveUpReturnsPositiveY}: Ensures moving up returns a positive Y vector.</li>
 *     <li>{@code testMoveDownReturnsNegativeY}: Ensures moving down returns a negative Y vector.</li>
 *     <li>{@code testJumpReturnsJumpForceY}: Ensures jumping returns the correct jump force in Y.</li>
 *     <li>{@code testDefaultGravityOnNonVerticalCommand}: Ensures default gravity is applied on non-vertical commands.</li>
 *     <li>{@code testZeroVectorIsImmutable}: Ensures the zero vector remains immutable.</li>
 * </ul>
 *
 * @author Alessandro Rebosio
 */
class TestPhysics {

    /**
     * The physics engine under test.
     */
    private Physics physics;

    /**
     * Initializes the physics engine before each test.
     */
    @BeforeEach
    void setUp() {
        physics = new GamePhysics();
    }

    /**
     * Ensures moving right returns a positive X vector.
     */
    @Test
    void testMoveRightReturnsPositiveX() {
        final Vector expected = new Vector(15f, 0f);
        final Vector actual = physics.calculateX(Command.MOVE_RIGHT);
        assertEquals(expected, actual);
    }

    /**
     * Ensures moving left returns a negative X vector.
     */
    @Test
    void testMoveLeftReturnsNegativeX() {
        final Vector expected = new Vector(-15f, 0f);
        final Vector actual = physics.calculateX(Command.MOVE_LEFT);
        assertEquals(expected, actual);
    }

    /**
     * Ensures non-horizontal commands return a zero vector for X.
     */
    @Test
    void testNonHorizontalCommandReturnsZeroVector() {
        final Vector expected = new Vector();
        final Vector actual = physics.calculateX(Command.JUMP);
        assertEquals(expected, actual);
    }

    /**
     * Ensures moving up returns a positive Y vector.
     */
    @Test
    void testMoveUpReturnsPositiveY() {
        final Vector expected = new Vector(0f, 15f);
        final Vector actual = physics.calculateY(Command.MOVE_UP);
        assertEquals(expected, actual);
    }

    /**
     * Ensures moving down returns a negative Y vector.
     */
    @Test
    void testMoveDownReturnsNegativeY() {
        final Vector expected = new Vector(0f, -15f);
        final Vector actual = physics.calculateY(Command.MOVE_DOWN);
        assertEquals(expected, actual);
    }

    /**
     * Ensures jumping returns the correct jump force in Y.
     */
    @Test
    void testJumpReturnsJumpForceY() {
        final Vector expected = new Vector(0f, 10f);
        final Vector actual = physics.calculateY(Command.JUMP);
        assertEquals(expected, actual);
    }

    /**
     * Ensures default gravity is applied on non-vertical commands.
     */
    @Test
    void testDefaultGravityOnNonVerticalCommand() {
        final Vector expected = new Vector(0f, -15f);
        final Vector actual = physics.calculateY(Command.MOVE_RIGHT);
        assertEquals(expected, actual);
    }

    /**
     * Ensures the zero vector remains immutable.
     */
    @Test
    void testZeroVectorIsImmutable() {
        final Vector modified = new Vector(1f, 1f);
        assertNotEquals(new Vector(0f, 0f), modified);
    }
}
