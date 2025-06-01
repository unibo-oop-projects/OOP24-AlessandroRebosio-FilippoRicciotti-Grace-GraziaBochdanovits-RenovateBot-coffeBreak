package it.unibo.coffebreak.view.api.renders;

import java.awt.Graphics2D;

import it.unibo.coffebreak.model.api.entities.Entity;

/**
 * An interface that defines a renderer for a specific type of game entity.
 * Implementations of this interface are responsible for defining how a particular
 * type of entity should be rendered on screen.
 *
 * @param <T> the type of entity this renderer can render, must extend {@link Entity}
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface EntityRender<T extends Entity> {
    /**
     * Renders the specified entity using the provided graphics context.
     * Implementations should define the visual representation of the entity
     * by drawing appropriate graphics elements.
     *
     * @param g the Graphics2D context used for rendering
     * @param entity the entity to be rendered
     */
    void render(Graphics2D g, T entity);

    /**
     * Gets the z-index of this render, which determines its drawing order.
     * Higher values are drawn on top of lower values.
     * 
     * @return the z-index of this render
     */
    int getZIndex();
}
