package it.unibo.coffebreak.impl.view.render.entities.structure.platform.normal;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.view.render.entities.structure.platform.AbstractPlatformRender;

/**
 * A renderer for the Normal Platform.
 * 
 * @author Alessandro Rebosio
 */
public class NormalPlatformRender extends AbstractPlatformRender {

    /**
     * Constructs a new Hammer with the specified screen dimensions.
     * The entity dimensions will be scaled according to these dimensions.
     *
     * @param resource
     */
    public NormalPlatformRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void renderPlatform(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        g.setColor(Color.MAGENTA);

        final float xRatio = entity.getPosition().x() / 1000f;
        final float yRatio = entity.getPosition().y() / 1000f;
        final float wRatio = entity.getDimension().width() / 1000f;
        final float hRatio = entity.getDimension().height() / 1000f;

        final int scaledX = (int) (xRatio * width);
        final int scaledY = (int) (yRatio * height);
        final int scaledW = (int) (wRatio * width);
        final int scaledH = (int) (hRatio * height);

        g.drawRect(scaledX, scaledY, scaledW, scaledH);
    }
}
