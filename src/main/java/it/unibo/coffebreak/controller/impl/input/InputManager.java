package it.unibo.coffebreak.controller.impl.input;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.controller.api.input.Input;

public class InputManager implements Input {

    private final Queue<Command> queue;

    public InputManager() {
        this.queue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public Command getCommand() {
        return this.queue.poll();
    }

    @Override
    public void notifyCommand(final Command command) {
        this.queue.add(command);
    }

}
