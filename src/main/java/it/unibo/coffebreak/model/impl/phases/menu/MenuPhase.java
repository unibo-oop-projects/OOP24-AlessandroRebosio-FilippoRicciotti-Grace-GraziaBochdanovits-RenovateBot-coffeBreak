package it.unibo.coffebreak.model.impl.phases.menu;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.phases.Phases;
import it.unibo.coffebreak.model.impl.phases.AbstractPhases;
import it.unibo.coffebreak.model.impl.phases.ingame.InGamePhase;

/**
 * Implementation of {@link Phases} interface;
 * <p>
 * Represents the <b>Main Menu</b> phase of the game.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class MenuPhase extends AbstractPhases {
    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(final Command action, final Model model) {
        switch (action) {
            case ENTER:
                model.setState(new InGamePhase());
                // TODO: model.start()
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
