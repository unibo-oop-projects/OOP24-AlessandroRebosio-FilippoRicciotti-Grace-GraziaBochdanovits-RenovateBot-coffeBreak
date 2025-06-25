package it.unibo.coffebreak.impl.model.states.menu;

import java.util.List;

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
     * The list of available menu options.
     */
    private static final List<Option> OPTIONS = List.of(Option.START, Option.QUIT);

    /**
     * The index of the currently selected option.
     */
    private int selectedIndex;

    /**
     * Handles input commands for menu navigation and selection.
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
