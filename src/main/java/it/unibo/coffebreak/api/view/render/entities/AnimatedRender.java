package it.unibo.coffebreak.api.view.render.entities;

import java.awt.image.BufferedImage;

import it.unibo.coffebreak.api.model.entities.Entity;

/**
 * Interface for rendering animated entities using sprite sheets.
 * Implementations are responsible for managing animation states and returning
 * the correct frame for rendering based on time and animation type.
 *
 * @param <T> the enum type representing the animation states or actions
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface AnimatedRender<T extends Enum<T>> {

    /**
     * Updates the animation state and returns the current frame image.
     *
     * @param entity        the entity being animated
     * @param animationType the current animation type
     * @param info          the animation configuration
     * @param deltaTime     time elapsed since last frame in seconds
     * @return the current frame image to render
     * @throws NullPointerException if any parameter is null
     */
    BufferedImage updateAndGetFrame(Entity entity, T animationType,
                AnimationInfo info, float deltaTime);

    /**
     * Extracts a specific frame from the sprite sheet based on animation info.
     *
     * @param frameIndex the index of the frame to extract
     * @param info       the animation configuration
     * @return the requested frame image
     * @throws NullPointerException if animation info is null or sprite sheet not loaded
     */
    BufferedImage getFrameImage(int frameIndex, AnimationInfo info);

    /**
     * Configuration record for an animation sequence.
     * 
     * @param frameCount  number of frames in animation
     * @param frameWidth  width of each frame in pixels
     * @param frameHeight height of each frame in pixels
     * @param xOffset     horizontal offset of first frame in sprite sheet
     * @param yOffset     vertical offset of first frame in sprite sheet
     * @param spacing     pixels between frames in sprite sheet
     * @param frameDuration duration of the frame animation
     */
    record AnimationInfo(int frameCount, int frameWidth, int frameHeight,
                       int xOffset, int yOffset, int spacing, float frameDuration) {
        /**
         * Calculates the total duration of the animation.
         *
         * @return total time to complete the full animation cycle
         */
        public float totalDuration() {
            return frameCount * frameDuration;
        }
    }
}
