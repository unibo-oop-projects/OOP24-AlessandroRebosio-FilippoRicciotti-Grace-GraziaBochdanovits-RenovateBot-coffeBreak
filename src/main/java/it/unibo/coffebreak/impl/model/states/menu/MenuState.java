package it.unibo.coffebreak.impl.model.states.menu;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.states.GameState;
import it.unibo.coffebreak.impl.model.states.AbstractState;
import it.unibo.coffebreak.impl.model.states.ingame.InGameState;

/**
 * Implementation of {@link GameState} interface;
 * <p>
 * Represents the <b>Main Menu</b> state of the game.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class MenuState extends AbstractState {

    private static final int NUM_OPTIONS = 2;
    private int selectedOption; // 0 for Start Game, 1 for Exit

    private enum MenuOption {
        START_GAME,
        EXIT
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case ENTER:
                if (selectedOption == MenuOption.START_GAME.ordinal()) {
                    model.start();
                    model.setState(InGameState::new);
                } else if (selectedOption == MenuOption.EXIT.ordinal()) {
                    model.stop();
                }
                break;
            case QUIT:
                model.stop();
                break;
            case MOVE_UP:
                selectedOption = (selectedOption - 1 + NUM_OPTIONS) % NUM_OPTIONS;
                break;
            case MOVE_DOWN:
                selectedOption = (selectedOption + 1) % NUM_OPTIONS;
                break;
            default:
                break;
        }

    }

    /**
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
        return "MENU";
    }

}
