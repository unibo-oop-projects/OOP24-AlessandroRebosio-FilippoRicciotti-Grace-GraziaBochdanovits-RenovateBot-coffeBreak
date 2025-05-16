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
 * 
 * @author Filippo Ricciotti
 */
public class PausePhase extends AbstractPhases {

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case ENTER:
                model.setState(new InGamePhase());
                break;
            case ESCAPE:
                model.setState(new MenuPhase());
                break;
            case QUIT:
                // TODO: Notify the controller of the quit, which will in turn notify the view,
                // and the application will close.
                break;
            default:
                break;

        }
    }

}
