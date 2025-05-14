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
    ESC,
    /**
     * Command for moving up.
     */
    MOVE_UP,
    /**
     * Command for going down.
     */
    MOVE_DOWN,
    /**
     * Command for moving right.
     */
    MOVE_RIGHT,
    /**
     * Command for moving left.
     */
    MOVE_LEFT,
    /**
     * Command for Jumping.
     */
    JUMP

}
