package it.unibo.coffebreak.api.common;

/**
 * Represents the possible selectable options in the game's menu and pause
 * states.
 * <ul>
 * <li>{@link #START}: Start a new game from the main menu.</li>
 * <li>{@link #RESUME}: Resume the game from the pause menu.</li>
 * <li>{@link #EXIT}: Exit the game or return to the main menu.</li>
 * <li>{@link #NONE}: No option selected or not applicable.</li>
 * </ul>
 * 
 * @author Filippo Ricciotti
 */
public enum Option {
    /** Start a new game from the main menu. */
    START,
    /** Resume the game from the pause menu. */
    RESUME,
    /** Exit the game or return to the main menu. */
    EXIT,
    /** No option selected or not applicable. */
    NONE;
}
