package it.unibo.coffebreak.controller.impl;

import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.controller.api.Controller;
import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.controller.api.input.Input;
import it.unibo.coffebreak.controller.impl.input.InputManager;

/**
 * Implementation of {@link Controller} that manages the game's control flow.
 * This class processes input commands and updates the game model accordingly.
 * 
 * <p>
 * The controller follows a command-based design pattern where:
 * <ul>
 * <li>Inputs are converted to {@link Command} objects</li>
 * <li>Commands are processed in sequence</li>
 * <li>The game {@link Model} is updated based on executed commands</li>
 * </ul>
 * 
 * <p>
 * This implementation uses an {@link Input} manager to handle command queuing
 * and processing.
 * 
 * @author Alessandro Rebosio
 */
public class GameController implements Controller {

    private final Input inputManager;

    /**
     * Constructs a new GameController with uninitialized dependencies.
     * The controller must be properly initialized before use.
     */
    public GameController() {
        this.inputManager = new InputManager();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Input getInputManager() {
        return this.inputManager;
    }

    /**
     * Processes all available input commands and updates the game model.
     * This method continuously polls the input manager for commands until
     * no more commands are available, executing each command in sequence.
     *
     * @param model the game model to be updated by the commands
     * @throws IllegalStateException if the controller or its dependencies
     *                               are not properly initialized
     */
    @Override
    public void processInput(final Model model) {
        Command command = inputManager.getCommand();
        while (command != null) {
            model.handleCommand(command);
            command = inputManager.getCommand();
        }
    }
}
