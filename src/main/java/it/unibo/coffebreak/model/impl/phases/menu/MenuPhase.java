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
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case ENTER:
                // TODO: model.start()
                model.setState(InGamePhase::new);
                break;
            case QUIT:
                model.stop();
                break;
            default:
                break;
        }
    }

}
