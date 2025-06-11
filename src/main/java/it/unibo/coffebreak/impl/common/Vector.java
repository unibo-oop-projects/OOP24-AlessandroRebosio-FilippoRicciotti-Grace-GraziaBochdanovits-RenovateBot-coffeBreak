package it.unibo.coffebreak.impl.common;

import java.util.Objects;

/**
 * The {@code Vector2D} class represents a mathematical vector in a 2D space
 * with x and y components.
 * This immutable record provides basic vector operations such as addition and
 * scalar multiplication,
 * and properly implements equality comparison and hash code generation.
 * 
 * @param x the x-component of the vector
 * @param y the y-component of the vector
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
     * Adds the given vector to this vector and returns the result as a new
     * Vector2D.
     *
     * @param other the vector to add
     * @return a new Vector2D representing the sum
     */
    public Vector sum(final Vector other) {
        return new Vector(this.x + other.x, this.y + other.y);
    }

    /**
     * Multiplies this vector by a scalar value, returning a new vector with each
     * component multiplied by the scalar.
     *
     * @param scalar the multiplication factor
     * @return a new {@code Vector2D} representing the scaled vector
     */
    public Vector multiply(final float scalar) {
        return new Vector(this.x * scalar, this.y * scalar);
    }

    /**
     * Returns a copy of this vector. Since {@code Vector2D} is immutable,
     * this method simply returns this instance.
     *
     * @return a copy of this vector (the same instance)
     */
    public Vector copy() {
        return new Vector(this.x, this.y);
    }

    /**
     * Compares this vector to the specified object for equality. The result is
     * {@code true}
     * if and only if:
     * <ul>
     * <li>The argument is not {@code null}</li>
     * <li>The argument is a {@code Vector2D} object</li>
     * <li>Both vectors have exactly the same x and y components</li>
     * </ul>
     * 
     * <p>
     * Note that floating-point comparison is performed using {@link Float#compare}
     * to
     * properly handle special cases like NaN and infinity values.
     *
     * @param obj the object to compare with
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Vector vector2D = (Vector) obj;
        return Float.compare(vector2D.x, x) == 0 && Float.compare(vector2D.y, y) == 0;
    }

    /**
     * Returns a hash code value for this vector. The hash code is computed based on
     * the vector's components using {@link Objects#hash}.
     *
     * @return a hash code value for this vector
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
