package it.unibo.coffebreak.impl.model.states.ingame;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.entities.Movable;
import it.unibo.coffebreak.api.model.entities.npc.Antagonist;
import it.unibo.coffebreak.api.model.states.ModelState;
import it.unibo.coffebreak.impl.model.physics.collision.GameCollision;
import it.unibo.coffebreak.impl.model.states.AbstractModelState;
import it.unibo.coffebreak.impl.model.states.gameover.GameOverModelState;
import it.unibo.coffebreak.impl.model.states.pause.PauseModelState;

/**
 * Implementation of {@link ModelState} interface;
 * <p>
 * Represents the <b>In Game state</b> of the game.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class InGameModelState extends AbstractModelState {

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case ESCAPE -> model.setState(PauseModelState::new);
            default -> {
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleDirection(final Model model, final Command command) {
        model.getMainCharacter().setDirection(command);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final Model model, final float deltaTime) {
        final var player = model.getMainCharacter();
        final int currentLives = player.getLives();

        model.getEntities().stream()
                .filter(Antagonist.class::isInstance)
                .map(Antagonist.class::cast)
                .findFirst()
                .orElseThrow()
                .tryThrowBarrel(deltaTime).ifPresent(model::addEntity);

        model.getEntities().stream()
                .filter(Movable.class::isInstance)
                .map(Movable.class::cast)
                .forEach(e -> e.update(deltaTime));

        GameCollision.checkCollision(model);

        if (currentLives != player.getLives()) {
            model.resetEntities();
        }

        model.transformEntities();
        model.cleanEntities();
        model.nextMap();
        model.calculateBonus(deltaTime);

        if (player.isGameOver()) {
            model.setState(GameOverModelState::new);
        }
    }
}
