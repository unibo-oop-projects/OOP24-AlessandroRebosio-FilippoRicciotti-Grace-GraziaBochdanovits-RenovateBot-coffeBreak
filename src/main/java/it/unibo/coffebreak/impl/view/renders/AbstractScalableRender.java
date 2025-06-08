package it.unibo.coffebreak.impl.view.renders;

import it.unibo.coffebreak.api.view.renders.ScalableRender;

/**
 * An abstract implementation of {@link ScalableRender} that provides basic functionality
 * for scaling renders while optionally maintaining the aspect ratio.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public abstract class AbstractScalableRender implements ScalableRender {

    private final int screenWidth;
    private final int screenHeight;
    private float screenRatioX = 1.0f;
    private float screenRatioY = 1.0f;

    /**
     * Constructs an AbstractScalableRender with the specified original dimensions.
     *
     * @param screenWidth the total screen width used for ratio calculation
     * @param screenHeight the total screen height used for ratio calculation
     */
    public AbstractScalableRender(final int screenWidth, final int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResize(final int newWidth, final int newHeight) {
        this.screenRatioX = (float) newWidth / screenWidth;
        this.screenRatioY = (float) newHeight / screenHeight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getScaledWidth(final float originalWidth) {
        return originalWidth * screenRatioX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getScaledHeight(final float originalHeight) {
        return originalHeight * screenRatioY;
    }
}
