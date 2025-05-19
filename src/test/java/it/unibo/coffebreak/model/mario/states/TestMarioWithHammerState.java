package it.unibo.coffebreak.model.mario.states;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.enemy.Enemy;
import it.unibo.coffebreak.model.impl.entities.mario.states.NormalState;
import it.unibo.coffebreak.model.impl.entities.mario.states.WithHammerState;

/**
 * Test class for {@link WithHammerState} implementation.
 * Verifies the behavior of Mario's state when equipped with a hammer.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
@ExtendWith(MockitoExtension.class)
class TestMarioWithHammerState {

    private static final float TEST_DELTA_TIME = 0.1f;

    @Mock private Character character;
    @Mock private Enemy enemy;
    @Mock private Entity otherEntity;

    private WithHammerState hammerState;

    @BeforeEach
    void setUp() {
        hammerState = spy(new WithHammerState());
    }

    /** 
     * Verifies state initialization on enter.
     */
    @Test
    void testOnEnterInitializesState() {
        hammerState.onEnter(character);
    }

    /**
     * Verifies no operation on state exit.
     */
    @Test
    void testOnExitDoesNothing() {
        hammerState.onExit(character);
        verifyNoInteractions(character);
    }

    /**
     * Tests state persistence when hammer active.
     */
    @Test
    void testUpdateWhenNotExpired() {
        doReturn(false).when(hammerState).isExpired();
        hammerState.update(character, TEST_DELTA_TIME);
        verify(character, never()).changeState(any());
    }

    /**
     * Tests automatic state transition when hammer expires.
     */
    @Test
    void testUpdateWhenExpired() {
        doReturn(true).when(hammerState).isExpired();

        hammerState.update(character, TEST_DELTA_TIME);
        verify(character).changeState(isA(NormalState.class));
    }

    /**
     * Verifies enemy destruction on collision.
     */
    @Test
    void testHandleCollisionWithEnemy() {
        hammerState.handleCollision(character, enemy);
        verify(enemy).destroy();
        verifyNoInteractions(character);
    }

    /**
     * Verifies non action on other entity collision.
     */
    @Test
    void testHandleCollisionWithOtherEntity() {
        hammerState.handleCollision(character, otherEntity);
        verifyNoInteractions(enemy, character);
    }

    /**
     * Verifies climbing disabled in hammer state.
     */
    @Test
    void testCanClimb() {
        assertFalse(hammerState.canClimb());
    }
}
