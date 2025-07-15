package it.unibo.coffebreak.impl.view.render.entities.structure.platform.breakable;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.impl.view.render.entities.structure.platform.AbstractPlatformRender;

/**
 * A renderer for the Breakable Platform.
 * 
 * @author Alessandro Rebosio
 */
public class BreakablePlatformRender extends AbstractPlatformRender {

    /**
     * Constructs a new BreackablePlatformRender with the specified screen dimensions.
     * The entity dimensions will be scaled according to these dimensions.
     *
     * @param resource
     */
    public BreakablePlatformRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void renderPlatform(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        if (entity instanceof final Platform platform && !platform.isBroken()) {
            g.setColor(Color.YELLOW);
            g.drawRect(
                    (int) entity.getPosition().x(),
                    (int) entity.getPosition().y(),
                    entity.getDimension().width(),
                    entity.getDimension().height());
        }
    }
}
