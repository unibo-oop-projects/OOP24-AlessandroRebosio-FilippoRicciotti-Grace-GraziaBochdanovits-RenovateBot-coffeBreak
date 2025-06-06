package it.unibo.coffebreak.model.impl.states.menu;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.states.GameState;
import it.unibo.coffebreak.model.impl.states.AbstractState;
import it.unibo.coffebreak.model.impl.states.ingame.InGameState;

/**
 * Implementation of {@link GameState} interface;
 * <p>
 * Represents the <b>Main Menu</b> state of the game.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class MenuState extends AbstractState {

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case ENTER:
                model.start();
                model.setState(InGameState::new);
                break;
            case QUIT:
                model.stop();
                break;
            default:
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameStateType getStateType() {
        return GameStateType.MENU;
    }
}
