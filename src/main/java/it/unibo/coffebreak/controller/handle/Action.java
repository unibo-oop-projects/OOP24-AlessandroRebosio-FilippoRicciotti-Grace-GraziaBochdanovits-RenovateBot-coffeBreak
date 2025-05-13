package it.unibo.coffebreak.controller.handle;

/**
 * Enum list of possible game Actions.  // TODO: after change fix JavaDoc
 */
public enum Action {
    /**
     * Action for starting the game.
     */
    START, // TODO: change to CONFIRM
    /**
     * Action for resuming the gameplay.
     */
    RESUME, // TODO: remove
    /**
     * Action for quitting the game.
     */
    EXIT_GAME, // TODO: remove
    /**
     * Action for pausing the gameplay.
     */
    PAUSE, // TODO: change to BACK or STOP, is for pause and exit 
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
    JUMP,
    /**
     * Action for going down.
     */
    MOVE_DOWN
}
