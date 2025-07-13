package it.unibo.coffebreak.api.controller.action;

/**
 * Enumeration representing the different types of actions that can be performed
 * in the coffee break application.
 * 
 * @author Alessandro Rebosio
 */
public enum Action {

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
