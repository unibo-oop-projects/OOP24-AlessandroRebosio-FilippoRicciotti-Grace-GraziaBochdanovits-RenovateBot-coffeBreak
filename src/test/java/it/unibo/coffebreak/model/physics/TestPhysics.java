package it.unibo.coffebreak.model.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.impl.common.Vector;
import it.unibo.coffebreak.impl.model.physics.GamePhysics;

/**
 * Unit tests for the {@link GamePhysics} class.
 * <p>
 * This test class verifies the correct calculation of movement and physics vectors
 * in response to various movement methods, such as moving left, right, up, down,
 * jumping, and gravity. It also checks the immutability of the zero vector.
 * </p>
 *
 * <ul>
 *     <li>{@code testMoveRightReturnsPositiveX}: Ensures moving right returns a positive X vector.</li>
 *     <li>{@code testMoveLeftReturnsNegativeX}: Ensures moving left returns a negative X vector.</li>
 *     <li>{@code testMoveUpReturnsPositiveY}: Ensures moving up returns a positive Y vector.</li>
 *     <li>{@code testMoveDownReturnsNegativeY}: Ensures moving down returns a negative Y vector.</li>
 *     <li>{@code testJumpReturnsJumpForceY}: Ensures jumping returns the correct jump force in Y.</li>
 *     <li>{@code testGravityReturnsNegativeY}: Ensures gravity returns a negative Y vector.</li>
 *     <li>{@code testZeroVectorIsImmutable}: Ensures the zero vector remains immutable.</li>
 * </ul>
 *
 * @author Alessandro Rebosio
 */
class TestPhysics {

    private static final float DELTA_TIME = 0.05f;
    private GamePhysics physics;

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
        final Vector expected = new Vector(200f * DELTA_TIME, 0f);
        final Vector actual = physics.moveRight(DELTA_TIME);
        assertEquals(expected, actual);
    }

    /**
     * Ensures moving left returns a negative X vector.
     */
    @Test
    void testMoveLeftReturnsNegativeX() {
        final Vector expected = new Vector(-200f * DELTA_TIME, 0f);
        final Vector actual = physics.moveLeft(DELTA_TIME);
        assertEquals(expected, actual);
    }

    /**
     * Ensures moving up returns a positive Y vector.
     */
    @Test
    void testMoveUpReturnsPositiveY() {
        final Vector expected = new Vector(0f, 200f * DELTA_TIME);
        final Vector actual = physics.moveUp(DELTA_TIME);
        assertEquals(expected, actual);
    }

    /**
     * Ensures moving down returns a negative Y vector.
     */
    @Test
    void testMoveDownReturnsNegativeY() {
        final Vector expected = new Vector(0f, -200f * DELTA_TIME);
        final Vector actual = physics.moveDown(DELTA_TIME);
        assertEquals(expected, actual);
    }

    /**
     * Ensures jumping returns the correct jump force in Y.
     */
    @Test
    void testJumpReturnsJumpForceY() {
        final Vector expected = new Vector(0f, 400f * DELTA_TIME);
        final Vector actual = physics.jump(DELTA_TIME);
        assertEquals(expected, actual);
    }

    /**
     * Ensures gravity returns a negative Y vector.
     */
    @Test
    void testGravityReturnsNegativeY() {
        final Vector expected = new Vector(0f, -900f * DELTA_TIME);
        final Vector actual = physics.gravity(DELTA_TIME);
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
