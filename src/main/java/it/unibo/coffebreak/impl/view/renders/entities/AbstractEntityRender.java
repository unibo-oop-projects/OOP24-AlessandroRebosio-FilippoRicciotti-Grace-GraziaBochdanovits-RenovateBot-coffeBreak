package it.unibo.coffebreak.impl.view.renders.entities;

import java.awt.Graphics2D;
import java.util.Objects;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.render.entities.EntityRender;
import it.unibo.coffebreak.api.view.resources.Resource;

/**
 * An abstract implementation of {@link EntityRender} that provides basic functionality
 * for rendering entities with screen-relative scaling.
 * 
 * @see EntityRender
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public abstract class AbstractEntityRender implements EntityRender {

    private final Resource resource;

    /**
     * Constructs a new AbstractEntityRender with the specified screen dimensions.
     * The scaling will be calculated relative to these dimensions.
     *
     * @param resource the resource loader for rendering
     */
    public AbstractEntityRender(final Resource resource) { // TODO: use width and height for draw
        this.resource = Objects.requireNonNull(resource);  // resize
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void draw(Graphics2D g, Entity entity, float deltaTime);

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
