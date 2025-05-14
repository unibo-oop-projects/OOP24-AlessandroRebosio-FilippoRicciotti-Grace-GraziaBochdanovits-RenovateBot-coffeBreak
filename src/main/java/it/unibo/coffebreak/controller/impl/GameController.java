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
 * <p>
 * Design principles:
 * <ul>
 * <li>User inputs are translated into {@link Command} objects</li>
 * <li>Commands are processed in sequence from a queue</li>
 * <li>The internal {@link Model} is updated based on those commands</li>
 * </ul>
 * 
 * <p>
 * This implementation encapsulates its dependencies and provides access to
 * the input interface via {@link #getInputManager()}.
 * 
 * @author Alessandro Rebosio
 */
public class GameController implements Controller {

    private final Input inputManager;
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
     * Returns the input manager used to enqueue user commands.
     *
     * @return the {@link Input} interface for submitting commands
     */
    @Override
    public Input getInputManager() {
        return this.inputManager;
    }

    /**
     * Processes all queued input commands and updates the game model.
     * <p>
     * Commands are retrieved from the input manager one by one and applied
     * to the model via {@link Model#handleCommand(Command)}.
     */
    @Override
    public void processInput() {
        Command command = inputManager.getCommand();
        while (command != null) {
            this.model.handleCommand(command);
            command = inputManager.getCommand();
        }
    }
}
