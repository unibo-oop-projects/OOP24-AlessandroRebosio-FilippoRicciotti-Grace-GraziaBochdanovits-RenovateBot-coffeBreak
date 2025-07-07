package it.unibo.coffebreak.impl.model.states.menu;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.common.Option;
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
     * Handles input commands for menu navigation and selection.
     *
     * @param model   the game model
     * @param command the command to process
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        super.handleCommand(model, command);

        switch (command) {
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
