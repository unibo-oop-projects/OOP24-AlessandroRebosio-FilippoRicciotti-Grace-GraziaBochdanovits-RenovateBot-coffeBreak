package it.unibo.coffebreak.impl.view.renders.entities.platform;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.view.resources.Resource;
import it.unibo.coffebreak.impl.view.renders.entities.AbstractEntityRender;

/**
 * A render implementation for {@link Platform} entities that maintains proportional
 * sizing relative to the screen dimensions. This render handles the visual representation
 * of platforms in the game, ensuring they scale appropriately when the game window is resized.
 * 
 * <p>The render maintains the original aspect ratio of the platform while scaling it
 * according to the specified screen proportions.</p>
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class PlatformRender extends AbstractEntityRender {

    private static final String PLATFORM_PATH = "/img/platform_sheet.png";
    private final transient BufferedImage platformImage;

    /**
     * Constructs a new PlatformRender with the specified resource loader and screen dimensions.
     *
     * @param resource the resource loader used to load the platform image
     * @throws NullPointerException if the resource loader is null
     */
    public PlatformRender(final Resource resource) {
        super(resource);
        this.platformImage = Objects.requireNonNull(resource, "Resource loader cannot be null").loadImage(PLATFORM_PATH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime) {
        if (entity instanceof Platform) {
            g.drawImage(platformImage, (int) entity.getPosition().x(), (int) entity.getPosition().y(),
                    (int) entity.getDimension().width(), (int) entity.getDimension().height(), null);
        }
    }
}
