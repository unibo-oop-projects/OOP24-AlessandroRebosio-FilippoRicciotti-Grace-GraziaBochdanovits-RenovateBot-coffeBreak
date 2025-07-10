package it.unibo.coffebreak.impl.model.states.ingame;

import java.util.Optional;

import it.unibo.coffebreak.api.controller.action.ActionQueue.Action;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
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

    private Optional<Action> action = Optional.empty();

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

        if (this.action.isPresent()) {
            switch (action.get()) {
                case ESCAPE -> {
                    model.setState(new PauseModelState());
                }
                case SPACE -> {
                    model.getMainCharacter().ifPresent(MainCharacter::jump);
                }
                case RIGHT -> {
                    model.getMainCharacter().ifPresent(MainCharacter::moveRight);
                }
                case LEFT -> {
                    model.getMainCharacter().ifPresent(MainCharacter::moveLeft);
                }
                case UP -> {
                    model.getMainCharacter().ifPresent(MainCharacter::moveUp);
                }
                default -> {
                }
            }

            this.action = Optional.empty();
        }

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

    /**
     * {@inheritDoc}
     * <p>
     * Handles in-game specific actions. Currently supports:
     * <ul>
     * <li>ESCAPE - pauses the game and transitions to the pause state</li>
     * </ul>
     * Note: Mario's movement and gameplay commands are handled separately
     * through the command queue system, not through this action handler.
     * </p>
     */
    @Override
    public void handleAction(final Model model, final Action action) {
        switch (action) {
            case ESCAPE -> model.setState(new PauseModelState());
            case SPACE, UP, DOWN, LEFT, RIGHT -> this.action = Optional.of(action);
            default -> {
            }
        }
    }
}
