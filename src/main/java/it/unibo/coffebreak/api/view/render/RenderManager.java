package it.unibo.coffebreak.api.view.render;

import java.awt.Graphics2D;
import java.util.List;

import it.unibo.coffebreak.api.model.entities.Entity;

/**
 * Simplified and more focused render manager interface.
 * Handles the rendering pipeline for game entities and static elements.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface RenderManager {
    /**
     * Renders the given list of entities and static elements.
     *
     * @param g the Graphics2D context to draw on
     * @param entities the list of entities to render
     * @param width the width of the rendering area
     * @param height the height of the rendering area
     * @param deltaTime the time elapsed since the last render call, in seconds
     */
    void render(Graphics2D g, List<Entity> entities, int width, int height,
            float deltaTime);
}
