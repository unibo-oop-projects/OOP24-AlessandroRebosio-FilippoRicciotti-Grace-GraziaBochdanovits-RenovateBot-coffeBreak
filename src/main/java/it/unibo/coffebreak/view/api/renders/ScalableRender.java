package it.unibo.coffebreak.view.api.renders;

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
     * Gets the current scaled width.
     * @return the width scaled
     */
    int getScaledWidth();

    /**
     * Gets the origina width.
     * @return the original width
     */
    int getOriginalWidth();

    /**
     * Gets the current scaled height.
     * @return the height scaled
     */
    int getScaledHeight();

    /**
     * Gets the original height.
     * @return the original height
     */
    int getOriginalHeight();
}
