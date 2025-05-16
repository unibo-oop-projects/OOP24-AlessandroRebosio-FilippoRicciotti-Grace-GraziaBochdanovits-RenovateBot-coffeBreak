package it.unibo.coffebreak.controller.impl;

import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.impl.GameModel;
import it.unibo.coffebreak.controller.api.Controller;
import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.controller.api.input.Input;
import it.unibo.coffebreak.controller.impl.input.InputManager;

/**
 * Main game controller implementation.
 * Uses an InputManager to handle commands and a Model to update the game state.
 * 
 * @author Alessandro Rebosio
 */
public class GameController implements Controller {

    /** Handles input and command management. */
    private final Input inputManager;

    /** The main game model. */
    private final Model model;

    /**
     * Creates a new game controller with default input and model.
     */
    public GameController() {
        this.inputManager = new InputManager();
        this.model = new GameModel();
    }

    /**
     * Adds a new input event to the input manager.
     */
    @Override
    public void notifyInput(final int keyCode) {
        this.inputManager.notifyInput(keyCode);
    }

    @Override
    public void removeInput(int keyCode) {
        this.inputManager.removeInput(keyCode);
    }

    /**
     * Retrieves and applies all input commands to the model.
     */
    @Override
    public void processInput() {
        Command command = inputManager.getCommand();
        while (command != null) {
            this.model.notifyCommand(command);
            command = inputManager.getCommand();
        }
    }

    /**
     * Updates the model based on time passed.
     */
    @Override
    public void updateModel(final float deltaTime) {
        this.model.update(deltaTime);
    }
}
