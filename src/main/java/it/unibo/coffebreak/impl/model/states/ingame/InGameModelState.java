package it.unibo.coffebreak.impl.model.states.ingame;

import it.unibo.coffebreak.api.controller.action.Action;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.entities.PhysicsEntity;
import it.unibo.coffebreak.api.model.entities.npc.Antagonist;
import it.unibo.coffebreak.api.model.physics.PhysicsEngine;
import it.unibo.coffebreak.impl.model.physics.GamePhysicsEngine;
import it.unibo.coffebreak.impl.model.states.AbstractModelState;
import it.unibo.coffebreak.impl.model.states.gameover.GameOverModelState;
import it.unibo.coffebreak.impl.model.states.pause.PauseModelState;

/**
 * State representing the in-game phase where gameplay occurs.
 * <p>
 * Handles entity updates, physics, collisions, and game progression logic.
 * Now uses a unified PhysicsEngine for consistent physics and collision
 * handling.
 * </p>
 *
 * @author Alessandro Rebosio
 */
public class InGameModelState extends AbstractModelState {

    private final PhysicsEngine physicsEngine;

    /**
     * Creates a new in-game model state with the default physics engine.
     */
    public InGameModelState() {
        this(new GamePhysicsEngine());
    }

    /**
     * Creates a new in-game model state with the specified physics engine.
     * 
     * @param physicsEngine the physics engine to use for entity updates
     */
    public InGameModelState(final PhysicsEngine physicsEngine) {
        this.physicsEngine = physicsEngine;
    }

    /**
     * Updates the game logic for the in-game state.
     * <p>
     * Updates all entities using the unified physics engine, manages bonus, and
     * checks for game
     * over. Player movement is handled directly by commands, not in this update
     * loop.
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

        model.getEntities().forEach(entity -> {
            entity.update(deltaTime);
            if (entity instanceof PhysicsEntity) {
                this.physicsEngine.updateEntity(entity, model, deltaTime);
            }
        });

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
     * Only handles the ESCAPE action to pause the game.
     * All movement commands are now handled directly by the command system.
     * </p>
     */
    @Override
    public void handleAction(final Model model, final Action action) {
        switch (action) {
            case ESCAPE -> model.setState(new PauseModelState());
            default -> {
                // Other actions are handled by the command system
            }
        }
    }
}
