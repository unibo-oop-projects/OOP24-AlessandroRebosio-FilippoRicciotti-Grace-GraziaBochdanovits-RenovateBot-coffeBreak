package it.unibo.coffeBreak.model.score.api;

/**
 * Represents a bonus system that can be applied to a game.
 * Provides methods to get, set and calculate the bonus value.
 */
public interface Bonus {

    /**
     * Gets the current bonus value.
     * 
     * @return the current bonus value
     */
    int getBonus();

    /**
     * Sets a new bonus value.
     * 
     * @param value the new bonus value to set (must be positive)
     * @throws IllegalArgumentException if the value is negative
     */
    void setBonus(int value);

    /**
     * Calculates and updates the bonus value according to implementation rules.
     * Typically reduces the bonus by a fixed amount.
     */
    void calculate();
}
