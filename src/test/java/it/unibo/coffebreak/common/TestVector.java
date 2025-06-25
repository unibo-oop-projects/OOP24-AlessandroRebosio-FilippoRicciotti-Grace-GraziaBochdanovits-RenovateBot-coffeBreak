package it.unibo.coffebreak.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.impl.common.Vector;

/**
 * Unit tests for the {@link Vector} record.
 * 
 * @author Alessandro Rebosio
 */
class TestVector {

    private static final float ZERO = 0.0f;
    private static final float PARAM_X = 2.5f;
    private static final float PARAM_Y = -1.5f;
    private static final float SUM1_X = 1.0f;
    private static final float SUM1_Y = 2.0f;
    private static final float SUM2_X = 3.0f;
    private static final float SUM2_Y = 4.0f;
    private static final float EXPECTED_SUM_X = SUM1_X + SUM2_X;
    private static final float EXPECTED_SUM_Y = SUM1_Y + SUM2_Y;
    private static final float MUL_X = 2.0f;
    private static final float MUL_Y = -3.0f;
    private static final float MUL_FACTOR = 2.5f;
    private static final float EXPECTED_MUL_X = MUL_X * MUL_FACTOR;
    private static final float EXPECTED_MUL_Y = MUL_Y * MUL_FACTOR;
    private static final float COPY_X = 7.5f;
    private static final float COPY_Y = -3.2f;

    /**
     * Tests the default constructor creates a zero vector.
     */
    @Test
    void testDefaultConstructor() {
        final Vector v = new Vector();
        assertEquals(ZERO, v.x());
        assertEquals(ZERO, v.y());
    }

    /**
     * Tests the parameterized constructor.
     */
    @Test
    void testParameterizedConstructor() {
        final Vector v = new Vector(PARAM_X, PARAM_Y);
        assertEquals(PARAM_X, v.x());
        assertEquals(PARAM_Y, v.y());
    }

    /**
     * Tests the sum method with a valid vector.
     */
    @Test
    void testSum() {
        final Vector v1 = new Vector(SUM1_X, SUM1_Y);
        final Vector v2 = new Vector(SUM2_X, SUM2_Y);
        final Vector result = v1.sum(v2);
        assertEquals(EXPECTED_SUM_X, result.x());
        assertEquals(EXPECTED_SUM_Y, result.y());
    }

    /**
     * Tests the mul method for scalar multiplication.
     */
    @Test
    void testMul() {
        final Vector v = new Vector(MUL_X, MUL_Y);
        final Vector result = v.mul(MUL_FACTOR);
        assertEquals(EXPECTED_MUL_X, result.x());
        assertEquals(EXPECTED_MUL_Y, result.y());
    }

    /**
     * Tests the copy method returns a new instance with the same values.
     */
    @Test
    void testCopy() {
        final Vector v = new Vector(COPY_X, COPY_Y);
        final Vector copy = v.copy();
        assertNotSame(v, copy);
        assertEquals(v.x(), copy.x());
        assertEquals(v.y(), copy.y());
    }
}
