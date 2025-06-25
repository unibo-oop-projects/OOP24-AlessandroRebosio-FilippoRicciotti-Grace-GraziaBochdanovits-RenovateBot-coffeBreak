package it.unibo.coffebreak.impl.model.states.pause;

import java.util.Collections;
import java.util.List;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.common.Option;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.states.ModelState;
import it.unibo.coffebreak.impl.model.states.AbstractModelState;
import it.unibo.coffebreak.impl.model.states.ingame.InGameModelState;
import it.unibo.coffebreak.impl.model.states.menu.MenuModelState;

/**
 * Implementation of {@link ModelState} interface;
 * <p>
 * Represents the <b>Paused</b> state of the game.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class PauseModelState extends AbstractModelState {

    private static final List<Option> OPTIONS = List.of(Option.RESUME, Option.MENU, Option.QUIT);
    private int selectedOption;

    /**
     * Constructs a new PauseState with the default selected option (START).
     */
    public PauseModelState() {
        this.selectedOption = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case ENTER:
                switch (OPTIONS.get(selectedOption)) {
                    case RESUME:
                        model.setState(new InGameModelState());
                        break;
                    case MENU:
                        model.setState(new MenuModelState());
                        break;
                    case QUIT:
                        model.stop();
                        break;
                    default:
                        break;
                }
                break;
            case MOVE_UP:
                selectedOption = (selectedOption - 1 + OPTIONS.size()) % OPTIONS.size();
                break;
            case MOVE_DOWN:
                selectedOption = (selectedOption + 1) % OPTIONS.size();
                break;
            default:
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option getSelectedOption() {
        return OPTIONS.get(this.selectedOption);
    }

    /**
     * Returns an unmodifiable list of all available pausw options.
     *
     * @return the list of {@link Option} available in the pause panel
     */
    @Override
    public List<Option> getOptions() {
        return Collections.unmodifiableList(OPTIONS);
    }
}
