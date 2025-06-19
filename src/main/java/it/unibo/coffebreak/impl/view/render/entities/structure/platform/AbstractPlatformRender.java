package it.unibo.coffebreak.impl.view.render.entities.structure.platform;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.view.render.entities.AbstractEntityRender;

/**
 * An abstract base class for rendering platform entities within the game.
 *
 * @see Platform
 * @see AbstractEntityRender
 * 
 * @author Alessandro Rebosio
 * 
 */
public abstract class AbstractPlatformRender extends AbstractEntityRender {

    private static final int PLATFORM_X = 109;
    private static final int PLATFORM_Y = 215;
    private static final int PLATFORM_WIDTH = 8;
    private static final int PLATFORM_HEIGHT = 8;

    /**
     * Constructs a new PlatformRender with the specified resource loader and screen
     * dimensions.
     *
     * @param resource the resource loader used to load the platform image
     */
    public AbstractPlatformRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        if (entity instanceof final Platform platform && !platform.isBroken()) {
            this.renderPlatform(g, entity, deltaTime, width, height);
            super.draw(g, entity, deltaTime, width, height);
            final BufferedImage spriteSheet = getSpriteSheet();
            if (spriteSheet != null) {
                g.drawImage(
                        spriteSheet,
                        (int) entity.getPosition().x(),
                        (int) entity.getPosition().y(),
                        (int) entity.getPosition().x() + entity.getDimension().width(),
                        (int) entity.getPosition().y() + entity.getDimension().height(),
                        PLATFORM_X, PLATFORM_Y, PLATFORM_X + PLATFORM_WIDTH, PLATFORM_Y + PLATFORM_HEIGHT, null);
            }
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
    protected abstract void renderPlatform(Graphics2D g, Entity entity, float deltaTime, int width, int height);
}
