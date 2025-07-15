package it.unibo.coffebreak.impl.view.render.entities.structure.tank;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.impl.view.render.entities.AnimatedEntityRender;

/**
 * A renderer for the Tank entity.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class TankRender extends AnimatedEntityRender<TankRender.TankAnimationType> {

    private static final int TANK_X = 145;
    private static final int TANK_Y = 193;
    private static final int FIRE_X = 163;
    private static final int SIZE = 16;
    private static final int SPACING = 2;

    private static final AnimationInfo FIRE_ANIMATION = new AnimationInfo(
        4, SIZE, SIZE, FIRE_X, TANK_Y, SPACING, 0.12f);

    /**
     * Constructs a new TankRender with the specified resource loader and screen
     * dimensions.
     *
     * @param loader the resource loader used to load the platform image
     */
    public TankRender(final Loader loader) {
        super(loader);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime, final int width, final int height) {
        if (entity instanceof final Tank tank) {
            final BufferedImage spriteSheet = getSpriteSheet();
            if (spriteSheet != null) {
                g.drawImage(
                    spriteSheet,
                    (int) entity.getPosition().x(),
                    (int) entity.getPosition().y(),
                    (int) entity.getPosition().x() + entity.getDimension().width(),
                    (int) entity.getPosition().y() + entity.getDimension().height(),
                    TANK_X, TANK_Y, TANK_X + SIZE, TANK_Y + SIZE,
                    null
                );

                if (tank.isActive()) {
                    final BufferedImage flame = updateAndGetFrame(entity, TankAnimationType.ACTIVE, FIRE_ANIMATION, deltaTime);

                    final int flameX = (int) entity.getPosition().x();
                    final int flameY = (int) entity.getPosition().y() - entity.getDimension().height();

                    g.drawImage(
                        flame,
                        (int) entity.getPosition().x(),
                        (int) entity.getPosition().y() - entity.getDimension().height(),
                        flameX + entity.getDimension().width(),
                        flameY + entity.getDimension().height(),
                        0, 0, flame.getWidth(), flame.getHeight(),
                        null
                    );
                }
            }
        }
    }

    /**
     * Enumeration of possible tank animation types.
     * Currently only contains the ACTIVE state for fire animation.
     */
    protected enum TankAnimationType {
        /** Animation state when tank is active and showing fire effect. */
        ACTIVE
    }
}
