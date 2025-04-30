package it.unibo.coffebreak.model.entity.ladder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.api.entity.ladder.Climbable;
import it.unibo.coffebreak.model.impl.entity.ladder.BasicLadder;
import it.unibo.coffebreak.model.impl.entity.ladder.FastLadder;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;

/**
 * Test class for {@link FastLadder}, verifying that it overrides the climb speed
 * while maintaining climbability.
 */
class TestFastLadder {

    private static final Position TEST_POSITION = new Position(0, 0);
    private static final Dimension TEST_DIMENSION =  new Dimension(3, 10);
    private static final float EXPECTED_SPEED = 4.0f;

    @Test
    void testFastLadderOverridesSpeed() {
        final Climbable baseLadder = new BasicLadder(TEST_POSITION, TEST_DIMENSION);
        final Climbable fastLadder = new FastLadder(baseLadder);

        assertEquals(EXPECTED_SPEED, fastLadder.getClimbSpeed()); 
        assertTrue(fastLadder.canClimb()); 
    }
}
