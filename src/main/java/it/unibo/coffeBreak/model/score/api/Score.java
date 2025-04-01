package it.unibo.coffebreak.model.score.api;

/**
 * Manages a numeric score value that can be incremented and reset.
 * The score always maintains a non-negative value.
 * 
 * <p>
 * Typical usage:
 * 
 * <pre>{@code
 * Score gameScore = new GameScore();
 * gameScore.increase(100);
 * int current = gameScore.getScore();
 * }</pre>
 */
public interface Score {
    /**
     * Returns the current score value.
     * 
     * @return the current score (always ≥ 0)
     */
    int getScore();

    /**
     * Increments the score by the specified amount.
     * 
     * @param amount the positive value to add (must be ≥ 0)
     * @throws IllegalArgumentException if amount is negative
     */
    void increase(int amount);

    /**
     * Resets the score to zero.
     */
    void reset();
}
