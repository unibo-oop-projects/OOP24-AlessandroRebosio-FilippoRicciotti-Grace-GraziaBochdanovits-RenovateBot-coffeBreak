package it.unibo.coffebreak.impl.view.renders.entities;

import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.render.entities.EntityRender;
import it.unibo.coffebreak.api.view.resources.Resource;

/**
 * An abstract implementation of {@link EntityRender} that provides basic functionality
 * for rendering entities with screen-relative scaling.
 * <p>
 * This class extends {@link AbstractScalableRender} to inherit scaling capabilities
 * based on screen dimensions. Concrete implementations should override the render
 * method to provide specific rendering logic for different entity types.
 * </p>
 * 
 * @see EntityRender
 * @see AbstractScalableRender
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public abstract class AbstractEntityRender implements EntityRender {

    private final Resource resource;

    /**
     * Constructs a new AbstractEntityRender with the specified screen dimensions.
     * The scaling will be calculated relative to these dimensions.
     * 
     * @param resource
     */
    public AbstractEntityRender(final Resource resource) {
        this.resource = resource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void draw(Graphics2D g, Entity entity, float deltaTIme);

    /**
     * Returns the resource loader used by this view state.
     * <p>
     * Intended for use only by subclasses to load fonts, images, or sounds.
     * </p>
     *
     * @return the {@link Resource} instance for this view
     */
    protected final Resource getResource() {
        return this.resource;
    }
}
