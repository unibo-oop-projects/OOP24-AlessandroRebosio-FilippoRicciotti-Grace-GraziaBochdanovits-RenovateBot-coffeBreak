package it.unibo.coffebreak.model.impl.common;

import java.util.Objects;

/**
 * The {@code Vector2D} class represents a mathematical vector in a 2D space with x and y components.
 * This immutable record provides basic vector operations such as addition and scalar multiplication,
 * and properly implements equality comparison and hash code generation.
 * 
 * @param x the x-component of the vector
 * @param y the y-component of the vector
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public record Vector2D(float x, float y) {

    /**
     * Sums another vector to this vector, returning a new vector with components equal
     * to the sum of the corresponding components of both vectors.
     *
     * @param other the vector to add to this vector (must not be null)
     * @return a new {@code Vector2D} representing the vector sum
     * @throws NullPointerException if the provided vector is null
     */
    public Vector2D sum(final Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    /**
     * Multiplies this vector by a scalar value, returning a new vector with each
     * component multiplied by the scalar.
     *
     * @param scalar the multiplication factor
     * @return a new {@code Vector2D} representing the scaled vector
     */
    public Vector2D multiply(final float scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    /**
     * Compares this vector to the specified object for equality. The result is {@code true}
     * if and only if:
     * <ul>
     *   <li>The argument is not {@code null}</li>
     *   <li>The argument is a {@code Vector2D} object</li>
     *   <li>Both vectors have exactly the same x and y components</li>
     * </ul>
     * 
     * <p>Note that floating-point comparison is performed using {@link Float#compare} to
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
        final Vector2D vector2D = (Vector2D) obj;
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
