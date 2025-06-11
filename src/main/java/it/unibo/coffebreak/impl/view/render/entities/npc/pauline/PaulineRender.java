package it.unibo.coffebreak.impl.view.render.entities.npc.pauline;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.npc.Princess;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.view.render.entities.AbstractEntityRender;

/**
 * A renderer for the princess that draws him as a pink rectangle on the screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class PaulineRender extends AbstractEntityRender {

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
            g.setColor(Color.PINK);

            final float xRatio = entity.getPosition().x() / 1000f;
            final float yRatio = entity.getPosition().y() / 1000f;
            final float wRatio = entity.getDimension().width() / 1000f;
            final float hRatio = entity.getDimension().height() / 1000f;

            final int scaledX = (int) (xRatio * width);
            final int scaledY = (int) (yRatio * height);
            final int scaledW = (int) (wRatio * width);
            final int scaledH = (int) (hRatio * height);

            g.fillRect(scaledX, scaledY, scaledW, scaledH);
        }
    }
}
