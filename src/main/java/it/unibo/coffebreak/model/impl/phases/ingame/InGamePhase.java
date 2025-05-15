package it.unibo.coffebreak.model.impl.phases.ingame;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.phases.Phases;
import it.unibo.coffebreak.model.impl.phases.AbstractPhases;
import it.unibo.coffebreak.model.impl.phases.pause.PausePhase;

/**
 * Implementation of {@link Phases} interface;
 * <p>
 * Represents the <b>In Game phase</b> of the game.
 * </p>
 * @author Filippo Ricciotti
 */
public class InGamePhase extends AbstractPhases {

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleAction(final Command action, final Model model) { // TODO: here i want only command, that not
                                                                        // modify the player, only ESC
        switch (action) {
            case ESC:
                model.setState(new PausePhase());
                break;
            case MOVE_LEFT, MOVE_RIGHT, MOVE_UP, JUMP:
                // TODO: make different case for move
                break;
            default:
                break;
        }
    }

    // TODO: override void update() apply gameLogic character move() check collision, check gameOver, checkNextLevel
}
