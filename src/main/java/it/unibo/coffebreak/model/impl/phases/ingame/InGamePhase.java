package it.unibo.coffebreak.model.impl.phases.ingame;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.entities.donkeykong.DonkeyKong;
import it.unibo.coffebreak.model.api.phases.Phases;
import it.unibo.coffebreak.model.impl.common.Dimension2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.donkeykong.GameDonkeyKong;
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

    private final DonkeyKong donkeyKong = new GameDonkeyKong(new Position2D(0, 0), new Dimension2D(0, 0));
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

        donkeyKong.tryThrowBarrel().ifPresent(model.getEntities()::add);

        // if game is not over
        final GameCollision collision = new GameCollision();
        collision.checkCollision(model);

        // TODO: checkNextLevel

        // TODO: check gameOver

        // TODO: check character movement
    }

}
