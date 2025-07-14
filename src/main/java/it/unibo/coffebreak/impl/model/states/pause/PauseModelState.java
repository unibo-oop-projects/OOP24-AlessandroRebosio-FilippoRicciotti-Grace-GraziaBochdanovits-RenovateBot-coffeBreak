package it.unibo.coffebreak.impl.model.states.pause;

import it.unibo.coffebreak.api.common.Option;
import it.unibo.coffebreak.api.controller.action.Action;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.impl.model.states.AbstractModelState;
import it.unibo.coffebreak.impl.model.states.ingame.InGameModelState;
import it.unibo.coffebreak.impl.model.states.menu.MenuModelState;

/**
 * State representing the pause menu of the game.
 * <p>
 * Handles option selection and command processing for pause menu navigation and
 * actions.
 * </p>
 *
 * @author Alessandro Rebosio
 */
public class PauseModelState extends AbstractModelState {

    /**
     * Constructs a new PauseModelState and initializes the available menu options.
     * Adds the RESUME, MENU and QUIT options to the menu.
     */
    public PauseModelState() {
        super.addOption(Option.RESUME);
        super.addOption(Option.MENU);
        super.addOption(Option.QUIT);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Handles pause menu actions in addition to the default navigation.
     * When ENTER is pressed, executes the currently selected option:
     * <ul>
     * <li>RESUME - returns to the in-game state</li>
     * <li>MENU - returns to the main menu</li>
     * <li>QUIT - exits the application</li>
     * </ul>
     * </p>
     */
    @Override
    public void handleAction(final Model model, final Action action) {
        super.handleAction(model, action);
        switch (action) {
            case ENTER -> {
                switch (super.getSelectedOption()) {
                    case RESUME -> {
                        model.setState(new InGameModelState());
                    }
                    case MENU -> {
                        model.setState(new MenuModelState());
                    }
                    case QUIT -> {
                        model.stop();
                    }
                    default -> {
                    }
                }
            }
            default -> {
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onExit(final Model model) {
        model.addEntry("");
    }

}
