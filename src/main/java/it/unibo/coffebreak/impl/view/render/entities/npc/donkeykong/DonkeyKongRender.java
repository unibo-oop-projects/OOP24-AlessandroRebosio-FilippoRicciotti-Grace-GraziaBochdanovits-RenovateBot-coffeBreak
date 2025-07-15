package it.unibo.coffebreak.impl.view.render.entities.npc.donkeykong;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.npc.Antagonist;
import it.unibo.coffebreak.impl.view.render.entities.AnimatedEntityRender;

/**
 * A renderer for the Antagonist entity.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public final class DonkeyKongRender extends AnimatedEntityRender<DonkeyKongRender.DKAnimationType> {

    private static final int SPRITE_WIDTH = 48;
    private static final int SPRITE_HEIGHT = 32;
    private static final int X_ANGRY = 1;
    private static final int X_THROW = 51;
    private static final int Y_ANGRY = 258;
    private static final int Y_THROW = 292;
    private static final int SPACING = 2;

    private static final Map<DKAnimationType, AnimationInfo> ANIMATIONS = Map.ofEntries(
        Map.entry(DKAnimationType.ANGRY, new AnimationInfo(4, SPRITE_WIDTH, SPRITE_HEIGHT, X_ANGRY, Y_ANGRY, SPACING, 0.15f)),
        Map.entry(DKAnimationType.THROW, new AnimationInfo(2, SPRITE_WIDTH, SPRITE_HEIGHT, X_THROW, Y_THROW, SPACING, 0.15f))
    );

    private final DKAnimationStatus animationStatus = new DKAnimationStatus();

    /**
     * Constructs a new DonkeyKongRender with the specified screen dimensions.
     * The entity dimensions will be scaled according to these dimensions.
     *
     *  @param loader the loader used to access the sprite sheet
     * @throws NullPointerException if resource is null
     */
    public DonkeyKongRender(final Loader loader) {
        super(loader);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime, 
                    final int width, final int height) {
        if (!(entity instanceof Antagonist dk)) {
            return;
        }

        if (dk.isThrowing()) {
            if (animationStatus.current != DKAnimationType.THROW) {
                animationStatus.current = DKAnimationType.THROW;
                animationStatus.time = 0f;
            }
        } else {
            if (animationStatus.current == DKAnimationType.THROW 
                && animationStatus.time >= ANIMATIONS.get(DKAnimationType.THROW).totalDuration()) {
                animationStatus.current = DKAnimationType.ANGRY;
                animationStatus.time = 0f;
            }
        }

        final AnimationInfo animationInfo = ANIMATIONS.get(animationStatus.current);
        final BufferedImage frame = updateAndGetFrame(dk, animationStatus.current, animationInfo, deltaTime);

        animationStatus.time += deltaTime;

        g.drawImage(
            frame,
            (int) dk.getPosition().x(),
            (int) dk.getPosition().y(),
            dk.getDimension().width(),
            dk.getDimension().height(),
            null
        );
    }

    /**
     * Enumeration of possible animation states for Donkey Kong character.
     */
    protected enum DKAnimationType {
        /**
         * Represents Donkey Kong's angry animation state. */
        ANGRY,

        /**
         * Represents Donkey Kong's throwing barrel animation state. */
        THROW
    }

    /**
     * Tracks the current animation state and timing.
     */
    private static final class DKAnimationStatus {
        /** Current active animation type. */
        private DKAnimationType current = DKAnimationType.ANGRY;

        /** Time accumulated for the current animation frame. */
        private float time;
    }
}
