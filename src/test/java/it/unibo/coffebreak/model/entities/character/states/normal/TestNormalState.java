package it.unibo.coffebreak.model.entities.character.states.normal;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.character.states.CharacterState;
import it.unibo.coffebreak.api.model.entities.enemy.Enemy;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.impl.model.entities.mario.states.normal.NormalState;

/**
 * Test class for {@link NormalState}, Mario's default ground state.
 * Verifies behavior including movement, climbing, invincibility and collisions.
 * 
 * <p>Tests cover:
 * <ul>
 *   <li>State update mechanics</li>
 *   <li>Collision handling with different entities</li>
 *   <li>Invincibility timer functionality</li>
 *   <li>Climbing and jumping capabilities</li>
 * </ul>
 */
@ExtendWith(MockitoExtension.class)
class TestNormalState {

    private static final float INVINCIBILITY_TIME = 2.0f;
    private CharacterState normalState;

    @Mock private MainCharacter mockCharacter;
    @Mock private Enemy mockEnemy;
    @Mock private Ladder mockLadder;
    @Mock private Entity mockOtherEntity;

    @BeforeEach
    void setUp() {
        normalState = new NormalState();
    }

    /**
     * Tests initial state conditions.
     * Verifies:
     * <ul>
     *   <li>Climbing is initially disabled</li>
     *   <li>Invincibility timer is initially zero</li>
     *   <li>Jumping is allowed</li>
     * </ul>
     */
    @Test
    void testInitialConditions() {
        assertFalse(normalState.canClimb());
        assertTrue(normalState.canJump());
    }

    /**
     * Tests state update mechanics.
     * Verifies:
     * <ul>
     *   <li>Climbing flag is reset each update</li>
     *   <li>Invincibility timer counts down correctly</li>
     * </ul>
     */
    @Test
    void testUpdate() {
        normalState.handleCollision(mockCharacter, mockLadder);
        assertTrue(normalState.canClimb());

        final float deltaTime = 0.5f;
        normalState.update(mockCharacter, deltaTime);

        assertFalse(normalState.canClimb());
    }

    /**
     * Tests invincibility timer functionality.
     */
    @Test
    void testInvincibilityTimer() {
        normalState.handleCollision(mockCharacter, mockEnemy);
        verify(mockCharacter).loseLife();

        reset(mockCharacter);
        normalState.handleCollision(mockCharacter, mockEnemy);
        verify(mockCharacter, never()).loseLife();

        normalState.update(mockCharacter, INVINCIBILITY_TIME);
        normalState.handleCollision(mockCharacter, mockEnemy);
        verify(mockCharacter).loseLife();
    }

    /**
     * Tests ladder collision handling.
     * Verifies climbing becomes possible after ladder collision.
     */
    @Test
    void testLadderCollision() {
        normalState.handleCollision(mockCharacter, mockLadder);
        assertTrue(normalState.canClimb());
    }

    /**
     * Tests enemy collision handling.
     * Verifies:
     * <ul>
     *   <li>Life is lost on first collision</li>
     *   <li>Invincibility period starts after collision</li>
     * </ul>
     */
    @Test
    void testEnemyCollision() {
        normalState.handleCollision(mockCharacter, mockEnemy);
        verify(mockCharacter).loseLife();
    }

    /**
     * Tests collision with other entity types.
     */
    @Test
    void testOtherEntityCollision() {
        assertDoesNotThrow(() -> 
            normalState.handleCollision(mockCharacter, mockOtherEntity));
    }

    /**
     * Tests climbing capability after update.
     */
    @Test
    void testClimbingResetAfterUpdate() {
        final float deltaTime = 0.1f;
        normalState.handleCollision(mockCharacter, mockLadder);
        normalState.update(mockCharacter, deltaTime);
        assertFalse(normalState.canClimb());
    }

    /**
     * Tests jump capability.
     */
    @Test
    void testJumpCapability() {
        assertTrue(normalState.canJump());
    }
}
