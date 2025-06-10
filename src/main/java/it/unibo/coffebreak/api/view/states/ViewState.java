package it.unibo.coffebreak.api.view.states;

import java.awt.Graphics2D;

/**
 * Represents a visual state of the game's UI (e.g. main menu, pause screen, in-game HUD).
 * <p>
 * Each state handles its own rendering logic and may optionally execute
 * logic when it becomes active or inactive.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public interface ViewState {
    /**
     * Called when this view state becomes the active one.
     * <p>
     * Use this method to initialize resources, play background music,
     * or reset animations specific to this state.
     * </p>
     */
    void onEnter();

    /**
     * Called when this view state is no longer the active one.
     * <p>
     * Use this method to release temporary resources or stop
     * any animations or music that should not persist across states.
     * </p>
     */
    void onExit();

    /**
     * Renders the content of this view state onto the screen.
     *
     * @param g      the {@link Graphics2D} context to draw on
     * @param width  the width of the rendering area
     * @param height the height of the rendering area
     * @param deltaTime
     */
    void draw(Graphics2D g, int width, int height, float deltaTime);
}
