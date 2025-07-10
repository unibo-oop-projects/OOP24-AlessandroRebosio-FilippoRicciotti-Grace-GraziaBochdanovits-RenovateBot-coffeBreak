package it.unibo.coffebreak.impl.model.states.menu;

import it.unibo.coffebreak.api.common.Option;
import it.unibo.coffebreak.api.controller.action.ActionQueue.Action;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.impl.model.states.AbstractModelState;
import it.unibo.coffebreak.impl.model.states.ingame.InGameModelState;

/**
 * State representing the main menu of the game.
 * <p>
 * Handles option selection and command processing for menu navigation and
 * actions.
 * </p>
 *
 * @author Alessandro Rebosio
 */
public class MenuModelState extends AbstractModelState {

    /**
     * Constructs a new MenuModelState and initializes the available menu options.
     * Adds the START and QUIT options to the menu.
     */
    public MenuModelState() {
        super.addOption(Option.START);
        super.addOption(Option.QUIT);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Handles menu-specific actions in addition to the default navigation.
     * When ENTER is pressed, executes the currently selected option:
     * <ul>
     * <li>START - starts the game and transitions to the in-game state</li>
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
                    case START -> {
                        model.start();
                        model.setState(new InGameModelState());
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
}
