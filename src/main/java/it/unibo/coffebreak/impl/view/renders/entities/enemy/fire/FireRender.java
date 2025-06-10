package it.unibo.coffebreak.impl.view.renders.entities.enemy.fire;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.enemy.fire.Fire;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.view.renders.entities.AbstractEntityRender;

/**
 * A renderer for Fire entities that draws them as red circle on the screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class FireRender extends AbstractEntityRender {

    /**
     * Constructs a new FireRender with the specified resource loader and screen
     * dimensions.
     *
     * @param resource the resource loader used to load the platform image
     */
    public FireRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime) {
        if (entity instanceof final Fire fire && !fire.isDestroyed()) {
            g.setColor(Color.RED);

            final int diameter = Math.min((int) entity.getDimension().width(), (int) entity.getDimension().height());

            g.drawRect((int) entity.getPosition().x(), (int) entity.getPosition().y(), diameter, diameter);
            g.fillOval((int) entity.getPosition().x(), (int) entity.getPosition().y(), diameter, diameter);
        }
    }
}
