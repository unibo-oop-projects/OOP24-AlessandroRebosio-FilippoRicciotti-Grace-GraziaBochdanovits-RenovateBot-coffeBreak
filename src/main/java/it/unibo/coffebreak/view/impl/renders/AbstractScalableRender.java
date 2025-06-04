package it.unibo.coffebreak.view.impl.renders;

import it.unibo.coffebreak.view.api.renders.ScalableRender;

/**
 * An abstract implementation of {@link ScalableRender} that provides basic functionality
 * for scaling renders while optionally maintaining the aspect ratio.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public abstract class AbstractScalableRender implements ScalableRender {

    private final int originalWidth;
    private final int originalHeight;
    private int scaledWidth;
    private int scaledHeight;
    private final float screenWidthRatio;
    private final float screenHeightRatio;

    /**
     * Constructs an AbstractScalableRender with the specified original dimensions.
     *
     * @param originalWidth the original width of the render
     * @param originalHeight the original height of the render
     * @param screenWidth the total screen width used for ratio calculation
     * @param screenHeight the total screen height used for ratio calculation
     */
    public AbstractScalableRender(final int originalWidth, final int originalHeight, 
                                final int screenWidth, final int screenHeight) {
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
        this.scaledWidth = originalWidth;
        this.scaledHeight = originalHeight;
        this.screenWidthRatio = (float) originalWidth / screenWidth;
        this.screenHeightRatio = (float) originalHeight / screenHeight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResize(final int newWidth, final int newHeight) {
        this.scaledWidth = (int) (newWidth * screenWidthRatio);
        this.scaledHeight = (int) (newHeight * screenHeightRatio);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScaledWidth() {
        return this.scaledWidth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScaledHeight() {
        return this.scaledHeight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOriginalWidth() {
        return this.originalWidth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOriginalHeight() {
        return this.originalHeight;
    }
}
