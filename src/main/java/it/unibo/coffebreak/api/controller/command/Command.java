package it.unibo.coffebreak.api.controller.command;

import java.util.Objects;

import it.unibo.coffebreak.api.model.Model;

/**
 * Command interface for the Command Pattern implementation.
 * Encapsulates a request as an object, allowing for parameterization and
 * queuing.
 * 
 * @author Alessandro Rebosio
 */
public interface Command {

    /**
     * Executes the command on the given model.
     * 
     * @param model the game model to execute the command on (cannot be null)
     * @throws NullPointerException if model is null
     */
    default void execute(final Model model) {
        Objects.requireNonNull(model, "The model cannot be null");
    }

}
