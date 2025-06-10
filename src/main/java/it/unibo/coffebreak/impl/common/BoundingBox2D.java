package it.unibo.coffebreak.impl.common;

/**
 * Represents a 2D bounding box with a specified width and height.
 * This record is immutable and provides utility methods for scaling
 * the bounding box along the X or Y axis.
 *
 * @param width  the width of the bounding box
 * @param height the height of the bounding box
 */
public record BoundingBox2D(float width, float height) {
    /**
     * Returns a new {@code BoundingBox2D} with its width multiplied by the
     * specified factor.
     *
     * @param multiply the factor to multiply the width by
     * @return a new {@code BoundingBox2D} with the scaled width
     */
    public BoundingBox2D mulX(final int multiply) {
        return new BoundingBox2D(this.width * multiply, this.height);
    }

    /**
     * Returns a new {@code BoundingBox2D} with its height multiplied by the
     * specified factor.
     *
     * @param multiply the factor to multiply the height by
     * @return a new {@code BoundingBox2D} with the scaled height
     */
    public BoundingBox2D mulY(final int multiply) {
        return new BoundingBox2D(this.width, this.height * multiply);
    }

    /**
     * Returns a new {@code BoundingBox2D} with both its width and height multiplied
     * by the specified factor.
     *
     * @param multiply the factor to multiply both the width and height by
     * @return a new {@code BoundingBox2D} with both dimensions scaled by the given
     *         factor
     */
    public BoundingBox2D mul(final int multiply) {
        return this.mulX(multiply).mulY(multiply);
    }

    /**
     * Creates a copy of this BoundingBox2D.
     *
     * @return a new BoundingBox2D with the same width and height
     */
    public BoundingBox2D copy() {
        return new BoundingBox2D(this.width, this.height);
    }
}
