package it.unibo.coffebreak.model.entities.character;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.api.model.entities.character.states.CharacterState;
import it.unibo.coffebreak.api.model.entities.collectible.Collectible;
import it.unibo.coffebreak.api.model.entities.npc.Princess;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.model.entities.mario.states.normal.NormalState;
import it.unibo.coffebreak.impl.model.entities.npc.donkeykong.DonkeyKong;

/**
 * Test class for {@link Mario}, the main player character implementation.
 * Verifies behavior including movement, collisions, state transitions and game mechanics.
 * 
 * @see Mario
 * @author Grazia Bochdanovits de Kavna
 */
@ExtendWith(MockitoExtension.class)
class TestMario {

    private Mario mario;

    @Mock private Position mockPosition;
    @Mock private BoundigBox mockBoundingBox;
    @Mock private CharacterState mockState;
    @Mock private CharacterState mockNewState;
    @Mock private Platform mockPlatform;
    @Mock private Collectible mockCollectible;
    @Mock private Princess mockPrincess;
    @Mock private DonkeyKong mockDonkeyKong;

    /**
     * Initializes test environment before each test.
     * Creates a new Mario instance with mock position and bounding box.
     */
    @BeforeEach
    void setUp() {
        mario = new Mario(mockPosition, mockBoundingBox);
    }

    /**
     * Tests Mario's initial state after creation.
     */
    @Test
    void testInitialState() {
        assertTrue(mario.isFacingRight());
        assertFalse(mario.isJumping());
        assertFalse(mario.isClimbing());
        assertNotNull(mario.getCurrentState());
        assertInstanceOf(NormalState.class, mario.getCurrentState());
    }

    /**
     * Tests state transition functionality.
     * Verifies Mario correctly changes to a new state.
     */
    @Test
    void testChangeState() {
        final Supplier<CharacterState> supplier = () -> mockNewState;
        mario.changeState(supplier);
        assertEquals(mockNewState, mario.getCurrentState());
    }

    /**
     * Tests movement direction changes.
     */
    @Test
    void testMovementDirections() {
        mario.moveLeft();
        assertFalse(mario.isFacingRight());
        mario.moveRight();
        assertTrue(mario.isFacingRight());
    }

    /**
     * Tests jumping when standing on a platform.
     */
    @Test
    void testJumpWhenOnPlatform() {
        mario.onCollision(mockPlatform);
        assertTrue(mario.canStandOnPlatforms());
        mario.jump();
        assertTrue(mario.isJumping());
    }

    /**
     * Tests jumping when not on a platform.
     */
    @Test
    void testJumpWhenNotOnPlatform() {
        mario.onPlatformLeave();
        mario.jump();
        assertFalse(mario.isJumping());
    }

    /**
     * Tests collision with platform.
     */
    @Test
    void testOnCollisionWithPlatform() {
        mario.onCollision(mockPlatform);
        assertTrue(mario.canStandOnPlatforms());
        assertFalse(mario.isJumping());
        assertFalse(mario.isClimbing());
        verify(mockPlatform).destroy();
    }

    /**
     * Tests collision with collectible item.
     */
    @Test
    void testOnCollisionWithCollectible() {
        mario.onCollision(mockCollectible);
        verify(mockCollectible).collect(mario);
    }

    /**
     * Tests collision with princess.
     */
    @Test
    void testOnCollisionWithPrincess() {
        mario.onCollision(mockPrincess);
        verify(mockPrincess).rescue();
    }

    /**
     * Tests collision with Donkey Kong.
     */
    @Test
    void testOnCollisionWithDonkeyKong() {
        final int initialLives = mario.getLives();
        mario.onCollision(mockDonkeyKong);
        assertTrue(mario.getLives() < initialLives || mario.isGameOver());
    }

    /**
     * Tests points earning mechanism.
     */
    @Test
    void testEarnPoints() {
        final int initialScore = mario.getScoreValue();
        final int points = 100;
        mario.earnPoints(points);
        assertEquals(initialScore + points, mario.getScoreValue());
    }

    /**
     * Tests life losing mechanism.
     */
    @Test
    void testLoseLife() {
        final int initialLives = mario.getLives();
        mario.loseLife();
        assertEquals(initialLives - 1, mario.getLives());
    }

    /**
     * Tests game over condition.
     */
    @Test
    void testGameOver() {
        while (mario.getLives() > 0) {
            mario.loseLife();
        }
        assertTrue(mario.isGameOver());
    }

    /**
     * Tests lives reset functionality.
     */
    @Test
    void testResetLives() {
        final int initialLives = mario.getLives();
        mario.loseLife();
        mario.resetLives();
        assertEquals(initialLives, mario.getLives());
    }

    /**
     * Tests behavior reset functionality.
     */
    @Test
    void testResetBehaviour() {
        mario.moveLeft();
        mario.jump();
        mario.resetBehaviour();
        assertTrue(mario.isFacingRight());
        assertFalse(mario.isJumping());
    }

    /**
     * Tests climbing state behavior.
     */
    @Test
    void testClimbingState() {
        when(mockNewState.canClimb()).thenReturn(true);
        mario.changeState(() -> mockNewState);
        mario.moveUp();
        assertTrue(mario.isClimbing());
        mario.moveDown();
        assertTrue(mario.isClimbing());
    }

    /**
     * Tests gravity effect.
     */
    @Test
    void testGravityAffected() {
        assertTrue(mario.isAffectedByGravity());
        when(mockNewState.canClimb()).thenReturn(true);
        mario.changeState(() -> mockNewState);
        mario.moveUp();
        assertFalse(mario.isAffectedByGravity());
    }
}
