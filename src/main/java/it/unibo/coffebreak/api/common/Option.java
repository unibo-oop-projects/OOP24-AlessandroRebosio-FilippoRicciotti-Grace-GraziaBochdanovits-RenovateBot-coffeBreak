package it.unibo.coffebreak.api.common;

/**
 * Represents the possible selectable options in the game's menu and pause
 * states.
 * <ul>
 * <li>{@link #START}: Start a new game from the main menu.</li>
 * <li>{@link #RESUME}: Resume the game from the pause menu.</li>
 * <li>{@link #QUIT}: Exit the game or return to the main menu.</li>
 * <li>{@link #NONE}: No option selected or not applicable.</li>
 * </ul>
 * 
 * @author Filippo Ricciotti
 */
public enum Option {
    /**
     * Start a new game from the main menu.
     */
    START,

    /**
     * Resume the game from the pause menu.
     */
    RESUME,

    /**
     * Exit the game or return to the main menu.
     */
    MENU,

    /**
     * Quit the game or application.
     */
    QUIT,

    /** First Char of the Player name. */
    CHAR0,
    /** Second Char of the Player name. */
    CHAR1,
    /** Third Char of the Player name. */
    CHAR2,

    /**
     * No option selected or not applicable.
     */
    NONE;
}
