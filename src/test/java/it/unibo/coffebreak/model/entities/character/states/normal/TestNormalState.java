package it.unibo.coffebreak.model.entities.character.states.normal;



import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.enemy.Enemy;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.mario.states.normal.NormalState;

/**
 * Tests for {@link NormalState} behavior.
 */
@ExtendWith(MockitoExtension.class)
class TestNormalState {

    private static final float LADDER_X_POSITION = 0.45f;
    private NormalState state;
    @Mock private MainCharacter mockCharacter;
    @Mock private Enemy mockEnemy;
    @Mock private Ladder mockLadder;

    @BeforeEach
    void setUp() {
        state = new NormalState();
    }

    @Test
    void testEnemyCollision() {
        state.handleCollision(mockCharacter, mockEnemy);
        verify(mockCharacter).loseLife();
    }

    @Test
    void testLadderCollision() {
        when(mockCharacter.getDimension()).thenReturn(new BoundigBox(1, 1));
        when(mockLadder.getPosition()).thenReturn(new Position(LADDER_X_POSITION, 0));
        when(mockLadder.getDimension()).thenReturn(new BoundigBox(0, 1));
        when(mockCharacter.getPosition()).thenReturn(new Position(0, 0));

        state.handleCollision(mockCharacter, mockLadder);
        assertTrue(state.canClimb());
    }

    @Test
    void testClimbingMechanics() {
        state.startClimb();
        assertAll(
            () -> assertTrue(state.isClimbing()),
            () -> assertTrue(state.canClimb())
        );

        state.stopClimb();
        assertAll(
            () -> assertFalse(state.isClimbing()),
            () -> assertFalse(state.canClimb())
        );
    }
}
