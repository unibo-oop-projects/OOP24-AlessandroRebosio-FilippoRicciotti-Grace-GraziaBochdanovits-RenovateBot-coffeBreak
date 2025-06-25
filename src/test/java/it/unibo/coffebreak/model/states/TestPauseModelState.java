package it.unibo.coffebreak.model.states;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.common.Option;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.impl.model.states.pause.PauseModelState;
import it.unibo.coffebreak.impl.model.GameModel;
import it.unibo.coffebreak.impl.model.states.ingame.InGameModelState;
import it.unibo.coffebreak.impl.model.states.menu.MenuModelState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

/**
 * Unit tests for {@link PauseModelState}.
 * <p>
 * These tests verify correct option selection, navigation, and state
 * transitions in the pause menu state.
 * </p>
 *
 * <ul>
 * <li>Verifies initial selected option is RESUME.</li>
 * <li>Checks navigation with MOVE_UP and MOVE_DOWN, including wrapping.</li>
 * <li>Ensures ENTER command triggers correct state transitions (resume,
 * menu).</li>
 * <li>Validates that all expected options are present.</li>
 * </ul>
 *
 * @author Alessandro Rebosio
 */
class TestPauseModelState {

    private PauseModelState state;
    private Model dummyModel;

    /**
     * Initializes the pause state and dummy model before each test.
     */
    @BeforeEach
    void setUp() {
        state = new PauseModelState();
        dummyModel = new GameModel();
        dummyModel.setState(new InGameModelState());
    }

    /**
     * Tests that the initial selected option is RESUME.
     */
    @Test
    void testInitialSelectedOption() {
        assertEquals(Option.RESUME, state.getSelectedOption());
    }

    /**
     * Tests that MOVE_DOWN selects the next option (MENU).
     */
    @Test
    void testMoveDownSelectsNextOption() {
        state.handleCommand(dummyModel, Command.MOVE_DOWN);
        assertEquals(Option.MENU, state.getSelectedOption());
    }

    /**
     * Tests that MOVE_UP wraps selection to the last option (QUIT).
     */
    @Test
    void testMoveUpWrapsToLastOption() {
        state.handleCommand(dummyModel, Command.MOVE_UP);
        assertEquals(Option.QUIT, state.getSelectedOption());
    }

    /**
     * Tests that repeated MOVE_DOWN wraps selection back to the first option
     * (RESUME).
     */
    @Test
    void testMoveDownWrapsToFirstOption() {
        state.handleCommand(dummyModel, Command.MOVE_DOWN);
        state.handleCommand(dummyModel, Command.MOVE_DOWN);
        state.handleCommand(dummyModel, Command.MOVE_DOWN);
        assertEquals(Option.RESUME, state.getSelectedOption());
    }

    /**
     * Tests that getOptions returns all expected options (RESUME, MENU, QUIT).
     */
    @Test
    void testGetOptionsReturnsAllOptions() {
        final List<Option> options = state.getOptions();
        assertEquals(3, options.size());
        assertTrue(options.contains(Option.RESUME));
        assertTrue(options.contains(Option.MENU));
        assertTrue(options.contains(Option.QUIT));
    }

    /**
     * Tests that ENTER on RESUME sets the model state to InGameModelState.
     */
    @Test
    void testEnterResumeSetsInGameState() {
        state.handleCommand(dummyModel, Command.ENTER);
        assertTrue(dummyModel.getGameState() instanceof InGameModelState);
    }

    /**
     * Tests that ENTER on MENU sets the model state to MenuModelState.
     */
    @Test
    void testEnterMenuSetsMenuState() {
        state.handleCommand(dummyModel, Command.MOVE_DOWN);
        state.handleCommand(dummyModel, Command.ENTER);
        assertTrue(dummyModel.getGameState() instanceof MenuModelState);
    }
}
