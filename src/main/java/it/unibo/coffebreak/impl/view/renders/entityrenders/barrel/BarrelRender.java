package it.unibo.coffebreak.impl.view.renders.entityrenders.barrel;

import java.awt.Color;
import java.awt.Graphics2D;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.impl.view.renders.AbstractScalableRender;
import it.unibo.coffebreak.impl.view.renders.entityrenders.AbstractEntityRender;

/**
 * A renderer for Barrel entities that draws them as blue circle on the screen.
 * This class extends {@link AbstractScalableRender} to provide scaling functionality
 * based on the screen dimensions.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class BarrelRender extends AbstractEntityRender {

    /**
     * Constructs a new BarrelRender with the specified screen dimensions.
     * The barrel dimensions will be scaled according to these dimensions.
     *
     * @param screenWidth  the width of the screen
     * @param screenHeight the height of the screen
     */
    public BarrelRender(final int screenWidth, final int screenHeight) {
        super(screenWidth, screenHeight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final Graphics2D g, final Entity entity) { // TODO: rename in draw
        super.render(g, entity);
        g.setColor(Color.BLUE);
        g.fillOval((int) entity.getPosition().x(), (int) entity.getPosition().y(), 
                    (int) getScaledEntityWidth(entity), (int) getScaledEntityHeight(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canRender(final Entity entity) { // TODO: remove
        return entity instanceof Barrel;
    }
}
