package it.unibo.coffebreak.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.impl.common.Vector;

/**
 * Unit tests for the {@link Vector} record.
 * 
 * @author Alessandro Rebosio
 */
public class TestVector {

    /**
     * Tests the default constructor creates a zero vector.
     */
    @Test
    void testDefaultConstructor() {
        Vector v = new Vector();
        assertEquals(0.0f, v.x());
        assertEquals(0.0f, v.y());
    }

    /**
     * Tests the parameterized constructor.
     */
    @Test
    void testParameterizedConstructor() {
        Vector v = new Vector(2.5f, -1.5f);
        assertEquals(2.5f, v.x());
        assertEquals(-1.5f, v.y());
    }

    /**
     * Tests the sum method with a valid vector.
     */
    @Test
    void testSum() {
        Vector v1 = new Vector(1.0f, 2.0f);
        Vector v2 = new Vector(3.0f, 4.0f);
        Vector result = v1.sum(v2);
        assertEquals(4.0f, result.x());
        assertEquals(6.0f, result.y());
    }

    /**
     * Tests that sum throws NullPointerException if vector is null.
     */
    @Test
    void testSumNull() {
        Vector v = new Vector(1.0f, 2.0f);
        assertThrows(NullPointerException.class, () -> v.sum(null));
    }

    /**
     * Tests the mul method for scalar multiplication.
     */
    @Test
    void testMul() {
        Vector v = new Vector(2.0f, -3.0f);
        Vector result = v.mul(2.5f);
        assertEquals(5.0f, result.x());
        assertEquals(-7.5f, result.y());
    }

    /**
     * Tests the copy method returns a new instance with the same values.
     */
    @Test
    void testCopy() {
        Vector v = new Vector(7.5f, -3.2f);
        Vector copy = v.copy();
        assertNotSame(v, copy);
        assertEquals(v.x(), copy.x());
        assertEquals(v.y(), copy.y());
    }
}
