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

    /**
     * Constructs an AbstractScalableRender with the specified original dimensions.
     *
     * @param originalWidth the original width of the render
     * @param originalHeight the original height of the render
     */
    public AbstractScalableRender(final int originalWidth, final int originalHeight) {
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
        this.scaledWidth = originalWidth;
        this.scaledHeight = originalHeight;
    }

    /**
     * Handles the resize event and updates the current dimensions.
     *
     * @param newWidth the new width after resizing
     * @param newHeight the new height after resizing
     */
    @Override
    public void onResize(final int newWidth, final int newHeight) {
        this.scaledWidth = newWidth;
        this.scaledHeight = newHeight;
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
