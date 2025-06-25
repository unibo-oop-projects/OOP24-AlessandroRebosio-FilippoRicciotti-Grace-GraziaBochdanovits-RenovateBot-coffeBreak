package it.unibo.coffebreak.impl.view.render.entities.npc.pauline;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.npc.Princess;
import it.unibo.coffebreak.impl.view.render.entities.AbstractEntityRender;

/**
 * A renderer for the princess that draws him as a pink rectangle on the screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class PaulineRender extends AbstractEntityRender {

    private static final int PAULINE_X = 1;
    private static final int PAULINE_Y = 141;
    private static final int PAULINE_WIDTH = 16;
    private static final int PAULINE_HEIGHT = 32;

    /**
     * Constructs a new Princess with the specified screen dimensions.
     * The target dimensions will be scaled according to these dimensions.
     *
     * @param resource
     */
    public PaulineRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        if (entity instanceof Princess) {
            final BufferedImage spriteSheet = getSpriteSheet();
            if (spriteSheet != null) {
                g.drawImage(
                        spriteSheet,
                        (int) entity.getPosition().x(),
                        (int) entity.getPosition().y(),
                        (int) entity.getPosition().x() + entity.getDimension().width(),
                        (int) entity.getPosition().y() + entity.getDimension().height(),
                        PAULINE_X, PAULINE_Y, PAULINE_X + PAULINE_WIDTH, PAULINE_Y + PAULINE_HEIGHT, null);
            }
        }
    }
}
