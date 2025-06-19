package it.unibo.coffebreak.impl.view.render.entities.mario;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.view.render.entities.AbstractEntityRender;

/**
 * Render implementation for {@link Mario} player entity.
 * Handles all animation states and rendering logic for the Mario character.
 *
 * @author Grazia Bochdanovits de Kavna
 */
public final class MarioRender extends AbstractEntityRender {

    private static final int MARIO_SIZE = 16;
    private static final int MARIO_X_OFFSET = 1;
    private static final int MARIO_Y_OFFSET = 1;
    private static final int MARIO_FRAME_SPACING_X = 2;
    private static final int MARIO_FRAME_SPACING_Y = 2;
    private static final float FRAME_DURATION = 0.03f;

    /** 
     * Mapping of animation types to their sprite sheet information.
     */
    private static final Map<MarioAnimationType, AnimationInfo> ANIMATIONS = Map.of(
        MarioAnimationType.IDLE, new AnimationInfo(0, 0, 1),
        MarioAnimationType.WALK, new AnimationInfo(1, 0, 2),
        MarioAnimationType.CLIMB, new AnimationInfo(5, 0, 2)
    );

    /** 
     * Tracks animation states for each Mario instance.
     */
    private final Map<Mario, AnimationState> animationStates = new HashMap<>();

    /**
     * Constructs a new MarioRender.
     *
     * @param resource the loader used to access the sprite sheet
     * @throws NullPointerException if resource is null
     */
    public MarioRender(final Loader resource) {
        super(Objects.requireNonNull(resource, "Resource loader cannot be null"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime,
                     final int width, final int height) {
        Objects.requireNonNull(g, "Graphics context cannot be null");
        Objects.requireNonNull(entity, "Entity cannot be null");

        if (!(entity instanceof Mario mario)) {
            return;
        }

        final MarioAnimationType animType = determineAnimation(mario);
        updateAnimation(mario, animType, deltaTime);
        final AnimationState state = animationStates.get(mario);
        final AnimationInfo info = ANIMATIONS.get(animType);
        final BufferedImage sheet = Objects.requireNonNull(getSpriteSheet(), "Sprite sheet cannot be null");

        final int frameX = MARIO_X_OFFSET + (MARIO_SIZE + MARIO_FRAME_SPACING_X) * (info.startColumn + state.frame);
        final int frameY = MARIO_Y_OFFSET + (MARIO_SIZE + MARIO_FRAME_SPACING_Y) * info.row;

        BufferedImage frame = sheet.getSubimage(frameX, frameY, MARIO_SIZE, MARIO_SIZE);
        if (!mario.isFacingRight()) {
            frame = flipImageHorizontally(frame);
        }

        g.drawImage(frame,
            (int) mario.getPosition().x(),
            (int) mario.getPosition().y(),
            mario.getDimension().width(),
            mario.getDimension().height(),
            null
        );
    }

     /**
     * Determines which animation to play based on Mario's state and velocity.
     *
     * @param mario the Mario character to evaluate
     * @return the appropriate animation type for current state
     */
    private MarioAnimationType determineAnimation(final MainCharacter mario) {
        if (mario.getCurrentState().isClimbing()) {
            return MarioAnimationType.CLIMB;
        }
        if (Math.abs(mario.getVelocity().x()) != 0.0f) {
            return MarioAnimationType.WALK;
        }
        return MarioAnimationType.IDLE;
    }

    /**
     * Updates the current animation frame using delta time.
     *
     * @param mario the Mario character to update
     * @param newAnimation the new animation type to transition to
     * @param deltaTime time since last update in seconds
     */
    private void updateAnimation(final Mario mario, final MarioAnimationType newAnimation, final float deltaTime) {
        final AnimationState state = animationStates.computeIfAbsent(mario, m -> new AnimationState());
        final AnimationInfo info = ANIMATIONS.get(newAnimation);

        if (state.animation != newAnimation) {
            state.animation = newAnimation;
            state.frame = 0;
            state.timer = 0f;
        }

        if (info.frameCount > 1) {
            state.timer += deltaTime;
            while (state.timer >= FRAME_DURATION) {
                state.timer -= FRAME_DURATION;
                state.frame = (state.frame + 1) % info.frameCount;
            }
        } else {
            state.frame = 0;
        }
    }

     /**
     * Enumeration of all possible Mario animation states.
     */
    private enum MarioAnimationType {
        /** Mario standing idle. */
        IDLE,
        /** Mario walking. */
        WALK,
        /** Mario climbing. */
        CLIMB
        // TODO: Add animations for remaining states (JUMP, HAMMER, etc.)
    }

    /**
     * Tracks the information of an animation sequence, including its position
     * on the sprite sheet and the number of frames.
     *
     * @param startColumn the column in the sprite sheet where the animation starts
     * @param row the row in the sprite sheet where the animation is located
     * @param frameCount the number of frames in the animation sequence
     */
    private record AnimationInfo(int startColumn, int row, int frameCount) { }

    /**
     * Dynamic animation state for each Mario.
     */
    private static final class AnimationState {
        private MarioAnimationType animation = MarioAnimationType.IDLE;
        private int frame;
        private float timer;
    }
}
