package it.unibo.coffebreak.impl.view.render.entities.collectible.hammer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.model.entities.collectible.hammer.Hammer;
import it.unibo.coffebreak.impl.view.render.entities.collectible.AbstractCollectableRender;

/**
 * A renderer for the Hammer that draws it as a white circle on the screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class HammerRender extends AbstractCollectableRender {

    private static final int HAMMER_X = 1;
    private static final int HAMMER_Y = 55;
    private static final int HAMMER_SIZE = 16;

    /**
     * Constructs a new Hammer with the specified screen dimensions.
     * The entity dimensions will be scaled according to these dimensions.
     *
     * @param resource
     */
    public HammerRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void renderCollectable(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        if (entity instanceof Hammer) {
            final BufferedImage spriteSheet = getSpriteSheet();
            if (spriteSheet != null) {
                g.drawImage(
                        spriteSheet,
                        (int) entity.getPosition().x(),
                        (int) entity.getPosition().y(),
                        (int) entity.getPosition().x() + entity.getDimension().width(),
                        (int) entity.getPosition().y() + entity.getDimension().height(),
                        HAMMER_X, HAMMER_Y, HAMMER_X + HAMMER_SIZE, HAMMER_Y + HAMMER_SIZE, null);
            }
        }
    }
}
