package it.unibo.coffebreak.impl.model.states.pause;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.common.Option;
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
     * Adds the RRESUME, MENU and QUIT options to the menu.
     */
    public PauseModelState() {
        super.addOption(Option.RESUME);
        super.addOption(Option.MENU);
        super.addOption(Option.QUIT);
    }

    /**
     * Handles input commands for pause menu navigation and selection.
     *
     * @param model   the game model
     * @param command the command to process
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        super.handleCommand(model, command);

        switch (command) {
            case ENTER -> {
                switch (this.getSelectedOption()) {
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
}
