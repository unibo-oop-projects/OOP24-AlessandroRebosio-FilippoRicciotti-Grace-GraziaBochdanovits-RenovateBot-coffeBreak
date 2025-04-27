package it.unibo.coffebreak.model.api.entity;

/**
 * Represents a command in the command pattern, encapsulating an action
 * as an object. This allows for parameterizing clients with different requests,
 * queueing or logging requests, and supporting undoable operations.
 * 
 * <p>Implementations should define the specific action to be performed
 * when the command is executed.
 */
public interface Command {

    /**
     * Executes the command's defined action.
     * 
     * <p>Implementations should handle any necessary error cases internally
     * and document any specific runtime exceptions that might be thrown.
     */
    void execute();
}
