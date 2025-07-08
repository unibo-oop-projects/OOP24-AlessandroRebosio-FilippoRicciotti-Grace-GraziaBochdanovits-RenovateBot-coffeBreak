package it.unibo.coffebreak.impl.model.states.ingame;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.entities.npc.Antagonist;
import it.unibo.coffebreak.impl.model.physics.collision.GameCollision;
import it.unibo.coffebreak.impl.model.states.AbstractModelState;
import it.unibo.coffebreak.impl.model.states.gameover.GameOverModelState;
import it.unibo.coffebreak.impl.model.states.pause.PauseModelState;

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
     * Handles commands during the in-game state.
     * <p>
     * ESCAPE sets the model state to pause (pause or exit to menu).
     * </p>
     *
     * @param model   the game model
     * @param command the command to process
     */
    @Override
    public void handleCommand(final Model model, final Command command) {
        switch (command) {
            case ESCAPE -> {
                model.setState(new PauseModelState());
            }
            case MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT, MOVE_UP, JUMP -> { // TODO: to fix
                model.getMainCharacter().ifPresent(p -> p.setDirection(command));
            }
            default -> {
            }
        }
    }

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
