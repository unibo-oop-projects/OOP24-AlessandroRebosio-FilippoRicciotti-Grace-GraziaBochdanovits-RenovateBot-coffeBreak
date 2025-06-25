package it.unibo.coffebreak.impl.common;

/**
 * Represents a 2D bounding box with a specified width and height.
 * This immutable record provides utility methods for scaling
 * the bounding box along the X or Y axis.
 *
 * @param width  the width of the bounding box
 * @param height the height of the bounding box
 * 
 * @author Alessadro Rebosio
 */
public record Dimension(int width, int height) {
    /**
     * Returns a new {@code Dimension} with its width multiplied by the
     * specified factor.
     *
     * @param factor the factor to multiply the width by
     * @return a new {@code Dimension} with the scaled width
     */
    public Dimension scaleWidth(final int factor) {
        return new Dimension(this.width * factor, this.height);
    }

    /**
     * Returns a new {@code Dimension} with its height multiplied by the
     * specified factor.
     *
     * @param factor the factor to multiply the height by
     * @return a new {@code Dimension} with the scaled height
     */
    public Dimension scaleHeight(final int factor) {
        return new Dimension(this.width, this.height * factor);
    }

    /**
     * Returns a new {@code Dimension} with both its width and height multiplied
     * by the specified factor.
     *
     * @param factor the factor to multiply both the width and height by
     * @return a new {@code Dimension} with both dimensions scaled by the given
     *         factor
     */
    public Dimension scale(final int factor) {
        return new Dimension(this.width * factor, this.height * factor);
    }

    /**
     * Creates a copy of this {@code Dimension}.
     *
     * @return a new {@code Dimension} with the same width and height
     */
    public Dimension copy() {
        return new Dimension(this.width, this.height);
    }
}
