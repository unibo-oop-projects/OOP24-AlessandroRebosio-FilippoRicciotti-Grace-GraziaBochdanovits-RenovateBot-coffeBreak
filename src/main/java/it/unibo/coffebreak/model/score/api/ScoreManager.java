package it.unibo.coffebreak.model.score.api;

import java.util.List;

/**
 * Interface for managing game scores, bonuses, and leaderboard operations.
 * 
 * @param <X> the type of entries in the leaderboard.
 */
public interface ScoreManager<X> {

    /**
     * @return the current player's score
     */
    int getCurrentScore();

    /**
     * @return the current bonus value
     */
    int getCurrentBonus();

    /**
     * @return the leaderboard containing all high scores
     */
    List<X> getLeaderBoard();

    /**
     * @return the highest score from the leaderboard
     */
    int getHighestScore();

    /**
     * Calculates the current bonus based on game conditions.
     */
    void calculateBonus();

    /**
     * Adds points to the current score.
     * 
     * @param amount the number of points to add
     */
    void earnPoints(int amount);

    /**
     * Initializes bonus for a new map.
     * 
     * @param value the initial bonus value
     */
    void startMap(int value);

    /**
     * Finalizes the current map and converts bonus to points.
     */
    void endMap();

    /**
     * Finalizes the game, saves the result and reset the score.
     * 
     * @param name the player's name to add to the leaderboard
     */
    void endGame(String name);
}
