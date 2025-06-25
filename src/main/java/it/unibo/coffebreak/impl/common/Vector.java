package it.unibo.coffebreak.impl.common;

import java.util.Objects;

/**
 * The {@code Vector} class represents a mathematical vector in 2D space
 * with {@code x} and {@code y} components.
 * This immutable record provides basic vector operations such as addition and
 * scalar multiplication, and properly implements equality and hash code.
 *
 * @param x the x component of the vector
 * @param y the y component of the vector
 *
 * @author Grazia Bochdanovits de Kavna
 */
public record Vector(float x, float y) {

    /**
     * Constructs a zero vector (0, 0).
     */
    public Vector() {
        this(0.0f, 0.0f);
    }

    /**
     * Adds the specified vector to this vector and returns the result as a new
     * {@code Vector}.
     *
     * @param vector the vector to add
     * @return a new {@code Vector} representing the sum
     * @throws NullPointerException if {@code vector} is null
     */
    public Vector sum(final Vector vector) {
        Objects.requireNonNull(vector, "The vector to add cannot be null");
        return new Vector(this.x + vector.x, this.y + vector.y);
    }

    /**
     * Multiplies this vector by a scalar value, returning a new vector with each
     * component multiplied by the scalar.
     *
     * @param factor the multiplication factor
     * @return a new {@code Vector} representing the scaled vector
     */
    public Vector mul(final float factor) {
        return new Vector(this.x * factor, this.y * factor);
    }

    /**
     * Returns a copy of this vector. Since {@code Vector} is immutable,
     * this method simply returns a new instance with the same values.
     *
     * @return a copy of this vector (a new instance)
     */
    public Vector copy() {
        return new Vector(this.x, this.y);
    }
}
