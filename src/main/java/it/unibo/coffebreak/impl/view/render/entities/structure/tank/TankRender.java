package it.unibo.coffebreak.impl.view.render.entities.structure.tank;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.view.render.entities.AbstractEntityRender;

/**
 * A renderer for the Tank that draws it as a white rectangle on the screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class TankRender extends AbstractEntityRender {

    /**
     * Constructs a new TankRender with the specified resource loader and screen
     * dimensions.
     *
     * @param resource the resource loader used to load the platform image
     */
    public TankRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        if (entity instanceof Tank) {
            g.setColor(Color.WHITE);

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
}
