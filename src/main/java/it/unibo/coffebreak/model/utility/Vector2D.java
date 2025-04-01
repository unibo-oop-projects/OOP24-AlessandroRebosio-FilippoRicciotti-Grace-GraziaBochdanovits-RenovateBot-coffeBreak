package it.unibo.coffebreak.model.utility;

/**
 * Represents a 2D vector with x and y components.
 */
public class Vector2D {

    private double x;
    private double y;

    /**
     * Constructs a new Vector2D with the specified components.
     *
     * @param x the x-component of the vector.
     * @param y the y-component of the vector.
     */
    public Vector2D(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the x-component of the vector.
     *
     * @param x the new x-component.
     */
    public void setX(final double x) {
        this.x = x;
    }

    /**
     * Sets the y-component of the vector.
     *
     * @param y the new y-component.
     */
    public void setY(final double y) {
        this.y = y;
    }

    /**
     * Retrieves the x-component of the vector.
     *
     * @return the x-component.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Retrieves the y-component of the vector.
     *
     * @return the y-component.
     */
    public double getY() {
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
    public Vector2D multiply(final double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

}
