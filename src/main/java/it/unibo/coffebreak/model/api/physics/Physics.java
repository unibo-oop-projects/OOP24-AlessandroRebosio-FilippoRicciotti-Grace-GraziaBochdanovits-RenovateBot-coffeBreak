package it.unibo.coffebreak.model.api.physics;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.impl.common.Vector2D;

/**
 * Represents the physics system for game entities in a 2D space.
 * This interface defines the contract for physics calculations including:
 * 
 * @author Alessandro Rebosio
 */
public interface Physics {

    Vector2D calculateX(float deltaTme, Command command);

    Vector2D calculateY(float deltaTme, Command command);
}
