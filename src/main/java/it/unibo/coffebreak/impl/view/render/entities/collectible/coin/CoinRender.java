package it.unibo.coffebreak.impl.view.render.entities.collectible.coin;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.view.render.entities.collectible.AbstractCollectableRender;

/**
 * A renderer for the Coin that draws it as a yellow circle on the screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class CoinRender extends AbstractCollectableRender {

    /**
     * Constructs a new Coin with the specified screen dimensions.
     * The entity dimensions will be scaled according to these dimensions.
     *
     * @param resource
     */
    public CoinRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void renderCollectable(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        g.setColor(Color.YELLOW);

        final float xRatio = entity.getPosition().x() / 1000f;
        final float yRatio = entity.getPosition().y() / 1000f;
        final float wRatio = entity.getDimension().width() / 1000f;
        final float hRatio = entity.getDimension().height() / 1000f;

        final int scaledX = (int) (xRatio * width);
        final int scaledY = (int) (yRatio * height);
        final int scaledW = (int) (wRatio * width);
        final int scaledH = (int) (hRatio * height);

        g.drawRect(scaledX, scaledY, scaledW, scaledH);
        g.fillOval(scaledX, scaledY, scaledW, scaledH);
    }
}
