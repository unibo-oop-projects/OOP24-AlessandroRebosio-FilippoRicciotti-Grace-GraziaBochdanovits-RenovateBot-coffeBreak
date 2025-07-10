package it.unibo.coffebreak.api.controller.command;

/**
 * Represents a command that can be executed in the coffee break application.
 * This interface follows the Command pattern, allowing for encapsulation of
 * requests as objects, thereby letting you parameterize clients with different
 * requests, queue or log requests, and support undoable operations.
 * 
 * @author Alessandro Rebosio
 */
public interface Command {

    /**
     * Executes the command.
     * Implementations should define the specific behavior to be performed
     * when this command is executed.
     */
    void execute();
}
