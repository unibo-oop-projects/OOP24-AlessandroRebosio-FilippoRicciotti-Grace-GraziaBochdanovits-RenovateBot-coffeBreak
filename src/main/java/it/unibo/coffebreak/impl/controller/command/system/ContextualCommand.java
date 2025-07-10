package it.unibo.coffebreak.impl.controller.command.system;

import java.util.Objects;

import it.unibo.coffebreak.api.controller.command.Command;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.impl.model.states.ingame.InGameModelState;

/**
 * Command that switches behavior based on the current game state.
 * In-game: executes movement command
 * Menu/Pause: executes navigation command
 * 
 * @author Alessandro Rebosio
 */
public class ContextualCommand implements Command {

    private final Command navigationCommand;
    private final Command movementCommand;

    /**
     * Creates a new contextual command with the specified navigation and movement
     * commands.
     * 
     * @param navigationCommand the command to execute when in menu/pause state
     *                          (cannot be null)
     * @param movementCommand   the command to execute when in game state (cannot be
     *                          null)
     * @throws NullPointerException if navigation or movement command are null
     */
    public ContextualCommand(final Command navigationCommand, final Command movementCommand) {
        this.navigationCommand = Objects.requireNonNull(navigationCommand, "The navigationCommand cannot be null");
        this.movementCommand = Objects.requireNonNull(movementCommand, "The movementCommand cannot be null");
    }

    /**
     * Executes the appropriate command based on the current game state.
     * 
     * @param model the game model containing the current state (cannot be null)
     * @throws NullPointerException if model is null
     * 
     */
    @Override
    public void execute(final Model model) {
        Command.super.execute(model);
        if (model.getGameState() instanceof InGameModelState) {
            movementCommand.execute(model);
        } else {
            navigationCommand.execute(model);
        }
    }

}
