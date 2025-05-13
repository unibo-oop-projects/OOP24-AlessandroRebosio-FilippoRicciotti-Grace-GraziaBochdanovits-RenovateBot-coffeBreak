package it.unibo.coffebreak.controller.api.input;

import it.unibo.coffebreak.controller.api.command.Command;

public interface Input {

    Command getCommand();

    void notifyCommand(Command command);
}
