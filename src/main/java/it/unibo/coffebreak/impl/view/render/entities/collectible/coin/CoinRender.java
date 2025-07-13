package it.unibo.coffebreak.impl.view.render.entities.collectible.coin;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.view.render.entities.AnimatedRender;
import it.unibo.coffebreak.impl.model.entities.collectible.coin.Coin;
import it.unibo.coffebreak.impl.view.render.entities.collectible.AbstractCollectableRender;

/**
 * A renderer for the Coin that draws it as a yellow circle on the screen.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class CoinRender extends AbstractCollectableRender implements AnimatedRender<CoinRender.CoinAnimationType> {
    private static final String COIN_PATH = "/img/coinSheet.png";
    private static final int FRAME_WIDTH = 200;
    private static final int FRAME_HEIGHT = 190;
    private static final int X_OFFSET = 0;
    private static final int Y_OFFSET = 0;

    /** Enum for the states of the coin. */
    protected enum CoinAnimationType { GLOW }
    private static final AnimationInfo GLOW_ANIMATION = new AnimationInfo(
        6, FRAME_WIDTH, FRAME_HEIGHT, X_OFFSET, Y_OFFSET, 0, 0.1f);

    private final BufferedImage coinSheet;
    private final Map<Entity, AnimationState> animationStates = new HashMap<>();

    /**
     * Constructs a new Coin with the specified screen dimensions.
     * The entity dimensions will be scaled according to these dimensions.
     *
     * @param loader
     */
    public CoinRender(final Loader loader) {
        super(loader);
        this.coinSheet = loader.loadImage(COIN_PATH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void renderCollectable(final Graphics2D g, final Entity entity, 
                                   final float deltaTime, final int width, final int height) {
        if (!(entity instanceof Coin)) {
            return;
        }

        final BufferedImage frame = updateAndGetFrame(entity, CoinAnimationType.GLOW, GLOW_ANIMATION, deltaTime);

        g.drawImage(
            frame,
            (int) entity.getPosition().x(),
            (int) entity.getPosition().y(),
            entity.getDimension().width(),
            entity.getDimension().height(),
            null
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage updateAndGetFrame(final Entity entity, final CoinAnimationType animationType, 
                                                            final AnimationInfo info, final float deltaTime) {
        final AnimationState state = animationStates.computeIfAbsent(entity, e -> new AnimationState());

        state.elapsedTime += deltaTime;

        if (info.frameCount() > 0) {
            final float totalDuration = info.totalDuration();

            if (state.isFirstLoop && state.elapsedTime >= totalDuration) {
                state.isFirstLoop = false;
            }

            state.elapsedTime %= totalDuration;
            state.frameIndex = (int) (state.elapsedTime / info.frameDuration());
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
        final int x = info.xOffset() + frameIndex * (info.frameWidth() + info.spacing());
        final int y = info.yOffset();
        return coinSheet.getSubimage(x, y, info.frameWidth(), info.frameHeight());
    }

    /**
     * Tracks animation state for a single coin entity.
     */
    private static final class AnimationState {
        private int frameIndex;
        private float elapsedTime;
        private boolean isFirstLoop = true;
    }
}
