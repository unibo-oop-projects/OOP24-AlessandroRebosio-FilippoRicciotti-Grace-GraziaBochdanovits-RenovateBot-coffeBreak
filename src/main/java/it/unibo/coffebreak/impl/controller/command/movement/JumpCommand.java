package it.unibo.coffebreak.impl.controller.command.movement;

import it.unibo.coffebreak.api.controller.command.Command;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;

/**
 * Command for making Mario jump.
 * <p>
 * This command implements the Command Pattern to encapsulate the jump action.
 * When executed, it triggers the jump behavior on the main character if one
 * exists.
 * The jump will only be performed if Mario is currently on a platform.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public class JumpCommand implements Command {

    /**
     * Executes the jump command on the main character.
     * 
     * @param model the game model containing the main character (cannot be null)
     * @throws NullPointerException if model is null
     */
    @Override
    public void execute(final Model model) {
        Command.super.execute(model);
        model.getMainCharacter().ifPresent(MainCharacter::jump);
    }

}
