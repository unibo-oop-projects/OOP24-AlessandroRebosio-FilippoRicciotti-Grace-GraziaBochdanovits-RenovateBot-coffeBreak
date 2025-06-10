package it.unibo.coffebreak.impl.view.render.entities.collectible.hammer;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.model.entities.collectible.hammer.Hammer;
import it.unibo.coffebreak.impl.view.render.entities.AbstractEntityRender;

/**
 * A renderer for the Hammer that draws it as a white circle on the screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class HammerRender extends AbstractEntityRender {

    /**
     * Constructs a new Hammer with the specified screen dimensions.
     * The entity dimensions will be scaled according to these dimensions.
     *
     * @param resource
     */
    public HammerRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime) {
        if (entity instanceof final Hammer hammer && !hammer.isCollected()) {
            g.setColor(Color.white);

            final int diameter = Math.min((int) entity.getDimension().width(), (int) entity.getDimension().height());

            g.drawRect((int) entity.getPosition().x(), (int) entity.getPosition().y(), diameter, diameter);
            g.fillOval((int) entity.getPosition().x(), (int) entity.getPosition().y(), diameter, diameter);
        }
    }
}
