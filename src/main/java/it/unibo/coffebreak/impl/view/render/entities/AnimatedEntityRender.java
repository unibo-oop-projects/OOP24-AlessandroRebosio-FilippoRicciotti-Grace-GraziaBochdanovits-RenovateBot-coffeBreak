package it.unibo.coffebreak.impl.view.render.entities;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.render.entities.AnimatedRender;

/**
 * Abstract base class for rendering animated entities with sprite sheet animations.
 * Provides common animation logic including frame timing, state management, and sprite sheet handling.
 * 
 * <p>Subclasses should implement entity-specific animation logic by defining:
 * <ul>
 *   <li>Animation types (enum)</li>
 *   <li>Animation configurations (AnimationInfo)</li>
 *   <li>Animation selection logic</li>
 * </ul>
 * @param <T> the enum type representing different animation states
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public abstract class AnimatedEntityRender<T extends Enum<T>>  extends AbstractEntityRender implements AnimatedRender<T> {

    /** Map tracking animation states for each entity. */
    private final Map<Entity, AnimationState<T>> animationStates = new HashMap<>();

    /**
     * Constructs a new AnimatedEntityRender with the specified resource loader.
     *
     * @param loader the resource loader for accessing sprite sheets
     * @throws NullPointerException if loader is null
     */
    public AnimatedEntityRender(final Loader loader) {
        super(loader);
    }

    /**
     * {@inheritDoc}
     * @param entity        the entity being animated
     * @param animationType the current animation type
     * @param info          the animation configuration
     * @param deltaTime     time elapsed since last frame in seconds
     * @return the current frame image to render
     */
    @Override
    public BufferedImage updateAndGetFrame(final Entity entity, final T animationType,
            final AnimationInfo info, final float deltaTime) {

        Objects.requireNonNull(entity, "Entity cannot be null");
        Objects.requireNonNull(animationType, "AnimationType cannot be null");
        Objects.requireNonNull(info, "AnimationInfo cannot be null");

        final AnimationState<T> state = animationStates.computeIfAbsent(entity, e -> new AnimationState<>());

        if (state.currentAnimation == null || !state.currentAnimation.equals(animationType)) {
            state.currentAnimation = animationType;
            state.frameIndex = 0;
            state.elapsedTime = 0f;
        }

        if (info.frameCount() > 1) {
            state.elapsedTime += deltaTime;

            while (state.elapsedTime >= info.frameDuration()) {
                state.frameIndex = (state.frameIndex + 1) % info.frameCount();
                state.elapsedTime -= info.frameDuration();
            }
        } else {
            state.frameIndex = 0;
        }

        return getFrameImage(state.frameIndex, info);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage getFrameImage(final int frameIndex, final AnimationInfo info) {
        Objects.requireNonNull(info, "AnimationInfo cannot be null");
        final BufferedImage sheet = Objects.requireNonNull(getSpriteSheet(), "Sprite sheet cannot be null");

        final int x = info.xOffset() + frameIndex * (info.frameWidth() + info.spacing());
        final int y = info.yOffset();

        return sheet.getSubimage(x, y, info.frameWidth(), info.frameHeight());
    }

    /**
     * Tracks the animation state for a single entity.
     * @param <T> the enum type representing animation types
     */
    protected static final class AnimationState<T extends Enum<T>> {
        /** Current animation type being played. */
        private T currentAnimation;
        /** Current frame index within the animation. */
        private int frameIndex;
        /** Time accumulated since last frame change. */
        private float elapsedTime;
    }
}
