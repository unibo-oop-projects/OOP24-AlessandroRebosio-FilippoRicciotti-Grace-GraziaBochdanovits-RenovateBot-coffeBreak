package it.unibo.coffebreak.controller.impl;

import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.controller.api.Controller;
import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.controller.api.input.Input;

public class GameController implements Controller {

    private Input inputManager;

    public GameController() {
        // TODO: this.model = null;
        // TODO: this.inputManager = null;
    }

    @Override
    public void proccessInput(Model model) {
        Command command = inputManager.getCommand();
        while (command != null) {
            // TODO: model.handleCommand(command)
            command = inputManager.getCommand();
        }
    }
}
