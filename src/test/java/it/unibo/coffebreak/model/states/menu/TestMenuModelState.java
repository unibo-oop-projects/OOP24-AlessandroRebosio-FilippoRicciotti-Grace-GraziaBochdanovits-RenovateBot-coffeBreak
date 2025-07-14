package it.unibo.coffebreak.model.states.menu;

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

/**
 * Test class for {@link MenuModelState}.
 * <p>
 * Verifies menu state behavior including option initialization, navigation,
 * and state transitions when menu options are selected.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
@ExtendWith(MockitoExtension.class)
class TestMenuModelState {

    @Mock
    private Model mockModel;

    private MenuModelState menuState;

    @BeforeEach
    void setUp() {
        menuState = new MenuModelState();
    }

    /**
     * Tests that the menu state is initialized with the correct options.
     */
    @Test
    void testMenuOptionsInitialization() {
        assertEquals(2, menuState.options().size());
        assertTrue(menuState.options().contains(Option.START));
        assertTrue(menuState.options().contains(Option.QUIT));
        assertEquals(Option.START, menuState.getSelectedOption());
    }

    /**
     * Tests navigation through menu options using UP and DOWN actions.
     */
    @Test
    void testMenuNavigation() {
        assertEquals(Option.START, menuState.getSelectedOption());

        menuState.handleAction(mockModel, Action.DOWN);
        assertEquals(Option.QUIT, menuState.getSelectedOption());

        menuState.handleAction(mockModel, Action.UP);
        assertEquals(Option.START, menuState.getSelectedOption());

        menuState.handleAction(mockModel, Action.UP);
        assertEquals(Option.START, menuState.getSelectedOption());

        menuState.handleAction(mockModel, Action.DOWN);
        assertEquals(Option.QUIT, menuState.getSelectedOption());

        menuState.handleAction(mockModel, Action.DOWN);
        assertEquals(Option.QUIT, menuState.getSelectedOption());
    }

    /**
     * Tests that selecting START option starts the game and transitions to in-game
     * state.
     */
    @Test
    void testStartGameAction() {
        assertEquals(Option.START, menuState.getSelectedOption());

        menuState.handleAction(mockModel, Action.ENTER);

        verify(mockModel).start();
        verify(mockModel).setState(any(InGameModelState.class));
    }

    /**
     * Tests that selecting QUIT option stops the game.
     */
    @Test
    void testQuitGameAction() {
        menuState.handleAction(mockModel, Action.DOWN);
        assertEquals(Option.QUIT, menuState.getSelectedOption());

        menuState.handleAction(mockModel, Action.ENTER);

        verify(mockModel).stop();
    }

    /**
     * Tests that unhandled actions don't cause any side effects.
     */
    @Test
    void testUnhandledActions() {
        final Option initialOption = menuState.getSelectedOption();

        menuState.handleAction(mockModel, Action.LEFT);
        menuState.handleAction(mockModel, Action.RIGHT);
        menuState.handleAction(mockModel, Action.SPACE);
        menuState.handleAction(mockModel, Action.ESCAPE);

        assertEquals(initialOption, menuState.getSelectedOption());
    }
}
