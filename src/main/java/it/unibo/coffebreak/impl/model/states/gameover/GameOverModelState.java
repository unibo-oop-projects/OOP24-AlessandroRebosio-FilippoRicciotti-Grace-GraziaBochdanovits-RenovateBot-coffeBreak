package it.unibo.coffebreak.impl.model.states.gameover;

import it.unibo.coffebreak.api.common.Command;
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
     * Handles commands during the game over state.
     * <p>
     * If ENTER is pressed, transitions the model to the main menu state.
     * </p>
     *
     * @param model   the game model
     * @param command the command to process
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case ENTER -> {
                model.setState(new MenuModelState());
            }
            default -> {
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onExit(final Model model) {
        model.addEntry("");
    }
}
