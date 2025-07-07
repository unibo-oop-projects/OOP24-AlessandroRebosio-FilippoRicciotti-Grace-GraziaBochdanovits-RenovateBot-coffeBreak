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

    public PauseModelState() {
        super.getOptions().add(Option.RESUME);
        super.getOptions().add(Option.MENU);
        super.getOptions().add(Option.QUIT);
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
