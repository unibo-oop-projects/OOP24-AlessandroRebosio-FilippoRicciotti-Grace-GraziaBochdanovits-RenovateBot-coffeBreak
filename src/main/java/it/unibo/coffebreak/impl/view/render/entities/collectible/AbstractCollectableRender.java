package it.unibo.coffebreak.impl.view.render.entities.collectible;

import java.awt.Graphics2D;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.collectible.Collectible;
import it.unibo.coffebreak.impl.view.render.entities.AbstractEntityRender;

/**
 * An abstract base class for rendering collectible game entities.
 * <p>
 * This class implements the common rendering logic for all collectible items,
 * specifically handling the case where an item has already been collected.
 * Concrete subclasses must implement the actual rendering of the collectible
 * item.
 * </p>
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public abstract class AbstractCollectableRender extends AbstractEntityRender {

    /**
     * Constructs a new AbstractCollectableRender with the specified resource
     * loader.
     * 
     * @param resource the resource loader used to load images, sounds, and other
     *                 assets.
     */
    public AbstractCollectableRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        if (entity instanceof final Collectible collectable && !collectable.isCollected()) {
            this.renderCollectable(g, entity, deltaTime, width, height);
            super.draw(g, entity, deltaTime, width, height);
        }
    }

    /**
     * Renders the visual representation of the collectible entity.
     * <p>
     * Concrete subclasses must implement this method to provide the specific
     * rendering logic for their collectible type.
     * </p>
     * 
     * @param g         the {@link Graphics2D} context to render onto
     * @param entity    the collectible entity to render
     * @param deltaTime the time in seconds since the last frame was rendered
     * @param width     the width available for rendering the entity
     * @param height    the height available for rendering the entity
     */
    protected abstract void renderCollectable(Graphics2D g, Entity entity, float deltaTime, int width, int height);
}
