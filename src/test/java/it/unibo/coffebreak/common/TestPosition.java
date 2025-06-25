package it.unibo.coffebreak.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.common.Vector;
import it.unibo.coffebreak.impl.common.BoundigBox;

/**
 * Unit tests for the {@link Position} record.
 * 
 * @author Alessandro Rebosio
 */
class TestPosition {

    private static final float INIT_X = 1.5f;
    private static final float INIT_Y = -2.0f;
    private static final float SUM_X = 2.0f;
    private static final float SUM_Y = 3.0f;
    private static final float VEC_X = 1.0f;
    private static final float VEC_Y = -1.5f;
    private static final float EXPECTED_SUM_X = SUM_X + VEC_X;
    private static final float EXPECTED_SUM_Y = SUM_Y + VEC_Y;
    private static final float SCALE_POS_X = 2.0f;
    private static final float SCALE_POS_Y = 3.0f;
    private static final int BOX_WIDTH = 4;
    private static final int BOX_HEIGHT = 5;
    private static final float EXPECTED_SCALED_X = SCALE_POS_X * BOX_WIDTH;
    private static final float EXPECTED_SCALED_Y = SCALE_POS_Y * BOX_HEIGHT;
    private static final float COPY_X = 7.5f;
    private static final float COPY_Y = -3.2f;

    /**
     * Tests the creation of a Position with given coordinates.
     */
    @Test
    void testConstructor() {
        final Position pos = new Position(INIT_X, INIT_Y);
        assertEquals(INIT_X, pos.x());
        assertEquals(INIT_Y, pos.y());
    }

    /**
     * Tests the sum method with a valid vector.
     */
    @Test
    void testSum() {
        final Position pos = new Position(SUM_X, SUM_Y);
        final Vector vec = new Vector(VEC_X, VEC_Y);
        final Position result = pos.sum(vec);
        assertEquals(EXPECTED_SUM_X, result.x());
        assertEquals(EXPECTED_SUM_Y, result.y());
    }

    /**
     * Tests the scalePosition method with a valid BoundigBox.
     */
    @Test
    void testScalePosition() {
        final Position pos = new Position(SCALE_POS_X, SCALE_POS_Y);
        final BoundigBox box = new BoundigBox(BOX_WIDTH, BOX_HEIGHT);
        final Position scaled = pos.scalePosition(box);
        assertEquals(EXPECTED_SCALED_X, scaled.x());
        assertEquals(EXPECTED_SCALED_Y, scaled.y());
    }

    /**
     * Tests the copy method returns a new instance with the same values.
     */
    @Test
    void testCopy() {
        final Position pos = new Position(COPY_X, COPY_Y);
        final Position copy = pos.copy();
        assertNotSame(pos, copy);
        assertEquals(pos.x(), copy.x());
        assertEquals(pos.y(), copy.y());
    }
}
