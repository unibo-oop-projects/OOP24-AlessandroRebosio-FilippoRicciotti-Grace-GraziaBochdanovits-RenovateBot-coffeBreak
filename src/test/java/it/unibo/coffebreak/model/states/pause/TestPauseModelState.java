package it.unibo.coffebreak.model.states.pause;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.unibo.coffebreak.api.common.Option;
import it.unibo.coffebreak.api.controller.action.Action;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.impl.model.states.ingame.InGameModelState;
import it.unibo.coffebreak.impl.model.states.menu.MenuModelState;
import it.unibo.coffebreak.impl.model.states.pause.PauseModelState;

/**
 * Test class for {@link PauseModelState}.
 * <p>
 * Verifies pause menu state behavior including option initialization,
 * navigation,
 * and state transitions when pause menu options are selected.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
@ExtendWith(MockitoExtension.class)
class TestPauseModelState {

    @Mock
    private Model mockModel;

    private PauseModelState pauseState;

    @BeforeEach
    void setUp() {
        pauseState = new PauseModelState();
    }

    /**
     * Tests that the pause state is initialized with the correct options.
     */
    @Test
    void testPauseOptionsInitialization() {
        assertEquals(3, pauseState.options().size());
        assertTrue(pauseState.options().contains(Option.RESUME));
        assertTrue(pauseState.options().contains(Option.MENU));
        assertTrue(pauseState.options().contains(Option.QUIT));
        assertEquals(Option.RESUME, pauseState.getSelectedOption());
    }

    /**
     * Tests navigation through pause menu options using UP and DOWN actions.
     */
    @Test
    void testPauseMenuNavigation() {
        assertEquals(Option.RESUME, pauseState.getSelectedOption());

        pauseState.handleAction(mockModel, Action.DOWN);
        assertEquals(Option.MENU, pauseState.getSelectedOption());

        pauseState.handleAction(mockModel, Action.DOWN);
        assertEquals(Option.QUIT, pauseState.getSelectedOption());

        pauseState.handleAction(mockModel, Action.DOWN);
        assertEquals(Option.QUIT, pauseState.getSelectedOption());

        pauseState.handleAction(mockModel, Action.UP);
        assertEquals(Option.MENU, pauseState.getSelectedOption());

        pauseState.handleAction(mockModel, Action.UP);
        assertEquals(Option.RESUME, pauseState.getSelectedOption());

        pauseState.handleAction(mockModel, Action.UP);
        assertEquals(Option.RESUME, pauseState.getSelectedOption());
    }

    /**
     * Tests that selecting RESUME option transitions back to in-game state.
     */
    @Test
    void testResumeGameAction() {
        assertEquals(Option.RESUME, pauseState.getSelectedOption());

        pauseState.handleAction(mockModel, Action.ENTER);

        verify(mockModel).setState(any(InGameModelState.class));
    }

    /**
     * Tests that selecting MENU option transitions to main menu.
     */
    @Test
    void testReturnToMenuAction() {
        pauseState.handleAction(mockModel, Action.DOWN);
        assertEquals(Option.MENU, pauseState.getSelectedOption());

        pauseState.handleAction(mockModel, Action.ENTER);

        verify(mockModel).setState(any(MenuModelState.class));
    }

    /**
     * Tests that selecting QUIT option stops the game.
     */
    @Test
    void testQuitGameAction() {
        pauseState.handleAction(mockModel, Action.DOWN);
        pauseState.handleAction(mockModel, Action.DOWN);
        assertEquals(Option.QUIT, pauseState.getSelectedOption());

        pauseState.handleAction(mockModel, Action.ENTER);

        verify(mockModel).stop();
    }

    /**
     * Tests that unhandled actions don't cause any side effects.
     */
    @Test
    void testUnhandledActions() {
        final Option initialOption = pauseState.getSelectedOption();

        pauseState.handleAction(mockModel, Action.LEFT);
        pauseState.handleAction(mockModel, Action.RIGHT);
        pauseState.handleAction(mockModel, Action.SPACE);
        pauseState.handleAction(mockModel, Action.ESCAPE);

        assertEquals(initialOption, pauseState.getSelectedOption());
    }

    /**
     * Tests that exiting the pause state adds an entry with empty name.
     */
    @Test
    void testOnExitAddsEntry() {
        pauseState.onExit(mockModel);

        verify(mockModel).addEntry("");
    }
}
