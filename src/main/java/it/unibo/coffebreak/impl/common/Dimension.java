package it.unibo.coffebreak.impl.common;

/**
 * Represents a 2D bounding box with a specified width and height.
 * This record is immutable and provides utility methods for scaling
 * the bounding box along the X or Y axis.
 *
 * @param width  the width of the bounding box
 * @param height the height of the bounding box
 */
public record Dimension(int width, int height) {

    private static final int DEFAULT_SIZE = 25;

    /**
     * Creates a {@code Dimension} with default width and height of 25.
     */
    public Dimension() {
        this(DEFAULT_SIZE, DEFAULT_SIZE);
    }

    /**
     * Returns a new {@code Dimension} with its width multiplied by the
     * specified factor.
     *
     * @param multiply the factor to multiply the width by
     * @return a new {@code Dimension} with the scaled width
     */
    public Dimension mulWidth(final int multiply) {
        return new Dimension(this.width * multiply, this.height);
    }

    /**
     * Returns a new {@code Dimension} with its height multiplied by the
     * specified factor.
     *
     * @param multiply the factor to multiply the height by
     * @return a new {@code Dimension} with the scaled height
     */
    public Dimension mulHeight(final int multiply) {
        return new Dimension(this.width, this.height * multiply);
    }

    /**
     * Returns a new {@code Dimension} with both its width and height multiplied
     * by the specified factor.
     *
     * @param multiply the factor to multiply both the width and height by
     * @return a new {@code Dimension} with both dimensions scaled by the given
     *         factor
     */
    public Dimension mul(final int multiply) {
        return this.mulWidth(multiply).mulHeight(multiply);
    }

    /**
     * Creates a copy of this Dimension.
     *
     * @return a new Dimension with the same width and height
     */
    public Dimension copy() {
        return new Dimension(this.width, this.height);
    }
}
