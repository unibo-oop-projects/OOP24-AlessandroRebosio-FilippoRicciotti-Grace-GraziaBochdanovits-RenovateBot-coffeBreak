package it.unibo.coffebreak.impl.view.render.entities.enemy.barrel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.impl.view.render.entities.enemy.AbstractEnemyRender;

/**
 * A renderer for Barrel entities that draws them as blue circle on the screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class BarrelRender extends AbstractEnemyRender {

    private static final int SIZE = 16;
    private static final int X_OFFSET = 1;
    private static final int Y_OFFSET_ROLL = 229;
    private static final int Y_OFFSET_THROW = 73;
    private static final int SPACING = 2;

    private static final Map<EnemyAnimationType, AnimationInfo> ANIMATIONS = Map.of(
        EnemyAnimationType.THROW,  new AnimationInfo(2, SIZE, SIZE, Y_OFFSET_THROW, Y_OFFSET_ROLL, SPACING, 0.15f),
        EnemyAnimationType.ROLL,   new AnimationInfo(4, SIZE, SIZE, X_OFFSET, Y_OFFSET_ROLL, SPACING, 0.15f)
    );

    private final Map<Entity, BarrelAnimationStatus> animationStates = new HashMap<>();

    /**
     * Constructs a new BarrelRender with the specified resource loader and screen
     * dimensions.
     *
     * @param resource the resource loader used to load the platform image
     * @throws NullPointerException if the resource loader is null
     */
    public BarrelRender(final Loader resource) {
        super(resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void renderEnemy(final Graphics2D g, final Entity entity, final float deltaTime,
                            final int width, final int height) {

        final BarrelAnimationStatus status = animationStates.computeIfAbsent(entity, e -> new BarrelAnimationStatus());

        status.time += deltaTime;

        if (status.current == EnemyAnimationType.THROW
                && status.time >= ANIMATIONS.get(EnemyAnimationType.THROW).totalDuration()) {
            status.current = EnemyAnimationType.ROLL;
            status.time = 0f;
        }

        final AnimationInfo info = ANIMATIONS.get(status.current);
        final BufferedImage frame = updateAndGetFrame(entity, status.current, info, deltaTime);

        g.drawImage(
            frame,
            (int) entity.getPosition().x(),
            (int) entity.getPosition().y(),
            entity.getDimension().width(),
            entity.getDimension().height(),
            null
        );
    }

    private static final class BarrelAnimationStatus {
        private EnemyAnimationType current = EnemyAnimationType.THROW;
        private float time;
    }
}
