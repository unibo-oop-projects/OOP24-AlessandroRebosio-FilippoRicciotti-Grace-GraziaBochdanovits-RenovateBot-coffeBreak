package it.unibo.coffebreak.api.view.renders;

/**
 * Marks a render as scalable and provides methods to handle resizing.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public interface ScalableRender {
    /**
     * Called when the container is resized.
     * @param newWidth new width
     * @param newHeight new height
     */
    void onResize(int newWidth, int newHeight);

    /**
     * Calculates and returns the scaled width based on the original dimension.
     *
     * @param originalWidth the original width to be scaled
     * @return the scaled width in pixels
     */
    float getScaledWidth(float originalWidth);

    /**
     * Calculates and returns the scaled height based on the original dimension.
     *
     * @param originalHeight the original height to be scaled
     * @return the scaled height in pixels
     */
    float getScaledHeight(float originalHeight);
}
