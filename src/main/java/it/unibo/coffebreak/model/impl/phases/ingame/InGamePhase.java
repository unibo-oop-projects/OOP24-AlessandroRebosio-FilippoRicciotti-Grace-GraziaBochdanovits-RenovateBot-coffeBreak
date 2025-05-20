package it.unibo.coffebreak.model.impl.phases.ingame;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.entities.Movable;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.collectible.Collectible;
import it.unibo.coffebreak.model.api.entities.enemy.Enemy;
import it.unibo.coffebreak.model.api.phases.Phases;
import it.unibo.coffebreak.model.impl.phases.AbstractPhases;
import it.unibo.coffebreak.model.impl.phases.gameover.GameOverPhase;
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

        model.getPlayer()
                .filter(Character::isGameOver)
                .ifPresent(p -> model.setState(new GameOverPhase()));
    }

}
