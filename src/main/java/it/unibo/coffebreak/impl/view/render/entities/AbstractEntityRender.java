package it.unibo.coffebreak.impl.view.render.entities;

import java.awt.Graphics2D;
import java.util.Objects;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.api.view.render.entities.EntityRender;

/**
 * An abstract implementation of {@link EntityRender} that provides basic
 * functionality
 * for rendering entities with screen-relative scaling.
 * 
 * @see EntityRender
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public abstract class AbstractEntityRender implements EntityRender {

    private final Loader resource;

    /**
     * Constructs a new AbstractEntityRender with the specified screen dimensions.
     * The scaling will be calculated relative to these dimensions.
     *
     * @param resource the resource loader for rendering
     */
    public AbstractEntityRender(final Loader resource) {
        this.resource = Objects.requireNonNull(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        g.drawRect((int) entity.getPosition().x(), (int) entity.getPosition().y(), entity.getDimension().width(),
                entity.getDimension().height());
    }

    /**
     * Returns the resource loader used by this view state.
     * <p>
     * Intended for use only by subclasses to load fonts, images, or sounds.
     * </p>
     *
     * @return the {@link Loader} instance for this view
     */
    protected final Loader getResource() {
        return this.resource;
    }
}
