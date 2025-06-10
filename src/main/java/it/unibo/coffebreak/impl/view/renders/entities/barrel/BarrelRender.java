package it.unibo.coffebreak.impl.view.renders.entities.barrel;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.view.renders.entities.AbstractEntityRender;

/**
 * A renderer for Barrel entities that draws them as blue circle on the screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class BarrelRender extends AbstractEntityRender {

    /**
     * Constructs a new BarrelRender with the specified resource loader and screen
     * dimensions.
     *
     * @param resource the resource loader used to load the platform image
     * @throws NullPointerException if the resource loader is null
     */
    public BarrelRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime) {
        if (entity instanceof final Barrel barrel && !barrel.isDestroyed()) {
            g.setColor(Color.BLUE);

            final int diameter = Math.min((int) entity.getDimension().width(), (int) entity.getDimension().height());

            g.drawRect((int) entity.getPosition().x(), (int) entity.getPosition().y(), diameter, diameter);
            g.fillOval((int) entity.getPosition().x(), (int) entity.getPosition().y(), diameter, diameter);
        }
    }
}
