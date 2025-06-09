package it.unibo.coffebreak.impl.view.renders.entities.ladder;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.impl.view.renders.entities.AbstractEntityRender;
import it.unibo.coffebreak.impl.view.resources.ResourceLoader;

/**
 * A renderer for ladder entities that draws them as red rectangles on the screen.
 * This class extends {@link AbstractScalableRender} to provide scaling functionality
 * based on the screen dimensions.
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
    public LadderRender(final ResourceLoader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime) {
        if (entity instanceof Ladder) {
            g.setColor(Color.GRAY);

            g.drawRect((int) entity.getPosition().x(), (int) entity.getPosition().y(),
                        (int) entity.getDimension().width(), (int) entity.getDimension().height());
        }
    }
}
