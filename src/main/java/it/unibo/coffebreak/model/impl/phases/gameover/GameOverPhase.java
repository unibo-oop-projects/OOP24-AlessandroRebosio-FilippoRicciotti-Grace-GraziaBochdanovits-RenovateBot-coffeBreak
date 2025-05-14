package it.unibo.coffebreak.model.impl.phases.gameover;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.phases.Phases;
import it.unibo.coffebreak.model.impl.phases.AbstractPhases;
import it.unibo.coffebreak.model.impl.phases.menu.MenuPhase;

/**
 * Implementation of {@link Phases} interface;
 * <p>
 * Represents the <b>Game Over</b> phase of the game.
 * </p>
 * @author Filippo Ricciotti
 */
public class GameOverPhase extends AbstractPhases {
    /**
     * {@inheritDoc}
     */
    @Override
    public void handleAction(final Command action, final Model model) {
        switch (action) {
            case ENTER:
                model.setState(new MenuPhase());
                break;
            default:
                break;
        }
    }

}
