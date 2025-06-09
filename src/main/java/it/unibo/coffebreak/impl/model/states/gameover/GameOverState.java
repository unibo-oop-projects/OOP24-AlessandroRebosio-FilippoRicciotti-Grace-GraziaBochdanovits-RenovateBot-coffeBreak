package it.unibo.coffebreak.impl.model.states.gameover;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.states.GameState;
import it.unibo.coffebreak.impl.model.states.AbstractState;
import it.unibo.coffebreak.impl.model.states.menu.MenuState;

/**
 * Implementation of {@link GameState} interface;
 * <p>
 * Represents the <b>Game Over</b> state of the game.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class GameOverState extends AbstractState {

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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() { // TODO: Consider to switch back to enum TypeState
        return "GAME OVER";
    }
}
