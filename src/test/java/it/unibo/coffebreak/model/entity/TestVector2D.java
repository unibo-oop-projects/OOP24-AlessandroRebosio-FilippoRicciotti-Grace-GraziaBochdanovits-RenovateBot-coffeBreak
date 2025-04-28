package it.unibo.coffebreak.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.impl.utility.Vector2D;

/**
 * Unit tests for {@link Vector2D}.
 */
class TestVector2D {

    private static final float X_VALUE = 3.5f;
    private static final float NEW_X = 1.5f;
    private static final float Y_VALUE = -2.0f;
    private static final float NEW_Y = 3.0f;
    private static final float DELTA = 0.001f;
    private static final float SCALAR = 2.0f;
    private Vector2D vector;
    private Vector2D testVector;

    @BeforeEach
    void setUp() {
        vector = new Vector2D(X_VALUE, Y_VALUE);
        testVector = new Vector2D(NEW_X, NEW_Y);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(X_VALUE, vector.getX(), DELTA);
        assertEquals(Y_VALUE, vector.getY(), DELTA);
    }

    @Test
    void testSetters() {
        vector.setX(NEW_X);
        vector.setY(NEW_Y);
        assertEquals(NEW_X, vector.getX(), DELTA);
        assertEquals(NEW_Y, vector.getY(), DELTA);
    }

    @Test
    void testAdd() {
        final Vector2D result = vector.add(testVector);
        assertEquals(X_VALUE + NEW_X, result.getX(), DELTA);
        assertEquals(Y_VALUE + NEW_Y, result.getY(), DELTA);

        assertEquals(X_VALUE, vector.getX(), DELTA);
        assertEquals(Y_VALUE, vector.getY(), DELTA);
    }

    @Test
    void testSubtract() {
        final Vector2D result = vector.subtract(testVector);
        assertEquals(X_VALUE - NEW_X, result.getX(), DELTA);
        assertEquals(Y_VALUE - NEW_Y, result.getY(), DELTA);

        assertEquals(X_VALUE, vector.getX(), DELTA);
        assertEquals(Y_VALUE, vector.getY(), DELTA);
    }

    @Test
    void testMultiply() {
        final Vector2D result = vector.multiply(SCALAR);
        assertEquals(X_VALUE * SCALAR, result.getX(), DELTA);
        assertEquals(Y_VALUE * SCALAR, result.getY(), DELTA);

        assertEquals(X_VALUE, vector.getX(), DELTA);
        assertEquals(Y_VALUE, vector.getY(), DELTA);
    }

    @Test
    void testEquals() {
        final Vector2D sameValues = new Vector2D(X_VALUE, Y_VALUE);
        final Vector2D differentValues = new Vector2D(NEW_X, NEW_Y);

        assertEquals(vector, sameValues);
        assertNotEquals(vector, differentValues);
        assertNotEquals(vector, null);
        assertNotEquals(vector, new Object());
    }

    @Test
    void testHashCode() {
        final Vector2D sameValues = new Vector2D(X_VALUE, Y_VALUE);
        assertEquals(vector.hashCode(), sameValues.hashCode());
    }

}
