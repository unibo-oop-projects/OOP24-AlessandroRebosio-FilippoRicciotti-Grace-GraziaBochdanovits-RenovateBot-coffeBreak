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
public record BoundigBox(int width, int height) {

    /**
     * Constructs a {@code BoundigBox} with width and height set to 8.
     */
    public BoundigBox() {
        this(8, 8);
    }

    /**
     * Returns a new {@code BoundigBox} with its width multiplied by the
     * specified factor.
     *
     * @param factor the factor to multiply the width by
     * @return a new {@code BoundigBox} with the scaled width
     */
    public BoundigBox scaleWidth(final int factor) {
        return new BoundigBox(this.width * factor, this.height);
    }

    /**
     * Returns a new {@code BoundigBox} with its height multiplied by the
     * specified factor.
     *
     * @param factor the factor to multiply the height by
     * @return a new {@code BoundigBox} with the scaled height
     */
    public BoundigBox scaleHeight(final int factor) {
        return new BoundigBox(this.width, this.height * factor);
    }

    /**
     * Returns a new {@code BoundigBox} with both its width and height multiplied
     * by the specified factor.
     *
     * @param factor the factor to multiply both the width and height by
     * @return a new {@code BoundigBox} with both BoundigBoxs scaled by the given
     *         factor
     */
    public BoundigBox scale(final int factor) {
        return new BoundigBox(this.width * factor, this.height * factor);
    }

    /**
     * Creates a copy of this {@code BoundigBox}.
     *
     * @return a new {@code BoundigBox} with the same width and height
     */
    public BoundigBox copy() {
        return new BoundigBox(this.width, this.height);
    }
}
