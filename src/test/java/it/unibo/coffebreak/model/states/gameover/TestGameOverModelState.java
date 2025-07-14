package it.unibo.coffebreak.model.states.gameover;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.api.controller.action.Action;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.impl.model.states.gameover.GameOverModelState;
import it.unibo.coffebreak.impl.model.states.menu.MenuModelState;

/**
 * Test class for {@link GameOverModelState}.
 * <p>
 * Verifies game over state behavior including transition back to menu
 * and proper cleanup when exiting the state.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
@ExtendWith(MockitoExtension.class)
class TestGameOverModelState {

    @Mock
    private Model mockModel;

    private GameOverModelState gameOverState;

    @BeforeEach
    void setUp() {
        gameOverState = new GameOverModelState();
    }

    /**
     * Tests that pressing ENTER returns to the main menu.
     */
    @Test
    void testReturnToMenuOnEnter() {
        gameOverState.handleAction(mockModel, Action.ENTER);

        verify(mockModel).setState(any(MenuModelState.class));
    }

    /**
     * Tests that unhandled actions don't cause any side effects.
     */
    @Test
    void testUnhandledActions() {
        gameOverState.handleAction(mockModel, Action.UP);
        gameOverState.handleAction(mockModel, Action.DOWN);
        gameOverState.handleAction(mockModel, Action.LEFT);
        gameOverState.handleAction(mockModel, Action.RIGHT);
        gameOverState.handleAction(mockModel, Action.SPACE);
        gameOverState.handleAction(mockModel, Action.ESCAPE);
    }

    /**
     * Tests that exiting the game over state adds an entry with empty name.
     */
    @Test
    void testOnExitAddsEntry() {
        gameOverState.onExit(mockModel);

        verify(mockModel).addEntry("");
    }
}
