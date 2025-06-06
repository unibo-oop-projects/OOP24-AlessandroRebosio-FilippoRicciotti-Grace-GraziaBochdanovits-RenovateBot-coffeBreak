package it.unibo.coffebreak.model.api.physics;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.impl.common.Vector2D;

/**
 * Represents the physics system for game entities in a 2D space.
 * This interface defines the contract for physics calculations including
 * movement and gravity effects on game entities.
 * 
 * @author Alessandro Rebosio
 */
public interface Physics {
    /**
     * Calculates the horizontal movement vector based on the given command and time
     * delta.
     * 
     * @param deltaTime the time elapsed since the last update (in seconds)
     * @param command   the movement command to process (MOVE_LEFT, MOVE_RIGHT,
     *                  etc.)
     * @return a Vector2D representing the horizontal movement
     */
    Vector2D calculateX(float deltaTime, Command command);

    /**
     * Calculates the vertical movement vector based on the given command and time
     * delta.
     * This includes both movement commands (up/down) and physics effects like
     * gravity.
     * 
     * @param deltaTime the time elapsed since the last update (in seconds)
     * @param command   the movement command to process (MOVE_UP, MOVE_DOWN, JUMP,
     *                  etc.)
     * @return a Vector2D representing the vertical movement
     */
    Vector2D calculateY(float deltaTime, Command command);
}
