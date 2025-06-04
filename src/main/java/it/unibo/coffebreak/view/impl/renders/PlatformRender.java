package it.unibo.coffebreak.view.impl.renders;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.structure.Platform;
import it.unibo.coffebreak.view.api.renders.EntityRender;
import it.unibo.coffebreak.view.impl.resources.ResourceLoader;

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
public class PlatformRender extends AbstractScalableRender implements EntityRender {

    private static final String PLATFORM_PATH = "/img/platform_sheet.png";
    private static final int PLATFORM_WIDTH = 100;
    private static final int PLATFORM_HEIGHT = 30;
    private static final int Z_INDEX = 0;
    private final transient BufferedImage platformImage;

    /**
     * Constructs a new PlatformRender with the specified resource loader and screen dimensions.
     *
     * @param resource the resource loader used to load the platform image
     * @param screenWidth the initial width of the game screen
     * @param screenHeight the initial height of the game screen
     * @throws NullPointerException if the resource loader is null
     */
    public PlatformRender(final ResourceLoader resource, final int screenWidth, final int screenHeight) {
        super(PLATFORM_WIDTH, PLATFORM_HEIGHT, screenWidth, screenHeight);
        this.platformImage = Objects.requireNonNull(resource, "Resource loader cannot be null").loadImage(PLATFORM_PATH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final Graphics2D g, final Entity entity) {
        Objects.requireNonNull(g, "Graphics context cannot be null");
        if (!(entity instanceof Platform)) {
            throw new IllegalArgumentException("Entity must be a Platform");
        }

        g.drawImage(platformImage, (int) entity.getPosition().x(), (int) entity.getPosition().y(),
                    getScaledWidth(), getScaledHeight(), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canRender(final Entity entity) {
        return entity instanceof Platform;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getZIndex() {
        return Z_INDEX;
    }
}
