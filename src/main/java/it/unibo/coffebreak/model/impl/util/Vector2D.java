package it.unibo.coffebreak.model.impl.util;

import java.util.Objects;

/**
 * Represents a 2D vector with x and y components.
 */
public class Vector2D {

    private float x;
    private float y;

    /**
     * Constructs a new Vector2D with the specified components.
     *
     * @param x the x-component of the vector.
     * @param y the y-component of the vector.
     */
    public Vector2D(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the x-component of the vector.
     *
     * @param x the new x-component.
     */
    public void setX(final float x) {
        this.x = x;
    }

    /**
     * Sets the y-component of the vector.
     *
     * @param y the new y-component.
     */
    public void setY(final float y) {
        this.y = y;
    }

    /**
     * Retrieves the x-component of the vector.
     *
     * @return the x-component.
     */
    public float getX() {
        return this.x;
    }

    /**
     * Retrieves the y-component of the vector.
     *
     * @return the y-component.
     */
    public float getY() {
        return this.y;
    }

    /**
     * Adds another vector to this vector.
     *
     * @param other the vector to add.
     * @return the resulting vector.
     */
    public Vector2D add(final Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    /**
     * Subtracts another vector from this vector.
     *
     * @param other the vector to subtract.
     * @return the resulting vector.
     */
    public Vector2D subtract(final Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    /**
     * Multiplies this vector by a scalar.
     *
     * @param scalar the scalar to multiply by.
     * @return the resulting vector.
     */
    public Vector2D multiply(final float scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    /**
     * Compares this vector to the specified object for equality.
     * Returns true if and only if:
     * <ul>
     *   <li>The argument is not null</li>
     *   <li>The argument is a Vector2D object</li>
     *   <li>Both vectors have exactly the same x and y components</li>
     * </ul>
     * 
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
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
     * Returns a hash code value for this vector.
     * 
     * @return a hash code value based on the vector's components
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
