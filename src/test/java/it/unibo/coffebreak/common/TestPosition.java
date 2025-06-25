package it.unibo.coffebreak.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.common.Vector;
import it.unibo.coffebreak.impl.common.BoundigBox;

/**
 * Unit tests for the {@link Position} record.
 */
public class TestPosition {

    /**
     * Tests the creation of a Position with given coordinates.
     */
    @Test
    void testConstructor() {
        Position pos = new Position(1.5f, -2.0f);
        assertEquals(1.5f, pos.x());
        assertEquals(-2.0f, pos.y());
    }

    /**
     * Tests the sum method with a valid vector.
     */
    @Test
    void testSum() {
        Position pos = new Position(2.0f, 3.0f);
        Vector vec = new Vector(1.0f, -1.5f);
        Position result = pos.sum(vec);
        assertEquals(3.0f, result.x());
        assertEquals(1.5f, result.y());
    }

    /**
     * Tests that sum throws NullPointerException if vector is null.
     */
    @Test
    void testSumNullVector() {
        Position pos = new Position(1.0f, 1.0f);
        assertThrows(NullPointerException.class, () -> pos.sum(null));
    }

    /**
     * Tests the scalePosition method with a valid BoundigBox.
     */
    @Test
    void testScalePosition() {
        Position pos = new Position(2.0f, 3.0f);
        BoundigBox box = new BoundigBox(4, 5);
        Position scaled = pos.scalePosition(box);
        assertEquals(8.0f, scaled.x());
        assertEquals(15.0f, scaled.y());
    }

    /**
     * Tests that scalePosition throws NullPointerException if dimension is null.
     */
    @Test
    void testScalePositionNull() {
        Position pos = new Position(1.0f, 1.0f);
        assertThrows(NullPointerException.class, () -> pos.scalePosition(null));
    }

    /**
     * Tests the copy method returns a new instance with the same values.
     */
    @Test
    void testCopy() {
        Position pos = new Position(7.5f, -3.2f);
        Position copy = pos.copy();
        assertNotSame(pos, copy);
        assertEquals(pos.x(), copy.x());
        assertEquals(pos.y(), copy.y());
    }
}
