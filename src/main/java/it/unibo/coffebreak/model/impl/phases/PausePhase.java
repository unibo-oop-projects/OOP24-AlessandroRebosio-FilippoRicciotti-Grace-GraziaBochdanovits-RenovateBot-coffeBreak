package it.unibo.coffebreak.model.impl.phases;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.phases.Phases;

/**
 * Implementation of {@link Phases} interface;
 * <p>
 * Represents the <b>Paused</b> phase of the game.
 * </p>
 */
public class PausePhase implements Phases {

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
    public void handleInput(final Command action, final Model model) {
        switch (action) {
            case RESUME: // TODO: change to CONFIRM
                model.setState(new InGamePhase());
                break;
            case EXIT_GAME: // TODO: change if you modified Action, pass to menu
                // TODO: model.setState(new MenuPhase)
                break;
            default: // TODO: you can add the quit, but not as default
                break;

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final long deltaTime) {
    }

}
