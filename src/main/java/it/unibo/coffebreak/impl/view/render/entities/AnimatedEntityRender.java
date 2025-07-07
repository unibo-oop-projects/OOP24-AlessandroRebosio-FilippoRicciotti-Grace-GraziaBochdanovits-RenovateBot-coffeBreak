package it.unibo.coffebreak.impl.view.render.entities;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;

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
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public abstract class AnimatedEntityRender extends AbstractEntityRender {

    /** Default duration for each animation frame in seconds. */
    protected static final float DEFAULT_FRAME_DURATION = 0.03f;

    /** Map tracking animation states for each entity. */
    private final Map<Entity, AnimationState> animationStates = new HashMap<>();

    /**
     * Constructs a new AnimatedEntityRender with the specified resource loader.
     *
     * @param loader the resource loader for accessing sprite sheets
     * @throws NullPointerException if loader is null
     */
    public AnimatedEntityRender(final Loader loader) {
        super(Objects.requireNonNull(loader, "Loader cannot be null"));
    }

    /**
     * Updates the animation state and returns the current frame image.
     *
     * @param <T>           the enum type representing animation types
     * @param entity        the entity being animated
     * @param animationType the current animation type
     * @param info          the animation configuration
     * @param deltaTime     time elapsed since last frame in seconds
     * @return the current frame image to render
     * @throws NullPointerException if any parameter is null
     */
    protected <T extends Enum<T>> BufferedImage updateAndGetFrame(final Entity entity, final T animationType,
            final AnimationInfo info, final float deltaTime) {

        Objects.requireNonNull(entity, "Entity cannot be null");
        Objects.requireNonNull(animationType, "AnimationType cannot be null");
        Objects.requireNonNull(info, "AnimationInfo cannot be null");

        final AnimationState state = animationStates.computeIfAbsent(entity, e -> new AnimationState());

        if (state.currentAnimation == null || !state.currentAnimation.equals(animationType)) {
            state.currentAnimation = animationType;
            state.frameIndex = 0;
            state.elapsedTime = 0f;
        }

        if (info.frameCount() > 1) {
            state.elapsedTime += deltaTime;
            if (state.elapsedTime >= DEFAULT_FRAME_DURATION) {
                final int frameAdvance = (int) (state.elapsedTime / DEFAULT_FRAME_DURATION);
                state.elapsedTime %= DEFAULT_FRAME_DURATION;
                state.frameIndex = (state.frameIndex + frameAdvance) % info.frameCount();
            }
        } else {
            state.frameIndex = 0;
        }

        return getFrameImage(state.frameIndex, info);
    }

    /**
     * Extracts a specific frame from the sprite sheet based on animation info.
     *
     * @param frameIndex the index of the frame to extract
     * @param info       the animation configuration
     * @return the requested frame image
     * @throws NullPointerException if animation info is null or sprite sheet not loaded
     */
    protected BufferedImage getFrameImage(final int frameIndex, final AnimationInfo info) {
        Objects.requireNonNull(info, "AnimationInfo cannot be null");
        final BufferedImage sheet = Objects.requireNonNull(getSpriteSheet(), "Sprite sheet cannot be null");

        final int x = info.xOffset() + frameIndex * (info.frameWidth() + info.spacing());
        final int y = info.yOffset();

        return sheet.getSubimage(x, y, info.frameWidth(), info.frameHeight());
    }

    /**
     * Tracks the animation state for a single entity.
     */
    protected static final class AnimationState {
        /** Current animation type being played. */
        private Enum<?> currentAnimation;
        /** Current frame index within the animation. */
        private int frameIndex;
        /** Time accumulated since last frame change. */
        private float elapsedTime;
    }

    /**
     * Configuration record for an animation sequence.
     * 
     * @param frameCount  number of frames in animation
     * @param frameWidth  width of each frame in pixels
     * @param frameHeight height of each frame in pixels
     * @param xOffset     horizontal offset of first frame in sprite sheet
     * @param yOffset     vertical offset of first frame in sprite sheet
     * @param spacing     pixels between frames in sprite sheet
     */
    public record AnimationInfo(int frameCount, int frameWidth,
        int frameHeight, int xOffset, int yOffset, int spacing) {
        /**
         * Compact constructor with parameter validation.
         * 
         * @throws NullPointerException if any parameter is null (boxed primitives)
         */
        public AnimationInfo {
            Objects.requireNonNull(frameCount);
            Objects.requireNonNull(frameWidth);
            Objects.requireNonNull(frameHeight);
            Objects.requireNonNull(xOffset);
            Objects.requireNonNull(yOffset);
            Objects.requireNonNull(spacing);
        }
    }
}
