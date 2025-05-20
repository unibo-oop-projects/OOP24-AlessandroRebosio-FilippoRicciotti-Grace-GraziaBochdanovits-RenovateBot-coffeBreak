package it.unibo.coffebreak.model.impl.phases.ingame;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.model.api.phases.Phases;
import it.unibo.coffebreak.model.impl.phases.AbstractPhases;
import it.unibo.coffebreak.model.impl.phases.pause.PausePhase;

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
            default:
                break;
        }
    }

    /***
     * {@inheritDoc}
     */
    @Override
    public void update(final Model model, final float deltaTime) {
        // TODO: check player move

        model.getDK().tryThrowBarrel().ifPresent(model.getEntities()::add);

        model.getEntities().stream() // TODO: generalize for Enemy change roll to move, also for Mario
                .filter(Barrel.class::isInstance)
                .map(Barrel.class::cast)
                .forEach(e -> e.roll(deltaTime));

        model.checkCollision();

        // TODO: nextLevel

        // TODO: isGameOver and set State 
    }

}
