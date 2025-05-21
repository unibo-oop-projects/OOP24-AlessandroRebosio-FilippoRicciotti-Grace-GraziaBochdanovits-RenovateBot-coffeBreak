package it.unibo.coffebreak.model.impl.phases.ingame;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.entities.Movable;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.collectible.Collectible;
import it.unibo.coffebreak.model.api.entities.enemy.Enemy;
import it.unibo.coffebreak.model.api.phases.GameState;
import it.unibo.coffebreak.model.impl.phases.AbstractState;
import it.unibo.coffebreak.model.impl.phases.gameover.GameOverState;
import it.unibo.coffebreak.model.impl.phases.pause.PauseState;
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

        // TODO: it doesn't work, due to the fact that getEntities returns an uneditable
        // list, it must be implemented in the levelManger
        model.getEntities().removeAll(model.getEntities().stream()
                .filter(Enemy.class::isInstance)
                .map(Enemy.class::cast)
                .filter(Enemy::isDestroyed)
                .toList());

        // TODO: same
        model.getEntities().removeAll(model.getEntities().stream()
                .filter(Collectible.class::isInstance)
                .map(Collectible.class::cast)
                .filter(Collectible::isCollected)
                .toList());

        // TODO: If model.getPlayer() is present and has lost a life (via
        // getCurrentState().hasLostLife()),
        // then reset the current level.

        // TODO: nextLevel if Target isRescued

        model.getPlayer().ifPresent(p -> p.getScoreManager().calculateBonus(deltaTime));

        model.getPlayer()
                .filter(Character::isGameOver)
                .ifPresent(p -> model.setState(GameOverState::new));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onExit(final Model model) {
        model.getPlayer().ifPresent(p -> p.getScoreManager().addEntryInLeaderBoard(""));
    }
}
