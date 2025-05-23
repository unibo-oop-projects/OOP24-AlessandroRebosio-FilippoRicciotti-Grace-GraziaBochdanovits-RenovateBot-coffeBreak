package it.unibo.coffebreak.model.impl.states.ingame;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.entities.Movable;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.model.api.states.GameState;
import it.unibo.coffebreak.model.impl.states.AbstractState;
import it.unibo.coffebreak.model.impl.states.gameover.GameOverState;
import it.unibo.coffebreak.model.impl.states.pause.PauseState;
import it.unibo.coffebreak.model.impl.physics.GameCollision;

/**
 * Implementation of {@link GameState} interface;
 * <p>
 * Represents the <b>In Game state</b> of the game.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public class InGameState extends AbstractState {

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
                model.getPlayer().ifPresent(player -> player.setCommand(command));
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
        model.getAntagonist().flatMap(a -> a.tryThrowBarrel(deltaTime)).ifPresent(model::addEntity);

        model.getEntities().stream()
                .filter(Movable.class::isInstance)
                .map(Movable.class::cast)
                .forEach(e -> e.move(deltaTime));

        GameCollision.checkCollision(model);

        // TODO: barrel to fire
        model.addEntity(null);
        model.getEntities().addAll(model.getEntities().stream()
                .filter(Barrel.class::isInstance)
                .map(Barrel.class::cast)
                .filter(Barrel::canTransformToFire)
                .toList());

        model.getLevelManager().cleanEntities();

        // TODO: If model.getPlayer() is present and has lost a life (via
        // getCurrentState().hasLostLife()),
        // then reset the current level.

        // TODO: nextLevel if Target isRescued

        model.getPlayer().ifPresent(p -> p.getScoreManager().calculateBonus(deltaTime));

        model.getPlayer()
                .filter(Character::isGameOver)
                .ifPresent(p -> model.setState(GameOverState::new));
    }

}
