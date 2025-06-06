package it.unibo.coffebreak.model.impl.states.gameover;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.states.GameState;
import it.unibo.coffebreak.model.impl.states.AbstractState;
import it.unibo.coffebreak.model.impl.states.menu.MenuState;

/**
 * Implementation of {@link GameState} interface;
 * <p>
 * Represents the <b>Game Over</b> state of the game.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class GameOverState extends AbstractState {

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case ENTER:
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
        model.addEntryInLeaderBoard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameStateType getStateType() {
        return GameStateType.GAME_OVER;
    }
}
