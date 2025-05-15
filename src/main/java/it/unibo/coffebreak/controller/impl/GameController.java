package it.unibo.coffebreak.controller.impl;

import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.impl.GameModel;
import it.unibo.coffebreak.controller.api.Controller;
import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.controller.api.input.Input;
import it.unibo.coffebreak.controller.impl.input.InputManager;

/**
 * Implementation of {@link Controller} that manages the game's control flow.
 * <p>
 * This controller is responsible for processing user input and updating the
 * game state accordingly. It uses an {@link InputManager} to queue and
 * retrieve commands, and a {@link Model} implementation to reflect changes in
 * the game state.
 * 
 * @author Alessandro Rebosio
 */
public class GameController implements Controller {

    /** Manages input command queue and key bindings. */
    private final Input inputManager;

    /** The game model to update based on commands. */
    private final Model model;

    /**
     * Constructs a new {@code GameController} instance with its own internal
     * {@link InputManager} and {@link GameModel}.
     */
    public GameController() {
        this.inputManager = new InputManager();
        this.model = new GameModel();
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * This implementation delegates to the {@link InputManager}
     * to queue the input for later processing.
     */
    @Override
    public void notifyInput(final int keyCode) {
        this.inputManager.notifyInput(keyCode);
    }

    /**
     * Processes all queued input commands and updates the game model.
     * <p>
     * Commands are retrieved from the input manager one by one and applied
     * to the model via {@link Model#notifyCommand(Command)}.
     */
    @Override
    public void processInput() {
        Command command = inputManager.getCommand();
        while (command != null) {
            this.model.notifyCommand(command);
            command = inputManager.getCommand();
        }
    }
}
