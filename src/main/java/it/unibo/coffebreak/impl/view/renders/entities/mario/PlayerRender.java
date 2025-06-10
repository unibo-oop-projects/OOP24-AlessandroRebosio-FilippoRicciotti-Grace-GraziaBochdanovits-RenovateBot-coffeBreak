package it.unibo.coffebreak.impl.view.renders.entities.mario;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.resources.Resource;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.view.renders.entities.AbstractEntityRender;

/**
 * Render implementation for {@link Mario} player entity.
 *
 * @author Grazia Bochdanovits de Kavna
 */
public class PlayerRender extends AbstractEntityRender {

    /**
     * Constructs a new PlayerRender with specified screen dimensions.
     *
     * @param resource
     */
    public PlayerRender(final Resource resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime) {
        if (entity instanceof final Mario mario  && !mario.isGameOver()) {
            g.setColor(Color.RED);

            g.fillRect((int) entity.getPosition().x(), (int) entity.getPosition().y(),
                       (int) entity.getDimension().width(), (int) entity.getDimension().height());
        }
    }
}
