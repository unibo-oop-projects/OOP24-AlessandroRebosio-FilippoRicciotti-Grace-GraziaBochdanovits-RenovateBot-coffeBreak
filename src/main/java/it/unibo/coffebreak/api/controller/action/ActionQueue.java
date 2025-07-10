package it.unibo.coffebreak.api.controller.action;

/**
 * Interface for converting physical input events into logical game commands.
 * <p>
 * Follows the Command Pattern to decouple input handling from command
 * execution.
 * Implementations should handle key bindings and command queuing.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public interface ActionQueue {

    /**
     * Enumeration representing the different types of actions that can be performed
     * in the coffee break application.
     * 
     * @author Alessandro Rebosio
     */
    enum Action {

        /**
         * Action representing entering or confirming a selection.
         */
        ENTER,

        /**
         * Action representing escaping or canceling the current operation.
         */
        ESCAPE,

        /**
         * Action representing jumping or performing a jump action in the game.
         */
        SPACE,

        /**
         * Action representing moving left or navigating leftward.
         */
        LEFT,

        /**
         * Action representing moving right or navigating rightward.
         */
        RIGHT,

        /**
         * Action representing moving up or navigating upward in menus.
         */
        UP,

        /**
         * Action representing moving down or navigating downward in menus.
         */
        DOWN;
    }

    /**
     * Adds an action to the queue for later execution.
     * 
     * @param action the action to be added to the queue
     * @return true if the action was successfully added, false otherwise
     */
    boolean add(Action action);

    /**
     * Retrieves and removes the next action from the queue.
     * The action is removed from the queue and returned for processing.
     * 
     * @return the next action in the queue, or null if the queue is empty
     */
    Action poll();

    /**
     * Checks whether the action queue is empty.
     * 
     * @return true if the queue contains no actions, false otherwise
     */
    boolean isEmpty();
}
