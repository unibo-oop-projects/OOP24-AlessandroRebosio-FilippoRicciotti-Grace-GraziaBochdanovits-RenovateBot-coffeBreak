package it.unibo.coffebreak.impl.view.render.entities.mario;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.model.entities.mario.states.withhammer.WithHammerState;
import it.unibo.coffebreak.impl.view.render.entities.AbstractEntityRender;

/**
 * Render implementation for {@link Mario} player entity.
 * Handles all animation states and rendering logic for the Mario character.
 *
 * @author Grazia Bochdanovits de Kavna
 */
public final class MarioRender extends AbstractEntityRender {

    private static final int SPRITE_SIZE = 16;
    private static final int DOUBLE_SPRITE_SIZE = SPRITE_SIZE * 2;
    private static final int X_OFFSET = 1;
    private static final int Y_OFFSET = 1;
    private static final int X_SPACING = 2;
    private static final int Y_SPACING = 2;
    private static final float FRAME_DURATION = 0.03f;

    /** 
     * Mapping of animation types to their sprite sheet information.
     */
    private static final Map<MarioAnimationType, AnimationInfo> ANIMATIONS = Map.ofEntries(
        Map.entry(MarioAnimationType.IDLE,        new AnimationInfo(0, 0, 1, SPRITE_SIZE, SPRITE_SIZE)),
        Map.entry(MarioAnimationType.WALK,        new AnimationInfo(1, 0, 2, SPRITE_SIZE, SPRITE_SIZE)),
        Map.entry(MarioAnimationType.CLIMB,       new AnimationInfo(5, 0, 2, SPRITE_SIZE, SPRITE_SIZE)),
        Map.entry(MarioAnimationType.JUMP,        new AnimationInfo(3, 0, 1, SPRITE_SIZE, SPRITE_SIZE)),
        Map.entry(MarioAnimationType.WITH_HAMMER, new AnimationInfo(0, 5, 6, DOUBLE_SPRITE_SIZE, DOUBLE_SPRITE_SIZE))
    );

    /** 
     * Tracks animation states for each Mario instance.
     */
    private final Map<Mario, AnimationState> animationStates = new HashMap<>();

    /**
     * Constructs a new MarioRender.
     *
     * @param loader the loader used to access the sprite sheet
     * @throws NullPointerException if resource is null
     */
    public MarioRender(final Loader loader) {
        super(Objects.requireNonNull(loader, "Loader cannot be null"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime,
                     final int width, final int height) {
        if (!(entity instanceof Mario mario)) {
            return;
        }

        final MarioAnimationType animation = resolveAnimationType(mario);
        final BufferedImage frame = updateAndGetFrame(mario, animation, deltaTime);

        g.drawImage(
            frame,
            (int) mario.getPosition().x(),
            (int) mario.getPosition().y(),
            mario.getDimension().width(),
            mario.getDimension().height(),
            null
        );
    }

    /**
     * Determines the appropriate animation type based on Mario's current state.
     *
     * @param mario the Mario character to evaluate
     * @return the appropriate MarioAnimationType for the current state
     */
    private MarioAnimationType resolveAnimationType(final MainCharacter mario) {
        final List<Map.Entry<Predicate<MainCharacter>, MarioAnimationType>> conditions = List.of(
            Map.entry(m -> m.getCurrentState().isClimbing(), MarioAnimationType.CLIMB),
            Map.entry(MainCharacter::isJumping, MarioAnimationType.JUMP),
            Map.entry(m -> m.getCurrentState().equals(new WithHammerState()), MarioAnimationType.WITH_HAMMER),
            Map.entry(m -> Math.abs(m.getVelocity().x()) > 0.0f, MarioAnimationType.WALK)
        );

        return conditions.stream()
            .filter(entry -> entry.getKey().test(mario))
            .map(Map.Entry::getValue)
            .findFirst()
            .orElse(MarioAnimationType.IDLE);
    }

    /**
     * Updates the animation state and returns the current frame to render.
     *
     * @param mario the Mario character being animated
     * @param type the current animation type
     * @param deltaTime the time elapsed since last frame
     * @return the current frame image to render
     */
    private BufferedImage updateAndGetFrame(final Mario mario, final MarioAnimationType type, final float deltaTime) {
        final AnimationState state = animationStates.computeIfAbsent(mario, m -> new AnimationState());
        final AnimationInfo info = ANIMATIONS.get(type);

        if (!state.currentAnimation.equals(type)) {
            state.currentAnimation = type;
            state.frameIndex = 0;
            state.elapsedTime = 0f;
        }

        if (info.frameCount > 1) {
            state.elapsedTime += deltaTime;
            if (state.elapsedTime >= FRAME_DURATION) {
                final int frameAdvance = (int) (state.elapsedTime / FRAME_DURATION);
                state.elapsedTime %= FRAME_DURATION;
                state.frameIndex = (state.frameIndex + frameAdvance) % info.frameCount;
            }
        } else {
            state.frameIndex = 0;
        }

        return getFrameImage(state.frameIndex, info, mario.isFacingRight());
    }

    /**
     * Retrieves the appropriate frame image from the sprite sheet.
     *
     * @param frameIndex the index of the frame to retrieve
     * @param info the animation information
     * @param facingRight whether the character is facing right
     * @return the frame image, flipped horizontally if not facing right
     * @throws NullPointerException if the sprite sheet is not loaded
     */
    private BufferedImage getFrameImage(final int frameIndex, final AnimationInfo info, final boolean facingRight) {
        final BufferedImage sheet = Objects.requireNonNull(getSpriteSheet(), "Sprite sheet cannot be null");

        final int x = X_OFFSET + (SPRITE_SIZE + X_SPACING) * (info.startColumn + frameIndex);
        final int y = Y_OFFSET + (SPRITE_SIZE + Y_SPACING) * info.row;

        final BufferedImage frame = sheet.getSubimage(x, y, info.frameWidth(), info.frameHeight());
        return facingRight ? frame : flipImageHorizontally(frame);
    }

    /**
     * Enumeration of possible Mario animation types.
     */
    private enum MarioAnimationType {
        /** Character is standing still. */
        IDLE,
        /** Character is walking. */
        WALK,
        /** Character is climbing. */
        CLIMB,
        /** Character is jumping. */
        JUMP,
        /** Character is using a hammer. */
        WITH_HAMMER
    }

    /**
     * Record containing animation information for a specific animation type.
     *
     * @param startColumn the starting column in the sprite sheet
     * @param row the row in the sprite sheet
     * @param frameCount the number of frames in the animation
     * @param frameWidth the width of each frame
     * @param frameHeight the height of each frame
     */
    private record AnimationInfo(int startColumn, int row, int frameCount, int frameWidth, int frameHeight) { }

    /**
     * Internal class representing the current state of an animation.
     */
    private static final class AnimationState {
        private MarioAnimationType currentAnimation = MarioAnimationType.IDLE;
        private int frameIndex;
        private float elapsedTime;
    }
}
