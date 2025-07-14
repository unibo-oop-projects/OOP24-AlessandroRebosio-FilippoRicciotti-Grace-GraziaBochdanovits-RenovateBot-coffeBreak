package it.unibo.coffebreak.model.states.ingame;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.api.controller.action.Action;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.api.model.entities.PhysicsEntity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.npc.Antagonist;
import it.unibo.coffebreak.api.model.physics.PhysicsEngine;
import it.unibo.coffebreak.impl.model.states.gameover.GameOverModelState;
import it.unibo.coffebreak.impl.model.states.ingame.InGameModelState;
import it.unibo.coffebreak.impl.model.states.pause.PauseModelState;

/**
 * Test class for {@link InGameModelState}.
 * <p>
 * Verifies in-game state behavior including entity updates, physics simulation,
 * player movement handling, and game over conditions.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
@ExtendWith(MockitoExtension.class)
class TestInGameModelState {

    private static final int VALUE = 500;

    @Mock
    private Model mockModel;

    @Mock
    private PhysicsEngine mockPhysicsEngine;

    @Mock
    private MainCharacter mockMainCharacter;

    @Mock
    private Antagonist mockAntagonist;

    @Mock
    private PhysicsEntity mockPhysicsEntity;

    @Mock
    private Entity mockEntity;

    private InGameModelState inGameState;

    @BeforeEach
    void setUp() {
        inGameState = new InGameModelState(mockPhysicsEngine);
    }

    /**
     * Tests game update loop including entity updates and physics processing.
     */
    @Test
    void testUpdateGameLogic() {
        final float deltaTime = 0.016f;
        final int initialLives = 3;
        final int updatedLives = 3;

        final List<Entity> entities = Arrays.asList(mockAntagonist, mockPhysicsEntity, mockEntity);

        when(mockModel.getMainCharacter()).thenReturn(Optional.of(mockMainCharacter));
        when(mockMainCharacter.getLives()).thenReturn(initialLives).thenReturn(updatedLives);
        when(mockModel.getEntities()).thenReturn(entities);
        when(mockModel.getBonusValue()).thenReturn(VALUE);
        when(mockMainCharacter.isGameOver()).thenReturn(false);

        when(mockAntagonist.tryThrowBarrel(deltaTime)).thenReturn(Optional.empty());

        inGameState.update(mockModel, deltaTime);

        verify(mockAntagonist).update(deltaTime);
        verify(mockPhysicsEntity).update(deltaTime);
        verify(mockEntity).update(deltaTime);

        verify(mockPhysicsEngine).updateEntity(eq(mockPhysicsEntity), eq(mockModel), eq(deltaTime));

        verify(mockModel).transformEntities();
        verify(mockModel).nextMap();
        verify(mockModel).calculateBonus(deltaTime);

        verify(mockModel, never()).setState(any(GameOverModelState.class));
    }

    /**
     * Tests that antagonist can throw barrels and they are added to the model.
     */
    @Test
    void testAntagonistBarrelThrowing() {
        final float deltaTime = 0.016f;
        final Barrel barrel = mock(Barrel.class);

        final List<Entity> entities = Arrays.asList(mockAntagonist);

        when(mockModel.getMainCharacter()).thenReturn(Optional.of(mockMainCharacter));
        when(mockMainCharacter.getLives()).thenReturn(3);
        when(mockModel.getEntities()).thenReturn(entities);
        when(mockModel.getBonusValue()).thenReturn(VALUE);
        when(mockMainCharacter.isGameOver()).thenReturn(false);
        when(mockAntagonist.tryThrowBarrel(deltaTime)).thenReturn(Optional.of(barrel));

        inGameState.update(mockModel, deltaTime);

        verify(mockModel).addEntity(barrel);
    }

    /**
     * Tests game over transition when player loses all lives.
     */
    @Test
    void testGameOverTransition() {
        final float deltaTime = 0.016f;

        when(mockModel.getMainCharacter()).thenReturn(Optional.of(mockMainCharacter));
        when(mockMainCharacter.getLives()).thenReturn(0);
        when(mockModel.getEntities()).thenReturn(Collections.emptyList());
        when(mockModel.getBonusValue()).thenReturn(VALUE);
        when(mockMainCharacter.isGameOver()).thenReturn(true);

        inGameState.update(mockModel, deltaTime);

        verify(mockModel).setState(any(GameOverModelState.class));
    }

    /**
     * Tests that entities are reset when player loses a life.
     */
    @Test
    void testEntityResetOnLifeLoss() {
        final float deltaTime = 0.016f;
        final int initialLives = 3;
        final int reducedLives = 2;

        when(mockModel.getMainCharacter()).thenReturn(Optional.of(mockMainCharacter));
        when(mockMainCharacter.getLives()).thenReturn(initialLives).thenReturn(reducedLives);
        when(mockModel.getEntities()).thenReturn(Collections.emptyList());
        when(mockMainCharacter.isGameOver()).thenReturn(false);

        inGameState.update(mockModel, deltaTime);

        verify(mockModel).initialEntitiesState();
    }

    /**
     * Tests that entities are reset when bonus reaches zero.
     */
    @Test
    void testEntityResetOnBonusZero() {
        final float deltaTime = 0.016f;

        when(mockModel.getMainCharacter()).thenReturn(Optional.of(mockMainCharacter));
        when(mockMainCharacter.getLives()).thenReturn(3);
        when(mockModel.getEntities()).thenReturn(Collections.emptyList());
        when(mockModel.getBonusValue()).thenReturn(0);
        when(mockMainCharacter.isGameOver()).thenReturn(false);

        inGameState.update(mockModel, deltaTime);

        verify(mockModel).initialEntitiesState();
    }

    /**
     * Tests that ESCAPE action pauses the game.
     */
    @Test
    void testEscapeActionPausesGame() {
        inGameState.handleAction(mockModel, Action.ESCAPE);

        verify(mockModel).setState(any(PauseModelState.class));
    }

    /**
     * Tests player movement actions are delegated to main character.
     */
    @Test
    void testPlayerMovementActions() {
        when(mockModel.getMainCharacter()).thenReturn(Optional.of(mockMainCharacter));

        inGameState.handleAction(mockModel, Action.LEFT);
        verify(mockMainCharacter).moveLeft();

        inGameState.handleAction(mockModel, Action.RIGHT);
        verify(mockMainCharacter).moveRight();

        inGameState.handleAction(mockModel, Action.UP);
        verify(mockMainCharacter).moveUp();

        inGameState.handleAction(mockModel, Action.DOWN);
        verify(mockMainCharacter).moveDown();

        inGameState.handleAction(mockModel, Action.SPACE);
        verify(mockMainCharacter).jump();
    }

    /**
     * Tests that movement actions are ignored when no main character is present.
     */
    @Test
    void testMovementActionsWithoutMainCharacter() {
        when(mockModel.getMainCharacter()).thenReturn(Optional.empty());

        inGameState.handleAction(mockModel, Action.LEFT);
        inGameState.handleAction(mockModel, Action.RIGHT);
        inGameState.handleAction(mockModel, Action.UP);
        inGameState.handleAction(mockModel, Action.DOWN);
        inGameState.handleAction(mockModel, Action.SPACE);
    }

    /**
     * Tests that unhandled actions don't cause side effects.
     */
    @Test
    void testUnhandledActions() {
        when(mockModel.getMainCharacter()).thenReturn(Optional.of(mockMainCharacter));

        inGameState.handleAction(mockModel, Action.ENTER);

        verify(mockModel, never()).setState(any(PauseModelState.class));
    }

    /**
     * Tests complex update scenario with multiple physics entities.
     */
    @Test
    void testComplexUpdateScenario() {
        final float deltaTime = 0.016f;
        final PhysicsEntity physicsEntity1 = mock(PhysicsEntity.class);
        final PhysicsEntity physicsEntity2 = mock(PhysicsEntity.class);
        final Entity normalEntity = mock(Entity.class);

        final List<Entity> entities = Arrays.asList(physicsEntity1, physicsEntity2, normalEntity);

        when(mockModel.getMainCharacter()).thenReturn(Optional.of(mockMainCharacter));
        when(mockMainCharacter.getLives()).thenReturn(3);
        when(mockModel.getEntities()).thenReturn(entities);
        when(mockModel.getBonusValue()).thenReturn(1000);
        when(mockMainCharacter.isGameOver()).thenReturn(false);

        inGameState.update(mockModel, deltaTime);

        verify(physicsEntity1).update(deltaTime);
        verify(physicsEntity2).update(deltaTime);
        verify(normalEntity).update(deltaTime);

        verify(mockPhysicsEngine).updateEntity(physicsEntity1, mockModel, deltaTime);
        verify(mockPhysicsEngine).updateEntity(physicsEntity2, mockModel, deltaTime);

        verify(mockModel).transformEntities();
        verify(mockModel).nextMap();
        verify(mockModel).calculateBonus(deltaTime);
    }
}
