package it.unibo.coffebreak.model.entities.structure.ladder.normal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.enemy.fire.GameFire;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.model.entities.structure.ladder.normal.NormalLadder;

/**
 * Test class for {@link NormalLadder} implementation.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
class TestNormalLadder {

    // Position constants
    private static final int LADDER_X = 10;
    private static final int LADDER_Y = 20;
    private static final int MARIO_INITIAL_X = 9;
    private static final int MARIO_INITIAL_Y = 18;
    private static final int FIRE_INITIAL_X = 9;
    private static final int FIRE_INITIAL_Y = 19;
    private static final float POSITION_OFFSET = 0.5f;
    private static final float FIRE_POSITION_OFFSET = 0.8f;

    // Dimension constants
    private static final int LADDER_WIDTH = 1;
    private static final int LADDER_HEIGHT = 5;
    private static final int MARIO_WIDTH = 2;
    private static final int MARIO_HEIGHT = 3;
    private static final int FIRE_WIDTH = 1;
    private static final int FIRE_HEIGHT = 1;

    // Test objects
    private static final Position TEST_POSITION = new Position(LADDER_X, LADDER_Y);
    private static final BoundigBox TEST_DIMENSION = new BoundigBox(LADDER_WIDTH, LADDER_HEIGHT);
    private static final float LADDER_CENTER_X = LADDER_X + (LADDER_WIDTH / 2f);
    private static final float DELTA_TIME = 0.001f;

    private NormalLadder ladder;

    /**
     * Initializes test environment before each test.
     */
    @BeforeEach
    void setUp() {
        ladder = new NormalLadder(TEST_POSITION, TEST_DIMENSION);
    }

    /**
     * Tests ladder initialization with valid parameters.
     */
    @Test
    void testInitialization() {
        assertEquals(TEST_POSITION, ladder.getPosition());
        assertEquals(TEST_DIMENSION, ladder.getDimension());
    }

    /**
     * Tests collision with a climbing character.
     * Verifies character gets centered on ladder.
     */
    @Test
    void testCollisionWithClimbingCharacter() {
        final Mario mario = new Mario(
            new Position(MARIO_INITIAL_X + POSITION_OFFSET, MARIO_INITIAL_Y),
            new BoundigBox(MARIO_WIDTH, MARIO_HEIGHT)
        );
        mario.moveUp();

        ladder.onCollision(mario);

        final float expectedX = LADDER_CENTER_X - (MARIO_WIDTH / 2f);
        assertEquals(expectedX, mario.getPosition().x(), DELTA_TIME);
        assertEquals(MARIO_INITIAL_Y, mario.getPosition().y(), DELTA_TIME);
    }

    /**
     * Tests collision with non-climbing character.
     * Verifies position remains unchanged.
     */
    @Test
    void testCollisionWithNonClimbingCharacter() {
        final Mario mario = new Mario(
            new Position(MARIO_INITIAL_X + POSITION_OFFSET, MARIO_INITIAL_Y),
            new BoundigBox(MARIO_WIDTH, MARIO_HEIGHT)
        );

        ladder.onCollision(mario);

        assertEquals(MARIO_INITIAL_X + POSITION_OFFSET, mario.getPosition().x(), DELTA_TIME);
        assertEquals(MARIO_INITIAL_Y, mario.getPosition().y(), DELTA_TIME);
    }

    /**
     * Tests collision with fire entity.
     * Verifies fire gets centered on ladder.
     */
    @Test
    void testCollisionWithFire() {
        final GameFire fire = new GameFire(
            new Position(FIRE_INITIAL_X + FIRE_POSITION_OFFSET, FIRE_INITIAL_Y),
            new BoundigBox(FIRE_WIDTH, FIRE_HEIGHT)
        );

        ladder.onCollision(fire);

        final float expectedX = LADDER_CENTER_X - (FIRE_WIDTH / 2f);
        assertEquals(expectedX, fire.getPosition().x(), DELTA_TIME);
        assertEquals(FIRE_INITIAL_Y, fire.getPosition().y(), DELTA_TIME);
    }
}
