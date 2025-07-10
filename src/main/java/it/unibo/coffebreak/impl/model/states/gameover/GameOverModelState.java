package it.unibo.coffebreak.impl.model.states.gameover;

import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.impl.model.states.AbstractModelState;

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
}
