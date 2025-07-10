package it.unibo.coffebreak.impl.controller.command.system;

import java.util.Objects;

import it.unibo.coffebreak.api.controller.action.Action;
import it.unibo.coffebreak.api.controller.command.Command;
import it.unibo.coffebreak.api.model.Model;

/**
 * Command for navigation and menu actions.
 * 
 * @author Alessandro Rebosio
 */
public class NavigationCommand implements Command {

    private final Action action;

    /**
     * Creates a new navigation command with the specified action type.
     * 
     * @param action the action type to execute (cannot be null)
     * @throws NullPointerException if type is null
     */
    public NavigationCommand(final Action action) {
        this.action = Objects.requireNonNull(action, "The action cannot be null");
    }

    /**
     * Executes the navigation command by delegating to the model's action handler.
     * <p>
     * This method passes the stored action type to the model's action handling system,
     * which will process the navigation action according to the current game state.
     * </p>
     * 
     * @param model the game model that will handle the navigation action (cannot be null)
     * @throws NullPointerException if model is null
     */
    @Override
    public void execute(final Model model) {
        Command.super.execute(model);
        model.handleAction(action);
    }
}
