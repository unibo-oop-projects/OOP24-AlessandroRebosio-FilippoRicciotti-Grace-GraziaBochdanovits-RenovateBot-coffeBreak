package it.unibo.coffebreak.model.entity.ladder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.api.entity.ladder.Climbable;
import it.unibo.coffebreak.model.impl.entity.ladder.BasicLadder;
import it.unibo.coffebreak.model.impl.entity.ladder.LadderDecorator;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;

/**
 * Test class for {@link LadderDecorator}, verifying that it delegates all operations
 * to the wrapped {@link Climbable} instance by default.
 */
class TestLadderDecorator {

    private static final Position TEST_POSITION = new Position(0, 0);
    private static final Dimension TEST_DIMENSION =  new Dimension(3, 10);

    @Test
    void testDecoratorDelegatesToBaseLadder() {
        final Climbable baseLadder = new BasicLadder(TEST_POSITION, TEST_DIMENSION);
        final Climbable decorator = new LadderDecorator(baseLadder) { }; 

        assertEquals(baseLadder.canClimb(), decorator.canClimb());
        assertEquals(baseLadder.getClimbSpeed(), decorator.getClimbSpeed());
    }
}
