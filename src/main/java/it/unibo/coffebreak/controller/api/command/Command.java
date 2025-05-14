package it.unibo.coffebreak.controller.api.command;

/**
 * Enum list of possible game Commmands.
 */
public enum Command {
    /**
     * Command for enterin the game.
     */
    ENTER,
    /**
     * Command for quitting the game.
     */
    QUIT,
    /**
     * Command for pausing the gameplay.
     */
    STOP,
    /**
     * Command for moving left.
     */
    MOVE_LEFT,
    /**
     * Command for moving up.
     */
    MOVE_UP,
    /**
     * Command for moving right.
     */
    MOVE_RIGHT,
    /**
     * Command for Jumping.
     */
    JUMP,
    /**
     * Command for going down.
     */
    MOVE_DOWN
}
