package it.unibo.coffebreak.model.entities.character;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.entities.collectible.Collectible;
import it.unibo.coffebreak.api.model.entities.npc.Princess;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.common.Vector;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.model.entities.mario.states.AbstractMarioState;

/**
 * Test class for {@link Mario} entity implementation.
 * Verifies core character behavior including movement, collisions, state transitions,
 * and life management.
 * 
 * <p>Key test areas:
 * <ul>
 *   <li>Initial state conditions</li>
 *   <li>Movement and direction changes</li> 
 *   <li>State transitions and delegation</li>
 *   <li>Collision handling with different entity types</li>
 *   <li>Life management and game over conditions</li>
 *   <li>Update cycle behavior</li>
 * </ul>
 * 
 * <p>Uses Mockito to isolate dependencies for focused unit testing.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
@ExtendWith(MockitoExtension.class)
class TestMario {

    private static final float DELTATIME = 0.016f;
    private static final Position INITIAL_POSITION = new Position(0, 0);
    private static final BoundigBox DIMENSION = new BoundigBox(1, 1);
    private static final Position PLATFORM_POSITION = new Position(0, 0.90f);

    private Mario mario;

    @Mock private AbstractMarioState mockState;
    @Mock private Collectible mockCollectible;
    @Mock private Princess mockPrincess;
    @Mock private Platform mockPlatform;
    @Mock private Tank mockTank;

    @BeforeEach
    void setUp() {
        mario = new Mario(INITIAL_POSITION, DIMENSION);
        mario.changeState(() -> mockState);
    }

    /**
     * Tests initial character setup.
     * - Starting position and dimensions
     * - Default state values (facing right, not jumping)
     * - Initial velocity and game state
     */
    @Test
    void testInitialConditions() {
        assertAll(
            () -> assertEquals(INITIAL_POSITION, mario.getPosition()),
            () -> assertEquals(DIMENSION, mario.getDimension()),
            () -> assertEquals(mockState, mario.getCurrentState()),
            () -> assertFalse(mario.isGameOver()),
            () -> assertTrue(mario.isFacingRight()),
            () -> assertFalse(mario.isJumping()),
            () -> assertEquals(new Vector(0, 0), mario.getVelocity())
        );
    }

    /**
     * Verifies direction change mechanics.
     */
    @Test
    void testMovementDirectionChange() {
        mario.setDirection(Command.MOVE_RIGHT);
        mario.update(DELTATIME);
        assertTrue(mario.isFacingRight());

        mario.setDirection(Command.MOVE_LEFT);
        mario.update(DELTATIME);
        assertFalse(mario.isFacingRight());
    }

    /**
     * Tests state transition protocol.
     * - Verifies onExit() called on previous state
     * - Verifies onEnter() called on new state 
     * - Confirms current state is updated
     */
    @Test
    void testStateTransition() {
        final AbstractMarioState newState = mock(AbstractMarioState.class);
        mario.changeState(() -> newState);

        verify(mockState).onExit(mario);
        verify(newState).onEnter(mario);
        assertEquals(newState, mario.getCurrentState());
    }

    /**
     * Tests collectible item pickup.
     */
    @Test
    void testCollisionWithCollectible() {
        mario.onCollision(mockCollectible);
        verify(mockCollectible).collect(mario);
    }

    /**
     * Tests princess rescue interaction.
     */
    @Test
    void testCollisionWithPrincess() {
        mario.onCollision(mockPrincess);
        verify(mockPrincess).rescue();
    }

    /**
     * Tests platform collision handling.
     */
   @Test
    void testCollisionWithPlatform() {
        when(mockPlatform.getPosition()).thenReturn(PLATFORM_POSITION);
        when(mockPlatform.getDimension()).thenReturn(new BoundigBox(1, 1));
        when(mockPlatform.canPassThrough()).thenReturn(false);

        assertTrue(mario.collidesWith(mockPlatform));

        mario.onCollision(mockPlatform);
        verify(mockState).handleCollision(mario, mockPlatform);
    }

    /**
     * Tests tank collision effects.
     */
    @Test
    void testCollisionWithTank() {
        mario.setDirection(Command.MOVE_RIGHT);
        mario.onCollision(mockTank);
        assertEquals(new Vector(0, 0), mario.getVelocity());
    }
}
