package it.unibo.coffebreak.model.mario.states;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.model.impl.entities.mario.states.NormalState;

/**
 * Test class for {@link NormalState} implementation.
 * Verifies the behavior of Mario's default state where he can move freely on ground.
 * 
 *@author Grazia Bochdanovits de Kavna
 */
@ExtendWith(MockitoExtension.class)
class TestMarioNormalState {

    private static final float TEST_DELTA_TIME = 0.1f;
    @Mock private Character character;
    @Mock private Barrel barrel;
    @Mock private Entity otherEntity;

    private NormalState normalState;

    /**
     * Initializes the test environment before each test.
     * Creates a fresh instance of NormalState for each test case.
     */
    @BeforeEach
    void setUp() {
        normalState = new NormalState();
    }

    /**
     * Tests that the onEnter method performs no operations.
     */
    @Test
    void testOnEnterDoesNothing() {
        assertDoesNotThrow(() -> normalState.onEnter(character));
        verifyNoInteractions(character);
    }

    /**
     * Tests that the onExit method performs no operations.
     */
    @Test
    void testOnExitDoesNothing() {
        assertDoesNotThrow(() -> normalState.onExit(character));
        verifyNoInteractions(character);
    }

    /**
     * Tests that the update method performs no operations.
     */
    @Test
    void testUpdateDoesNothing() {
        assertDoesNotThrow(() -> normalState.update(character, TEST_DELTA_TIME));
        verifyNoInteractions(character);
    }

    /**
     * Tests collision handling with Barrel entities.
     * Verifies that:
     * <ul>
     *   <li>Character loses a life when colliding with a barrel</li>
     * </ul>
     */
    @Test
    void testHandleCollisionWithBarrel() {
        normalState.handleCollision(character, barrel);
        verify(character).loseLife();
    }

    /**
     * Tests collision handling with other entities.
     */
    @Test
    void testHandleCollisionWithOtherEntity() {
        normalState.handleCollision(character, otherEntity);
        verifyNoInteractions(character);
    }

    /**
     * Tests climbing capability in normal state.
     */
    @Test
    void testCanClimb() {
        assertTrue(normalState.canClimb());
    }
}
