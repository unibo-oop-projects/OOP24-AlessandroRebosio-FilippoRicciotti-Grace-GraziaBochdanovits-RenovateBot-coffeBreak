package it.unibo.coffebreak.model.entity.ladder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.api.entity.ladder.Climbable;
import it.unibo.coffebreak.model.impl.entity.ladder.BasicLadder;
import it.unibo.coffebreak.model.impl.entity.ladder.BrokenLadder;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;

/**
 * Test class for {@link BrokenLadder}, verifying that it makes a ladder non-climbable
 * while preserving other properties.
 */
class TestBrokenLadder {

    private static final Position TEST_POSITION = new Position(0, 0);
    private static final Dimension TEST_DIMENSION =  new Dimension(3, 10);
    private static final float DEFAULT_SPEED = 2.0f;

    @Test
    void testBrokenLadderCannotBeClimbed() {
        final Climbable baseLadder = new BasicLadder(TEST_POSITION, TEST_DIMENSION);
        final Climbable brokenLadder = new BrokenLadder(baseLadder);

        assertFalse(brokenLadder.canClimb()); 
        assertEquals(DEFAULT_SPEED, brokenLadder.getClimbSpeed());
    }
}
