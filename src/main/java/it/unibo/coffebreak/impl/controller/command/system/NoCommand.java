package it.unibo.coffebreak.impl.controller.command.system;

import it.unibo.coffebreak.api.controller.command.Command;
import it.unibo.coffebreak.api.model.Model;

/**
 * Null object pattern implementation for no-action commands.
 * 
 * @author Alessandro Rebosio
 */
public class NoCommand implements Command {

    /**
     * Executes the no-action command.
     * 
     * @param model the game model (cannot be null, but no action is performed on
     *              it)
     * @throws NullPointerException if model is null
     */
    @Override
    public void execute(final Model model) {
        Command.super.execute(model);
        // Intentionally empty - no action
    }

}
