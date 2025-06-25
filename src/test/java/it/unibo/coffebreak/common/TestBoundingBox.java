package it.unibo.coffebreak.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.impl.common.BoundigBox;

/**
 * Unit tests for the {@link BoundigBox} record.
 * 
 * @author Alessandro Rebosio
 */
public class TestBoundingBox {

    /**
     * Tests the default constructor sets width and height to 0.
     */
    @Test
    void testDefaultConstructor() {
        BoundigBox box = new BoundigBox();
        assertEquals(0, box.width());
        assertEquals(0, box.height());
    }

    /**
     * Tests the parameterized constructor.
     */
    @Test
    void testParameterizedConstructor() {
        BoundigBox box = new BoundigBox(5, 10);
        assertEquals(5, box.width());
        assertEquals(10, box.height());
    }

    /**
     * Tests scaling the width.
     */
    @Test
    void testScaleWidth() {
        BoundigBox box = new BoundigBox(2, 3);
        BoundigBox scaled = box.scaleWidth(4);
        assertEquals(8, scaled.width());
        assertEquals(3, scaled.height());
    }

    /**
     * Tests scaling the height.
     */
    @Test
    void testScaleHeight() {
        BoundigBox box = new BoundigBox(2, 3);
        BoundigBox scaled = box.scaleHeight(5);
        assertEquals(2, scaled.width());
        assertEquals(15, scaled.height());
    }

    /**
     * Tests scaling both width and height.
     */
    @Test
    void testScale() {
        BoundigBox box = new BoundigBox(2, 3);
        BoundigBox scaled = box.scale(6);
        assertEquals(12, scaled.width());
        assertEquals(18, scaled.height());
    }

    /**
     * Tests the copy method.
     */
    @Test
    void testCopy() {
        BoundigBox box = new BoundigBox(7, 8);
        BoundigBox copy = box.copy();
        assertNotSame(box, copy);
        assertEquals(box.width(), copy.width());
        assertEquals(box.height(), copy.height());
    }
}
