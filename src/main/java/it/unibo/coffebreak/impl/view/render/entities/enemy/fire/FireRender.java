package it.unibo.coffebreak.impl.view.render.entities.enemy.fire;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.impl.view.render.entities.enemy.AbstractEnemyRender;

/**
 * A renderer for Fire entities that draws them as red circle on the screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class FireRender extends AbstractEnemyRender {

    private static final int SIZE = 16;
    private static final int X_OFFSET = 1;
    private static final int Y_OFFSET = 193;
    private static final int SPACING = 2;

    private static final AnimationInfo ANIMATION = new AnimationInfo(
            2, SIZE, SIZE, X_OFFSET, Y_OFFSET, SPACING, 0.2f);

    /**
     * Constructs a new FireRender with the specified resource loader and screen
     * dimensions.
     *
     * @param resource the resource loader used to load the platform image
     */
    public FireRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void renderEnemy(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        final BufferedImage frame = updateAndGetFrame(entity, EnemyAnimationType.ROLL, ANIMATION, deltaTime);
        g.drawImage(
            frame,
            (int) entity.getPosition().x(),
            (int) entity.getPosition().y(),
            entity.getDimension().width(),
            entity.getDimension().height(),
            null
        );
    }
}
