package it.unibo.coffebreak.model.entity.ladder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.api.entity.ladder.Climbable;
import it.unibo.coffebreak.model.impl.entity.ladder.BasicLadder;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;

/**
 * Test class for {@link BasicLadder}, verifying its default behavior.
 * <p>
 * Ensures that a basic ladder is always climbable and has the expected default speed.
 * </p>
 */
class TestBasicLadder {

    private static final Position TEST_POSITION = new Position(0, 0);
    private static final Dimension TEST_DIMENSION =  new Dimension(3, 10);
    private static final float DEFAULT_SPEED = 2.0f;

    @Test
    void testBasicLadderIsAlwaysClimbable() {
        final Climbable ladder = new BasicLadder(TEST_POSITION, TEST_DIMENSION);
        assertTrue(ladder.canClimb());
    }

    @Test
    void testDefaultClimbSpeed() {
        final Climbable ladder = new BasicLadder(TEST_POSITION, TEST_DIMENSION);
        assertEquals(DEFAULT_SPEED, ladder.getClimbSpeed());
    }
}
