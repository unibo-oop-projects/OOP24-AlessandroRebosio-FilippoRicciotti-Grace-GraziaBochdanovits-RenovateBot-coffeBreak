package it.unibo.coffebreak.model.states;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.common.Option;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.impl.model.GameModel;
import it.unibo.coffebreak.impl.model.states.menu.MenuModelState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

/**
 * Unit tests for {@link MenuModelState}.
 * <p>
 * These tests verify correct option selection, navigation, and wrapping
 * behavior
 * in the menu state.
 * </p>
 *
 * @author Alessandro Rebosio
 */
class TestMenuModelState {

    private MenuModelState state;
    private Model dummyModel;

    /**
     * Initializes the menu state and dummy model before each test.
     */
    @BeforeEach
    void setUp() {
        state = new MenuModelState();
        dummyModel = new GameModel();
    }

    /**
     * Tests that the initial selected option is START.
     */
    @Test
    void testInitialSelectedOption() {
        assertEquals(Option.START, state.getSelectedOption());
    }

    /**
     * Tests that moving down selects the next option (QUIT).
     */
    @Test
    void testMoveDownSelectsNextOption() {
        state.handleCommand(dummyModel, Command.MOVE_DOWN);
        assertEquals(Option.QUIT, state.getSelectedOption());
    }

    /**
     * Tests that moving up from the first option wraps to the last option.
     */
    @Test
    void testMoveUpWrapsToLastOption() {
        state.handleCommand(dummyModel, Command.MOVE_UP);
        assertEquals(Option.QUIT, state.getSelectedOption());
    }

    /**
     * Tests that moving down from the last option wraps to the first option.
     */
    @Test
    void testMoveDownWrapsToFirstOption() {
        state.handleCommand(dummyModel, Command.MOVE_DOWN);
        state.handleCommand(dummyModel, Command.MOVE_DOWN);
        assertEquals(Option.START, state.getSelectedOption());
    }

    /**
     * Tests that getOptions returns all available menu options.
     */
    @Test
    void testGetOptionsReturnsAllOptions() {
        final List<Option> options = state.options();
        assertEquals(2, options.size());
        assertTrue(options.contains(Option.START));
        assertTrue(options.contains(Option.QUIT));
    }
}
