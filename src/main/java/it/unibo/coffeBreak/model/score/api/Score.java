package it.unibo.coffeBreak.model.score.api;

/**
 * Represents a score system that can be increased, reset and queried.
 * This interface defines the basic operations for managing a game score.
 */
public interface Score {

    /**
     * Gets the current score value.
     * 
     * @return the current score as an integer
     */
    int getScore();

    /**
     * Increases the current score by the specified amount.
     * 
     * @param amount the positive value to add to the current score
     * @throws IllegalArgumentException if the amount is negative
     */
    void increase(int amount);

    /**
     * Resets the score to zero.
     */
    void reset();

}
