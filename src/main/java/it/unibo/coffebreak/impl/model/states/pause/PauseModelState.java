package it.unibo.coffebreak.impl.model.states.pause;

import java.util.List;

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

    private static final List<Option> OPTIONS = List.of(Option.RESUME, Option.MENU, Option.QUIT);

    private int selectedIndex;

    /**
     * Handles input commands for pause menu navigation and selection.
     *
     * @param model   the game model
     * @param command the command to process
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case MOVE_UP -> {
                selectedIndex = (selectedIndex - 1 + OPTIONS.size()) % OPTIONS.size();
            }
            case MOVE_DOWN -> {
                selectedIndex = (selectedIndex + 1) % OPTIONS.size();
            }
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Option getSelectedOption() {
        return OPTIONS.get(selectedIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Option> getOptions() {
        return OPTIONS;
    }

}
