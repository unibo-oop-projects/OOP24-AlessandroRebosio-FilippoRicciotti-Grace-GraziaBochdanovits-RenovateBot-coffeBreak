package it.unibo.coffebreak.impl.view.renders.entityrenders.mario;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.view.renders.entityrenders.AbstractEntityRender;

/**
 * Render implementation for {@link Mario} player entity.
 *
 * @author Grazia Bochdanovits de Kavna
 */
public class PlayerRender extends AbstractEntityRender {

    /**
     * Constructs a PlayerRender with specified screen dimensions.
     *
     * @param screenWidth the reference screen width for scaling calculations
     * @param screenHeight the reference screen height for scaling calculations
     * @throws NullPointerException if resource is null
     */
    public PlayerRender(final int screenWidth, final int screenHeight) {
        super(screenWidth, screenHeight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final Graphics2D g, final Entity entity) {
        super.render(g, entity);
        g.setColor(Color.GREEN);
        g.fillRect((int) entity.getPosition().x(), (int) entity.getPosition().y(), 
                    (int) getScaledEntityWidth(entity), (int) getScaledEntityHeight(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canRender(final Entity entity) {
        return entity instanceof Mario;
    }
}
