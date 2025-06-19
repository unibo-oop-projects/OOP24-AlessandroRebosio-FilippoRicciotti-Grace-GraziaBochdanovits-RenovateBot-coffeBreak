package it.unibo.coffebreak.impl.view.render.entities.structure.ladder;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.view.render.entities.AbstractEntityRender;

/**
 * A renderer for ladder entities that draws them as red rectangles on the
 * screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class LadderRender extends AbstractEntityRender {

    private static final int LADDER_X = 40;
    private static final int LADDER_Y = 211;
    private static final int LADDER_WIDTH = 10;
    private static final int LADDER_HEIGHT = 16;

    /**
     * Constructs a new LadderRender with the specified screen dimensions.
     * The ladder dimensions will be scaled according to these dimensions.
     *
     * @param resource
     */
    public LadderRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime, final int width, final int height) {
        if (entity instanceof Ladder) {
            final BufferedImage spriteSheet = getSpriteSheet();
            if (spriteSheet != null) {
                g.drawImage(
                        spriteSheet,
                        (int) entity.getPosition().x(),
                        (int) entity.getPosition().y(),
                        (int) entity.getPosition().x() + entity.getDimension().width(),
                        (int) entity.getPosition().y() + entity.getDimension().height(),
                        LADDER_X, LADDER_Y, LADDER_X + LADDER_WIDTH, LADDER_Y + LADDER_HEIGHT, null);
            }
        }
    }
}
