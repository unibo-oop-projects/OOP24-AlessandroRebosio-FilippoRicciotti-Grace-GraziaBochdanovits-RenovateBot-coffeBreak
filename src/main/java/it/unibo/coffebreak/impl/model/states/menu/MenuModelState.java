package it.unibo.coffebreak.impl.model.states.menu;

import java.util.Collections;
import java.util.List;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.common.Option;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.states.ModelState;
import it.unibo.coffebreak.impl.model.states.AbstractModelState;
import it.unibo.coffebreak.impl.model.states.ingame.InGameModelState;

/**
 * Implementation of {@link ModelState} interface;
 * <p>
 * Represents the <b>Main Menu</b> state of the game.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class MenuModelState extends AbstractModelState {

    private static final List<Option> OPTIONS = List.of(Option.START, Option.QUIT);
    private int selectedOption;

    /**
     * Constructs a new MenuState with the default selected option (START).
     */
    public MenuModelState() {
        this.selectedOption = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case ENTER:
                switch (OPTIONS.get(this.selectedOption)) {
                    case START:
                        model.start();
                        model.setState(new InGameModelState());
                        break;
                    case QUIT:
                        model.stop();
                        break;
                    default:
                        break;
                }
                break;
            case MOVE_UP:
                this.selectedOption = (this.selectedOption - 1 + OPTIONS.size()) % OPTIONS.size();
                break;
            case MOVE_DOWN:
                this.selectedOption = (this.selectedOption + 1) % OPTIONS.size();
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
     * Returns an unmodifiable list of all available menu options.
     *
     * @return the list of {@link Option} available in the menu
     */
    @Override
    public List<Option> getOptions() {
        return Collections.unmodifiableList(OPTIONS);
    }
}
