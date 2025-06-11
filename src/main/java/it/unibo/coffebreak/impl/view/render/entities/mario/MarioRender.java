package it.unibo.coffebreak.impl.view.render.entities.mario;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.view.render.entities.AbstractEntityRender;

/**
 * Render implementation for {@link Mario} player entity.
 *
 * @author Grazia Bochdanovits de Kavna
 */
public class MarioRender extends AbstractEntityRender {

    /**
     * Constructs a new PlayerRender with specified screen dimensions.
     *
     * @param resource
     */
    public MarioRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        if (entity instanceof Mario) {
            g.setColor(Color.RED);

            super.draw(g, entity, deltaTime, width, height);
        }
    }
}
