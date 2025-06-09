package it.unibo.coffebreak.impl.model.states.gameover;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.states.ModelState;
import it.unibo.coffebreak.impl.model.states.AbstractModelState;
import it.unibo.coffebreak.impl.model.states.menu.MenuState;

/**
 * Implementation of {@link ModelState} interface;
 * <p>
 * Represents the <b>Game Over</b> state of the game.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class GameOverState extends AbstractModelState {

    private String name;

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case ENTER:
                this.name = "TODO";
                model.setState(MenuState::new);
                break;
            default:
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onExit(final Model model) {
        model.addEntry(this.name);
    }
}
