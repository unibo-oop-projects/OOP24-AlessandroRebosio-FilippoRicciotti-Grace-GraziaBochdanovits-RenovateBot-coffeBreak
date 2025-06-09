package it.unibo.coffebreak.api.model.entities.character.score;

/**
 * Manages a numeric score value that can be incremented and reset.
 * The score always maintains a non-negative value.
 * 
 * @author Alessandro Rebosio
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
