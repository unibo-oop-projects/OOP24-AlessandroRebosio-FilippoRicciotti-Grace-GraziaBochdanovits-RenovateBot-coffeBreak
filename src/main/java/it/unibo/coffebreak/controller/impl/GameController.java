package it.unibo.coffebreak.controller.impl;

import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.impl.GameModel;
import it.unibo.coffebreak.controller.api.Controller;
import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.controller.api.input.Input;
import it.unibo.coffebreak.controller.impl.input.InputManager;

/**
 * Concrete implementation of the game {@link Controller}.
 * <p>
 * Bridges input handling and model updates in the MVC pattern by:
 * <ul>
 * <li>Receiving input events from the view</li>
 * <li>Translating them into game commands</li>
 * <li>Applying commands to the model</li>
 * </ul>
 * 
 * @author Alessandro Rebosio
 */
public class GameController implements Controller {

    private final Input inputManager;
    private final Model model;

    /**
     * Constructs a new GameController with default dependencies.
     * Initializes:
     * <ul>
     * <li>{@link InputManager} for input handling</li>
     * <li>{@link GameModel} as the game logic implementation</li>
     * </ul>
     */
    public GameController() {
        this.inputManager = new InputManager();
        this.model = new GameModel();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Forwards the key press event to the input manager for processing.
     * The actual command generation depends on current key bindings.
     */
    @Override
    public void handleKeyDown(final int keyCode) {
        this.inputManager.registerKeyPress(keyCode);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Notifies the input manager that a key has been released,
     * which may affect continuous commands like movement.
     */
    @Override
    public void handleKeyUp(final int keyCode) {
        this.inputManager.registerKeyRelease(keyCode);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Processes all pending input commands by:
     * <ol>
     * <li>Retrieving commands from the input queue</li>
     * <li>Executing each command on the model</li>
     * </ol>
     * Continues until the input queue is empty.
     */
    @Override
    public void processInput() {
        Command command = inputManager.getCommand();
        while (command != null) {
            this.model.executeCommand(command);
            command = inputManager.getCommand();
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Updates the game state based on elapsed time, but only
     * if the game is currently in a running state.
     * 
     * @return {@code true} if the game should continue running,
     *         {@code false} if the game has ended
     */
    @Override
    public boolean updateModel(final float deltaTime) {
        if (this.model.isRunning()) {
            this.model.update(deltaTime);
        }
        return this.model.isRunning();
    }
}
