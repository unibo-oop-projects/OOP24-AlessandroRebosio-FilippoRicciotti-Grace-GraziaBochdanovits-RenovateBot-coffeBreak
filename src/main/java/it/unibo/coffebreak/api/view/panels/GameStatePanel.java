package it.unibo.coffebreak.api.view.panels;

import java.awt.Graphics2D;

/**
 * Represents a game state screen with protected access to its render manager.
 * Provides a default proxy implementation to prevent unauthorized modifications.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface GameStatePanel {
    /**
     * Updates the game state's logic.
     * This method should be called once per frame and should contain:
     * <ul>
     *   <li>Entity updates</li>
     *   <li>State transitions logic</li>
     *   <li>Any other state-specific processing</li>
     * </ul>
     * 
     * <p>Implementations should ensure this method is efficient as it runs every frame.</p>
     */
    void update();

    /**
     * Renders the game state's visual elements.
     * This method is responsible for drawing all visual components of the game state
     * using the provided Graphics2D context. The rendering should adapt to the
     * specified width and height parameters, which typically represent the current
     * dimensions of the game window or panel.
     *
     * @param g the Graphics2D context used for rendering
     * @param width the current width of the rendering area
     * @param height the current height of the rendering area
     */
    void render(Graphics2D g, int width, int height);

}
