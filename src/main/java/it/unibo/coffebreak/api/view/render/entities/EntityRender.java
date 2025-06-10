package it.unibo.coffebreak.api.view.render.entities;

import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;

/**
 * An interface that defines a renderer for a specific type of game entity.
 * Implementations of this interface are responsible for defining how a
 * particular
 * type of entity should be rendered on screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface EntityRender {
	/**
	 * Renders the specified entity using the provided graphics context.
	 * Implementations should define the visual representation of the entity
	 * by drawing appropriate graphics elements.
	 *
	 * @param g         the Graphics2D context used for rendering
	 * @param entity    the entity to be rendered
	 * @param deltaTime the time passed since the last frame
	 */
	void draw(Graphics2D g, Entity entity, float deltaTime);
}
