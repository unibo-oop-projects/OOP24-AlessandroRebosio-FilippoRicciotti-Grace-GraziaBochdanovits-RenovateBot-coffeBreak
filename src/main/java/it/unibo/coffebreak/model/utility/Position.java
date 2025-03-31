package it.unibo.coffebreak.model.utility;

/**
 * Represents a position in a 2D space with x and y coordinates.
 */
public final class Position {

    private final float x;
    private final float y;

    /**
     * Constructs a new Position with the specified coordinates.
     *
     * @param x the x-coordinate of the position.
     * @param y the y-coordinate of the position.
     */
    public Position(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retrieves the x-coordinate of the position.
     *
     * @return the x-coordinate.
     */
    public float getX() {
        return this.x;
    }

    /**
     * Retrieves the y-coordinate of the position.
     *
     * @return the y-coordinate.
     */
    public float getY() {
        return this.y;
    }

}
