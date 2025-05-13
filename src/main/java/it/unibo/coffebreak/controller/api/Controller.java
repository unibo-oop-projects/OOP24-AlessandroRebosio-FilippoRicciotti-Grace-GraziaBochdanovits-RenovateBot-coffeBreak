package it.unibo.coffebreak.controller.api;

import it.unibo.coffebreak.controller.api.command.Command;

public interface Controller {

    void proccessInput(Command command);
    
}
