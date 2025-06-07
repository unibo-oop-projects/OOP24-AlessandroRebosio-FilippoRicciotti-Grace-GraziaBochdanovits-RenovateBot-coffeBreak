package it.unibo.coffebreak.impl.controller;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.controller.Controller;
import it.unibo.coffebreak.api.controller.input.Input;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.impl.controller.input.InputManager;
import it.unibo.coffebreak.impl.model.GameModel;

import java.util.Objects;

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
     * Provides access to the game model while ensuring non-null return value.
     * 
     * @return The associated game model instance
     * @throws IllegalStateException if the model reference has been cleared
     */
    @Override
    public Model getModel() {
        return Objects.requireNonNull(this.model, "The model cannot be null");
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
     * Updates the game state based on elapsed time.
     */
    @Override
    public void updateModel(final float deltaTime) {
        if (deltaTime < 0) {
            throw new IllegalArgumentException("DeltaTime cannot be negative");
        }
        this.model.update(deltaTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameActive() {
        return this.model.isRunning();
    }
}
