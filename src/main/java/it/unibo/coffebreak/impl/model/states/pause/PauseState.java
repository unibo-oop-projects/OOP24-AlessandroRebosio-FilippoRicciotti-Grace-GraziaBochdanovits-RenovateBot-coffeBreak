package it.unibo.coffebreak.impl.model.states.pause;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.states.GameState;
import it.unibo.coffebreak.impl.model.states.AbstractState;
import it.unibo.coffebreak.impl.model.states.ingame.InGameState;
import it.unibo.coffebreak.impl.model.states.menu.MenuState;

/**
 * Implementation of {@link GameState} interface;
 * <p>
 * Represents the <b>Paused</b> state of the game.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class PauseState extends AbstractState {

    private static final int NUM_OPTIONS = 3;
    private int selectedOption; // 0 for Start Game,1 for Menu, 2 for Exit

    private enum PauseOption {
        START_GAME,
        MENU,
        EXIT
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case ENTER:
                if (selectedOption == PauseOption.START_GAME.ordinal()) { // TODO: Resume game
                    model.start();
                    model.setState(InGameState::new);
                } else if (selectedOption == PauseOption.MENU.ordinal()) {
                    model.setState(MenuState::new);
                } else if (selectedOption == PauseOption.EXIT.ordinal()) {
                    model.stop();
                }
                break;
            case ESCAPE:
                model.setState(MenuState::new);
                break;
            case QUIT:
                model.stop();
                break;
            case MOVE_UP:
                selectedOption = (selectedOption - 1 + NUM_OPTIONS) % NUM_OPTIONS;
                break;
            case MOVE_DOWN:
                selectedOption = (selectedOption + 1 + NUM_OPTIONS) % NUM_OPTIONS;
                break;
            default:
                break;
        }
    }

    /***
     * {@inheritDoc}
     */
    @Override
    public int getSelectedOption() {
        return this.selectedOption;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() { // TODO: Consider to switch back to enum TypeState
        return "PAUSE";
    }
}
