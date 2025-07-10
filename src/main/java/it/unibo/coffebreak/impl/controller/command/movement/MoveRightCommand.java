package it.unibo.coffebreak.impl.controller.command.movement;

import it.unibo.coffebreak.api.controller.command.Command;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;

/**
 * Command for moving Mario to the right.
 * 
 * @author Alessandro Rebosio
 */
public class MoveRightCommand implements Command {

    /**
     * Executes the jump command on the main character.
     * 
     * @param model the game model containing the main character (cannot be null)
     * @throws NullPointerException if model is null
     */
    @Override
    public void execute(final Model model) {
        Command.super.execute(model);
        model.getMainCharacter().ifPresent(MainCharacter::moveRight);
    }

}
