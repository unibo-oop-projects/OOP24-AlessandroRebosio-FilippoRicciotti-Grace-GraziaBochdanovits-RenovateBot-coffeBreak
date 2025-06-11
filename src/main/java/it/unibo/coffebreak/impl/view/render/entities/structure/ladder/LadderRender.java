package it.unibo.coffebreak.impl.view.render.entities.structure.ladder;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.view.render.entities.AbstractEntityRender;

/**
 * A renderer for ladder entities that draws them as red rectangles on the
 * screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class LadderRender extends AbstractEntityRender {

    /**
     * Constructs a new LadderRender with the specified screen dimensions.
     * The ladder dimensions will be scaled according to these dimensions.
     *
     * @param resource
     */
    public LadderRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime, final int width, final int height) {
        if (entity instanceof Ladder) {
            g.setColor(Color.GRAY);

            super.draw(g, entity, deltaTime, width, height);
        }
    }
}
