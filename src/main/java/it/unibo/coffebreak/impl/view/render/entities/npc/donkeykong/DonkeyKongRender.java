package it.unibo.coffebreak.impl.view.render.entities.npc.donkeykong;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.model.entities.npc.donkeykong.DonkeyKong;
import it.unibo.coffebreak.impl.view.render.entities.AbstractEntityRender;

/**
 * A renderer for the Antagonist that draws him as a yellow rectangle on the
 * screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class DonkeyKongRender extends AbstractEntityRender {

    /**
     * Constructs a new DonkeyKong with the specified screen dimensions.
     * The entity dimensions will be scaled according to these dimensions.
     *
     * @param resource
     */
    public DonkeyKongRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        if (entity instanceof DonkeyKong) {
            g.setColor(Color.YELLOW);

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
