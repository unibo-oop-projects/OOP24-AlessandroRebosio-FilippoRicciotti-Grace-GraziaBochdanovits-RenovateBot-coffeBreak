package it.unibo.coffebreak.impl.view.renders.entities.structure.tank;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.view.renders.entities.AbstractEntityRender;

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
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime) {
        if (entity instanceof Tank) {
            g.setColor(Color.WHITE);

            g.drawRect((int) entity.getPosition().x(), (int) entity.getPosition().y(),
                    (int) entity.getDimension().width(), (int) entity.getDimension().height());
        }
    }
}
