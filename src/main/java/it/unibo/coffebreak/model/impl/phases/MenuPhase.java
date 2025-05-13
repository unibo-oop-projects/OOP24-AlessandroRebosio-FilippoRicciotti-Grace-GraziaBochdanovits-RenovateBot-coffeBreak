package it.unibo.coffebreak.model.impl.phases;

import it.unibo.coffebreak.controller.handle.Action;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.phases.Phases;

/**
 * Implementation of {@link Phases} interface;
 * <p>
 * Represents the <b>Main Menu</b> phase of the game.
 * </p>
 */
public class MenuPhase implements Phases {

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
    public void handleInput(final Action action, final Model model) {
        switch (action) {
            case START:
                model.setState(new InGamePhase());
                break;
            case EXIT_GAME:
                // TODO: exit game
                break;
            default:
                break;
        }
    }

}
