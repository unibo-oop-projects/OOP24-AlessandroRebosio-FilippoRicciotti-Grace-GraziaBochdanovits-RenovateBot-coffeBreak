package it.unibo.coffebreak.model.impl.phases;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.phases.Phases;

/**
 * Implementation of {@link Phases} interface;
 * <p>
 * Represents the <b>Game Over</b> phase of the game.
 * </p>
 */
public class GameOverPhase implements Phases {

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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleInput(final Command action, final Model model) {
        switch (action) {
            case START: // TODO: change to CONFIRM and it setPhase to Menu
                model.setState(new InGamePhase());
                break;
            case EXIT_GAME:
                // TODO: make only CONFIRM, is only action you can do
                break;
            default:
                break;
        }
    }

}
