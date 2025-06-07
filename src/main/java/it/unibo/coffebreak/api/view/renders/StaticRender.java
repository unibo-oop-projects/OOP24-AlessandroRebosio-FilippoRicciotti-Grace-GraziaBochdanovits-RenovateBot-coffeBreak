package it.unibo.coffebreak.api.view.renders;

import java.awt.Graphics2D;

/**
 * An interface that defines a renderer for static game elements.
 * Static renders are used for elements that don't change their position or state
 * during gameplay, such as backgrounds, UI elements, or static decorations.
 * Implementations of this interface define how these elements should be drawn.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface StaticRender {
    /**
     * Renders the static element using the provided graphics context.
     * This method is called every frame to draw the static content.
     *
     * @param g the Graphics2D context used for rendering operations
     */
    void render(Graphics2D g);

    /**
     * Gets the z-index of this render, which determines its drawing order.
     * Higher values are drawn on top of lower values.
     * 
     * @return the z-index of this render
     */
    int getZIndex();
}
