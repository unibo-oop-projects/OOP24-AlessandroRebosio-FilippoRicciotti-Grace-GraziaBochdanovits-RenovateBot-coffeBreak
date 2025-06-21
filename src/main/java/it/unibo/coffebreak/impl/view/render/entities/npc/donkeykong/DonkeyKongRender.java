package it.unibo.coffebreak.impl.view.render.entities.npc.donkeykong;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.npc.Antagonist;
import it.unibo.coffebreak.api.view.loader.Loader;
import it.unibo.coffebreak.impl.view.render.entities.AbstractEntityRender;

/**
 * A renderer for the Antagonist that draws him as a yellow rectangle on the
 * screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class DonkeyKongRender extends AbstractEntityRender {

    private static final int DK_WIDTH = 48;
    private static final int DK_HEIGHT = 32;
    private static final int DK_X = 1;
    private static final int DK_Y = 259;
    private static final int DK_FRAME_SPACING_X = 2;
    private static final int DK_FRAME_SPACING_Y = 2;
    private static final float FRAME_DURATION = 0.03f;

    private static final Map<DonkeyKongAnimationType, AnimationInfo> ANIMATIONS = Map.of(
        DonkeyKongAnimationType.ANGRY, new AnimationInfo(0, 0, 4),
        DonkeyKongAnimationType.THROW, new AnimationInfo(2, 1, 2)
    );

    private final Map<Antagonist, AnimationState> animationStates = new HashMap<>();

    /**
     * Constructs a new DonkeyKong with the specified screen dimensions.
     * The entity dimensions will be scaled according to these dimensions.
     *
     * @param resource
     */
    public DonkeyKongRender(final Loader resource) {
        super(resource);
    }

    private void updateAnimation(final Antagonist dk, final DonkeyKongAnimationType newAnimation, final float deltaTime) {
        final AnimationState state = animationStates.computeIfAbsent(dk, k -> new AnimationState());
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

    private DonkeyKongAnimationType determineAnimation(final Antagonist dk) {
        return dk.isTrowing() ? DonkeyKongAnimationType.THROW : DonkeyKongAnimationType.ANGRY;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g, final Entity entity, final float deltaTime, final int width,
            final int height) {
        if (!(entity instanceof final Antagonist dk)) {
            return;
        }
        final DonkeyKongAnimationType animType = determineAnimation(dk);
        updateAnimation(dk, animType, deltaTime);
        final AnimationState state = animationStates.get(dk);
        final AnimationInfo info = ANIMATIONS.get(animType);
        final BufferedImage sheet = Objects.requireNonNull(getSpriteSheet());

        final int frameX = DK_X + (DK_WIDTH + DK_FRAME_SPACING_X) * (info.startColumn + state.frame);
        final int frameY = DK_Y + (DK_HEIGHT + DK_FRAME_SPACING_Y) * info.row;

        final BufferedImage frame = sheet.getSubimage(frameX, frameY, DK_WIDTH, DK_HEIGHT);

        g.drawImage(frame,
            (int) dk.getPosition().x(),
            (int) dk.getPosition().y(),
            dk.getDimension().width(),
            dk.getDimension().height(),
            null
        );
    }

    private enum DonkeyKongAnimationType {
        ANGRY,
        THROW
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
    //TODO: a sto punto meglio metterlo fuori per tutti
    /**
     * Dynamic animation state for each Mario.
     */
    private static final class AnimationState {
        private DonkeyKongAnimationType animation = DonkeyKongAnimationType.ANGRY;
        private int frame;
        private float timer;
    }
}
