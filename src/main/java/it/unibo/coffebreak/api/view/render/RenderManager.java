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
     * Main rendering method that draws all game elements.
     * 
     * @param g      Graphics2D context to render onto
     * @param entities
     * @param deltaTime
     */
    void render(Graphics2D g, List<Entity> entities, float deltaTime);
}
