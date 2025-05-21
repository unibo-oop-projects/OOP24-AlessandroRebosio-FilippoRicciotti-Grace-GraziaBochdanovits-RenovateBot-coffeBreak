package it.unibo.coffebreak.model.impl.phases.pause;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.phases.GameState;
import it.unibo.coffebreak.model.impl.phases.AbstractState;
import it.unibo.coffebreak.model.impl.phases.ingame.InGameState;
import it.unibo.coffebreak.model.impl.phases.menu.MenuState;

/**
 * Implementation of {@link GameState} interface;
 * <p>
 * Represents the <b>Paused</b> state of the game.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class PauseState extends AbstractState {

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case ENTER:
                model.setState(InGameState::new);
                break;
            case ESCAPE:
                model.setState(MenuState::new);
                break;
            case QUIT:
                model.stop();
                break;
            default:
                break;
        }
    }

}
