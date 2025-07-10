package it.unibo.coffebreak.impl.model.states.ingame;

import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.entities.npc.Antagonist;
import it.unibo.coffebreak.impl.model.physics.collision.GameCollision;
import it.unibo.coffebreak.impl.model.states.AbstractModelState;
import it.unibo.coffebreak.impl.model.states.gameover.GameOverModelState;

/**
 * State representing the in-game phase where gameplay occurs.
 * <p>
 * Handles player input, entity updates, collisions, and game progression logic.
 * </p>
 *
 * @author Alessandro Rebosio
 */
public class InGameModelState extends AbstractModelState {

    /**
     * Updates the game logic for the in-game state.
     * <p>
     * Updates all entities, handles collisions, manages bonus, and checks for game
     * over.
     * </p>
     *
     * @param model     the game model
     * @param deltaTime the time elapsed since the last update (in seconds)
     */
    @Override
    public void update(final Model model, final float deltaTime) {
        final var player = model.getMainCharacter().get();
        final int currentLives = player.getLives();

        model.getEntities().stream()
                .filter(Antagonist.class::isInstance)
                .map(Antagonist.class::cast)
                .findFirst()
                .ifPresent(a -> a.tryThrowBarrel(deltaTime).ifPresent(model::addEntity));

        model.getEntities().forEach(e -> e.update(deltaTime));

        GameCollision.checkCollision(model);

        if (currentLives != player.getLives()) {
            model.initialEntitiesState();
        }

        model.transformEntities();
        model.nextMap();
        model.calculateBonus(deltaTime);

        if (player.isGameOver()) {
            model.setState(new GameOverModelState());
        }
    }
}
