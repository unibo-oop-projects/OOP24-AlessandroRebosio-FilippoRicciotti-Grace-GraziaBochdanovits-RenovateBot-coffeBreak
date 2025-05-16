package it.unibo.coffebreak.controller.api.command;

/**
 * Enum list of possible game Commmands.
 * 
 * @author Filippo Ricciotti
 */
public enum Command {
    /**
     * Command for entering a new game,
     * <p>
     * for unpausing the game
     * </p>
     * <p>
     * or getting back to the main menu when the <b>Game is Over</b>.
     * </p>
     */
    ENTER,
    /**
     * Command for quitting the game.
     */
    QUIT,
    /**
     * Command for pausing the gameplay when in game
     * <p>
     * or getting back to the main menu when the game is paused.
     * </p>
     */
    ESCAPE,
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
    JUMP,
    /**
     * Command representing no action/input.
     */
    NONE;

    public Command getInverseDirection() {
        return switch (this) {
            case MOVE_UP -> MOVE_DOWN;
            case MOVE_DOWN -> MOVE_UP;
            case MOVE_LEFT -> MOVE_RIGHT;
            case MOVE_RIGHT -> MOVE_LEFT;
            default -> NONE;
        };
    }

}
