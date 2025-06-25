package it.unibo.coffebreak.model.states;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.impl.model.GameModel;
import it.unibo.coffebreak.impl.model.states.ingame.InGameModelState;
import it.unibo.coffebreak.impl.model.states.pause.PauseModelState;

/**
 * Unit tests for {@link InGameModelState}.
 * <p>
 * These tests verify command handling and state transitions in the in-game
 * state.
 * </p>
 *
 * @author Alessandro Rebosio
 */
class TestInGameModelState {

    private InGameModelState state;
    private Model dummyModel;

    /**
     * Initializes the in game state and dummy model before each test.
     */
    @BeforeEach
    void setUp() {
        state = new InGameModelState();
        dummyModel = new GameModel();
        dummyModel.setState(state);
    }

    /**
     * Tests that ESCAPE command sets the model state to PauseModelState.
     */
    @Test
    void testHandleEscapeCommandSetsPauseState() {
        state.handleCommand(dummyModel, Command.ESCAPE);
        assertEquals(PauseModelState.class, dummyModel.getGameState().getClass());
    }
}
