package it.unibo.coffebreak.impl.controller.command.movement;

import it.unibo.coffebreak.api.controller.command.Command;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.impl.common.Vector;

/**
 * Command for stopping Mario's horizontal movement.
 * 
 * @author Alessandro Rebosio
 */
public class StopHorizontalMovementCommand implements Command {

    /**
     * Executes the jump command on the main character.
     * 
     * @param model the game model containing the main character (cannot be null)
     * @throws NullPointerException if model is null
     */
    @Override
    public void execute(final Model model) {
        Command.super.execute(model);
        model.getMainCharacter().ifPresent(character -> {
            character.setVelocity(new Vector(0.0f, character.getVelocity().y()));
        });
    }

}
