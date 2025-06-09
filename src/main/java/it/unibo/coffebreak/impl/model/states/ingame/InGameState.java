package it.unibo.coffebreak.impl.model.states.ingame;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.entities.Movable;
import it.unibo.coffebreak.api.model.states.ModelState;
import it.unibo.coffebreak.impl.model.physics.collision.GameCollision;
import it.unibo.coffebreak.impl.model.states.AbstractModelState;
import it.unibo.coffebreak.impl.model.states.gameover.GameOverState;
import it.unibo.coffebreak.impl.model.states.pause.PauseState;

/**
 * Implementation of {@link ModelState} interface;
 * <p>
 * Represents the <b>In Game state</b> of the game.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class InGameState extends AbstractModelState {

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case ESCAPE:
                model.setState(PauseState::new);
                break;
            case MOVE_LEFT, MOVE_RIGHT, MOVE_UP, MOVE_DOWN, JUMP:
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
        final var player = model.getMainCharacter();
        final int currentLives = player.getLives();

        // model.getAntagonist().flatMap(a ->
        // a.tryThrowBarrel(deltaTime)).ifPresent(model::addEntity);

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
            model.setState(GameOverState::new);
        }
    }
}
