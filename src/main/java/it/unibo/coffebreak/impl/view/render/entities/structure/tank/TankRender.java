package it.unibo.coffebreak.impl.view.render.entities.structure.tank;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.impl.view.render.entities.AbstractEntityRender;

/**
 * A renderer for the Tank that draws it as a white rectangle on the screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class TankRender extends AbstractEntityRender {

    private static final int TANK_X = 145;
    private static final int TANK_Y = 193;
    private static final int SIZE = 16;
    //TODO: animazione fuoco sopra tanica

    /**
     * Constructs a new TankRender with the specified resource loader and screen
     * dimensions.
     *
     * @param resource the resource loader used to load the platform image
     */
    public TankRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        if (entity instanceof Tank) {
            final BufferedImage spriteSheet = getSpriteSheet();
            if (spriteSheet != null) {
                g.drawImage(
                    spriteSheet,
                    (int) entity.getPosition().x(),
                    (int) entity.getPosition().y(),
                    (int) entity.getPosition().x() + entity.getDimension().width(),
                    (int) entity.getPosition().y() + entity.getDimension().height(),
                    TANK_X, TANK_Y, TANK_X + SIZE, TANK_Y + SIZE, null);
            }
        }
    }
}
