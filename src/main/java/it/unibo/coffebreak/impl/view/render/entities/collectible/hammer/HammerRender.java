package it.unibo.coffebreak.impl.view.render.entities.collectible.hammer;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.view.render.entities.collectible.AbstractCollectableRender;

/**
 * A renderer for the Hammer that draws it as a white circle on the screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class HammerRender extends AbstractCollectableRender {

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
    protected void renderCollectable(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        g.setColor(Color.YELLOW);
    }
}
