package it.unibo.coffebreak.controller.api.command;

/**
 * Enumeration of all possible game commands.
 * <p>
 * These commands represent user inputs used to interact with the game,
 * such as movement, control actions, or menu navigation.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public enum Command {
    /**
     * Command to start a new game,
     * <p>
     * resume the game if it was paused,
     * </p>
     * <p>
     * or return to the main menu after the <b>Game Over</b> screen.
     * </p>
     */
    ENTER,

    /**
     * Command to quit the game.
     */
    QUIT,

    /**
     * Command to pause the game during gameplay,
     * <p>
     * or return to the main menu when the game is paused.
     * </p>
     */
    ESCAPE,

    /**
     * Command to move the character or cursor upward.
     */
    MOVE_UP,

    /**
     * Command to move the character or cursor downward.
     */
    MOVE_DOWN,

    /**
     * Command to move the character or cursor to the right.
     */
    MOVE_RIGHT,

    /**
     * Command to move the character or cursor to the left.
     */
    MOVE_LEFT,

    /**
     * Command to make the character jump.
     */
    JUMP,

    /**
     * Command representing no input or action.
     */
    NONE;

    /**
     * Returns the opposite directional command, if applicable.
     * <p>
     * For example, {@code MOVE_LEFT} returns {@code MOVE_RIGHT}, and vice versa.
     * If the command has no directional opposite, {@code NONE} is returned.
     * </p>
     *
     * @return the opposite directional {@code Command}, or {@code NONE} if
     *         undefined
     */
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
