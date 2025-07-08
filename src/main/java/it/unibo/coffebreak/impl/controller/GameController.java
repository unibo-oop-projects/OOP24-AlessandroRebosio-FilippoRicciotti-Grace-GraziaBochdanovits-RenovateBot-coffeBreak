package it.unibo.coffebreak.impl.controller;

import java.util.List;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.controller.input.Input;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.leaderboard.entry.Entry;
import it.unibo.coffebreak.api.model.states.ModelState;
import it.unibo.coffebreak.impl.controller.input.InputManager;
import it.unibo.coffebreak.impl.model.GameModel;

/**
 * Concrete implementation of the game {@link Controller}.
 * <p>
 * Bridges input handling and model updates in the MVC pattern by:
 * <ul>
 * <li>Receiving input events from the view</li>
 * <li>Translating them into game commands</li>
 * <li>Applying commands to the model</li>
 * <li>Managing the game loop updates</li>
 * </ul>
 * 
 * @author Alessandro Rebosio
 */
public class GameController implements Controller {

    private final Input input = new InputManager();
    private final Model model;

    /**
     * Constructs a new {@code GameController} using the specified {@link Loader}.
     * Initializes the game model with the provided loader.
     *
     * @param loader the loader used to initialize the game model
     */
    public GameController(final Loader loader) {
        this.model = new GameModel(loader);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processInput() {
        final Command cmd = this.input.getCommand();
        if (cmd != null) {
            this.model.handleCommand(cmd);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Updates the game state based on elapsed time.
     */
    @Override
    public void updateModel(final float deltaTime) {
        this.model.update(deltaTime);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Forwards the key press event to the input manager for processing.
     * The actual command generation depends on current key bindings.
     */
    @Override
    public void keyPressed(final int keyCode) {
        this.input.keyPressed(keyCode);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Notifies the input manager that a key has been released,
     * which may affect continuous commands like movement.
     */
    @Override
    public void keyReleased(final int keyCode) {
        this.input.keyReleased(keyCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entity> getEntities() {
        return this.model.getEntities();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScoreValue() {
        return this.model.getScoreValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBonusValue() {
        return this.model.getBonusValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entry> getLeaderBoard() {
        return this.model.getLeaderBoard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHighestScore() {
        return this.model.getHighestScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLevelIndex() {
        return this.model.getLevelIndex();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelState getGameState() {
        return this.model.getGameState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameActive() {
        return this.model.isRunning();
    }
}
