package it.unibo.coffebreak.model.impl.phases;

import it.unibo.coffebreak.controller.handle.Action;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.phases.Phases;

/**
 * Implementation of {@link Phases} interface;
 * <p>
 * Represents the <b>In Game phase</b> of the game.
 * </p>
 */
public class InGamePhase implements Phases {

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterState() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exitState() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final long deltaTime) {
        // TODO: 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleInput(final Action action, final Model model) {
        switch (action) {
            case PAUSE:
                model.setState(new PausePhase());
                break;
            case MOVE_LEFT, MOVE_RIGHT, MOVE_UP, JUMP:
                // TODO: make different case for move
                break;
            default:
                break;
        }
    }

}
