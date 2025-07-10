package it.unibo.coffebreak.impl.model.states.gameover;

import it.unibo.coffebreak.api.controller.action.ActionQueue.Action;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.impl.model.states.AbstractModelState;
import it.unibo.coffebreak.impl.model.states.menu.MenuModelState;

/**
 * State representing the game over phase.
 * <p>
 * Handles user input and transitions after the game ends.
 * </p>
 *
 * @author Alessandro Rebosio
 */
public class GameOverModelState extends AbstractModelState {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onExit(final Model model) {
        model.addEntry("");
    }

    /**
     * {@inheritDoc}
     * <p>
     * Handles game over screen actions. Currently supports:
     * <ul>
     * <li>ENTER - returns to the main menu</li>
     * </ul>
     * </p>
     */
    @Override
    public void handleAction(final Model model, final Action action) {
        switch (action) {
            case ENTER -> {
                model.setState(new MenuModelState());
            }
            default -> {
            }
        }
    }
}
