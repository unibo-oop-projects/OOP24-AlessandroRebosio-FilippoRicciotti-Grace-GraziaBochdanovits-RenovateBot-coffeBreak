package it.unibo.coffebreak.impl.common;

import java.util.Objects;

/**
 * Represents a position in a 2D space with x and y coordinates.
 *
 * @param x the x-coordinate of the position.
 * @param y the y-coordinate of the position.
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public record Position(float x, float y) {
    /**
     * Sums this position with another position, creating a new Position2D instance
     * with coordinates equal to the sum of the corresponding coordinates.
     *
     * @param vector the vector to add to this position
     * @return a new Position2D representing the sum of the two positions
     * @throws NullPointerException if the provided position is null
     */
    public Position sum(final Vector vector) {
        Objects.requireNonNull(vector, "The vector cannot be null");
        return new Position(this.x + vector.x(), this.y + vector.y());
    }

    /**
     * Creates a copy of this Position2D instance.
     *
     * @return a new Position2D with the same x and y coordinates as this instance
     */
    public Position copy() {
        return new Position(this.x, this.y);
    }
}
