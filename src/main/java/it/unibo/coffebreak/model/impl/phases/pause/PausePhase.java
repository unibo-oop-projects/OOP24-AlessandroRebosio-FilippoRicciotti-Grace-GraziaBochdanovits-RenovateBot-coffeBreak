package it.unibo.coffebreak.model.impl.phases.pause;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.phases.Phases;
import it.unibo.coffebreak.model.impl.phases.AbstractPhases;
import it.unibo.coffebreak.model.impl.phases.ingame.InGamePhase;
import it.unibo.coffebreak.model.impl.phases.menu.MenuPhase;

/**
 * Implementation of {@link Phases} interface;
 * <p>
 * Represents the <b>Paused</b> phase of the game.
 * </p>
 */
public class PausePhase extends AbstractPhases {

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleAction(final Command action, final Model model) {
        switch (action) {
            case ENTER:
                model.setState(new InGamePhase());
                break;
            case ESC:
                model.setState(new MenuPhase());
                break;
            case QUIT:
                // TODO: exit game
                // TODO: model.stop()
                break;
            default:
                break;

        }
    }

}
