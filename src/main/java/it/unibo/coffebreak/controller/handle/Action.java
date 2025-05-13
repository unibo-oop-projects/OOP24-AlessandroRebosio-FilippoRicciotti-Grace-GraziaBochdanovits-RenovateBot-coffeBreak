package it.unibo.coffebreak.controller.handle;

/**
 * Enum list of possible game Actions.
 */
public enum Action {
    /**
     * Action for starting the game.
     */
    START,
    /**
     * Action for changing level.
     */
    NEXT_LEVEL,
    /**
     * Action for resuming the gameplay.
     */
    RESUME,
    /**
     * Action for quitting the game.
     */
    EXIT_GAME,
    /**
     * Action for pausing the gameplay.
     */
    PAUSE,
    /**
     * Action for moving left.
     */
    MOVE_LEFT,
    /**
     * Action for moving up.
     */
    MOVE_UP,
    /**
     * Action for moving right.
     */
    MOVE_RIGHT,
    /**
     * Action for Jumping.
     */
    JUMP
}
