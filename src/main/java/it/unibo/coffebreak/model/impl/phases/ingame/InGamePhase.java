package it.unibo.coffebreak.model.impl.phases.ingame;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.entities.Movable;
import it.unibo.coffebreak.model.api.phases.Phases;
import it.unibo.coffebreak.model.impl.phases.AbstractPhases;
import it.unibo.coffebreak.model.impl.phases.pause.PausePhase;
import it.unibo.coffebreak.model.impl.physics.GameCollision;

/**
 * Implementation of {@link Phases} interface;
 * <p>
 * Represents the <b>In Game phase</b> of the game.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class InGamePhase extends AbstractPhases {

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case ESCAPE:
                model.setState(new PausePhase());
                break;
            // TODO: case LEFT, RIGHT, MOVE_UP, MOVE_DOWN, JUMP call
            // model.getPlayer().ifPresent(player -> player.setCommand(command));
            default:
                break;
        }
    }

    /***
     * {@inheritDoc}
     */
    @Override
    public void update(final Model model, final float deltaTime) {

        // TODO: to fix
        // model.getDK().tryThrowBarrel().ifPresent(model.getEntities()::add);

        model.getEntities().stream()
                .filter(Movable.class::isInstance)
                .map(Movable.class::cast)
                .forEach(e -> e.move(deltaTime));

        GameCollision.checkCollision(model);

        // TODO: nextLevel

        // TODO: isGameOver and set State
    }

}
