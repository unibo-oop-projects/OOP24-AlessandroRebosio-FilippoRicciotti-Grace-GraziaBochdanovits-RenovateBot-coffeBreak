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
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime) {
        if (entity instanceof Princess) {
            g.setColor(Color.PINK);

            g.fillRect((int) entity.getPosition().x(), (int) entity.getPosition().y(),
                    (int) entity.getDimension().width(), (int) entity.getDimension().height());
        }
    }
}
