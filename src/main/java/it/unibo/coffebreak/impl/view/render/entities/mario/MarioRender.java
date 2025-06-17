package it.unibo.coffebreak.impl.view.render.entities.mario;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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

    private static final int MARIO_X = 1;
    private static final int MARIO_Y = 1;
    private static final int MARIO_SIZE = 16; 

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
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime,
                   final int width, final int height) {
        if (entity instanceof Mario) {
            final BufferedImage spriteSheet = getSpriteSheet();
            if (spriteSheet != null) {
                g.drawImage(
                        spriteSheet,
                        (int) entity.getPosition().x(),
                        (int) entity.getPosition().y(),
                        (int) entity.getPosition().x() + entity.getDimension().width(),
                        (int) entity.getPosition().y() + entity.getDimension().height(),
                        MARIO_X, MARIO_Y, MARIO_X + MARIO_SIZE, MARIO_Y + MARIO_SIZE, null);
            }
        } //TODO: devi fare le animazioni
    }
}
