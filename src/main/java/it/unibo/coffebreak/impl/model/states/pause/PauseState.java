package it.unibo.coffebreak.impl.model.states.pause;

import java.util.List;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.common.Option;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.states.ModelState;
import it.unibo.coffebreak.impl.model.states.AbstractModelState;
import it.unibo.coffebreak.impl.model.states.ingame.InGameState;
import it.unibo.coffebreak.impl.model.states.menu.MenuState;

/**
 * Implementation of {@link ModelState} interface;
 * <p>
 * Represents the <b>Paused</b> state of the game.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class PauseState extends AbstractModelState {

    private static final List<Option> OPTIONS = List.of(Option.RESUME, Option.EXIT);
    private int selectedOption;

    /**
     * Constructs a new PauseState with the default selected option (START).
     */
    public PauseState() {
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
                        model.setState(InGameState::new);
                        break;
                    case EXIT:
                        model.setState(MenuState::new);
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
}
