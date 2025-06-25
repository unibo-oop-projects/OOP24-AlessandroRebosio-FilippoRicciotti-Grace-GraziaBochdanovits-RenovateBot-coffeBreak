package it.unibo.coffebreak.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.impl.common.BoundigBox;

/**
 * Unit tests for the {@link BoundigBox} record.
 * 
 * @author Alessandro Rebosio
 */
class TestBoundingBox {

    private static final int WIDTH = 2;
    private static final int HEIGHT = 3;
    private static final int SCALE_WIDTH_FACTOR = 4;
    private static final int SCALE_HEIGHT_FACTOR = 5;
    private static final int SCALE_FACTOR = 6;
    private static final int EXPECTED_SCALED_WIDTH = WIDTH * SCALE_WIDTH_FACTOR;
    private static final int EXPECTED_SCALED_HEIGHT = HEIGHT * SCALE_HEIGHT_FACTOR;
    private static final int EXPECTED_SCALED_BOTH_WIDTH = WIDTH * SCALE_FACTOR;
    private static final int EXPECTED_SCALED_BOTH_HEIGHT = HEIGHT * SCALE_FACTOR;

    private BoundigBox box;

    /**
     * Tests the creation of a Position with given coordinates.
     */
    @BeforeEach
    void setUp() {
        box = new BoundigBox(WIDTH, HEIGHT);
    }

    /**
     * Tests the default constructor sets width and height to 0.
     */
    @Test
    void testDefaultConstructor() {
        final BoundigBox defaultBox = new BoundigBox();
        assertEquals(8, defaultBox.width());
        assertEquals(8, defaultBox.height());
    }

    /**
     * Tests the parameterized constructor.
     */
    @Test
    void testParameterizedConstructor() {
        final int paramWidth = 5;
        final int paramHeight = 10;
        final BoundigBox paramBox = new BoundigBox(paramWidth, paramHeight);
        assertEquals(paramWidth, paramBox.width());
        assertEquals(paramHeight, paramBox.height());
    }

    /**
     * Tests scaling the width.
     */
    @Test
    void testScaleWidth() {
        final BoundigBox scaled = box.scaleWidth(SCALE_WIDTH_FACTOR);
        assertEquals(EXPECTED_SCALED_WIDTH, scaled.width());
        assertEquals(HEIGHT, scaled.height());
    }

    /**
     * Tests scaling the height.
     */
    @Test
    void testScaleHeight() {
        final BoundigBox scaled = box.scaleHeight(SCALE_HEIGHT_FACTOR);
        assertEquals(WIDTH, scaled.width());
        assertEquals(EXPECTED_SCALED_HEIGHT, scaled.height());
    }

    /**
     * Tests scaling both width and height.
     */
    @Test
    void testScale() {
        final BoundigBox scaled = box.scale(SCALE_FACTOR);
        assertEquals(EXPECTED_SCALED_BOTH_WIDTH, scaled.width());
        assertEquals(EXPECTED_SCALED_BOTH_HEIGHT, scaled.height());
    }

    /**
     * Tests the copy method.
     */
    @Test
    void testCopy() {
        final int copyWidth = 7;
        final int copyHeight = 8;
        final BoundigBox copyBox = new BoundigBox(copyWidth, copyHeight);
        final BoundigBox copy = copyBox.copy();
        assertNotSame(copyBox, copy);
        assertEquals(copyBox.width(), copy.width());
        assertEquals(copyBox.height(), copy.height());
    }
}
