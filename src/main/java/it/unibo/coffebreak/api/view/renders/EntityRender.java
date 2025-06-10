package it.unibo.coffebreak.api.view.renders;

import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;

/**
 * An interface that defines a renderer for a specific type of game entity.
 * Implementations of this interface are responsible for defining how a particular
 * type of entity should be rendered on screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface EntityRender { // TOOD: put in ./entities/EntityRender
    /**
     * Renders the specified entity using the provided graphics context.
     * Implementations should define the visual representation of the entity
     * by drawing appropriate graphics elements.
     *
     * @param g the Graphics2D context used for rendering
     * @param entity the entity to be rendered
     */
    void render(Graphics2D g,  Entity entity); // TODO: raname in draw

    /**
     * Checks whether this renderer can handle the specified entity.
     * This method should return true if the entity is of the type
     * that this renderer is designed to display.
     *
     * @param entity the entity to check (cannot be null)
     * @return true if this renderer can render the given entity, false otherwise
     * @throws NullPointerException if the entity parameter is null
     */
    boolean canRender(Entity entity); // TODO: remove
}
