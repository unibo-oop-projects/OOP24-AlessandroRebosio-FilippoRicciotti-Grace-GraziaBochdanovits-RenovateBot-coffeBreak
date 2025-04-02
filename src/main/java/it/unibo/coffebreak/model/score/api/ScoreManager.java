package it.unibo.coffebreak.model.score.api;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Manages all scoring-related operations for a game, including.
 * <ul>
 * <li>Player score tracking</li>
 * <li>Bonus calculation and conversion</li>
 * <li>Leaderboard management</li>
 * <li>Game session lifecycle</li>
 * </ul>
 * 
 * @param <X> the type of entries stored in the leaderboard, must implement
 *            {@link Entry}
 * 
 * @author Alessandro Rebosio
 */
public interface ScoreManager<X> {

    /**
     * Gets the player's current accumulated score.
     * 
     * @return the current score value (always ≥ 0)
     */
    int getCurrentScore();

    /**
     * Gets the current convertible bonus value.
     * 
     * @return the current bonus value (always ≥ 0)
     */
    int getCurrentBonus();

    /**
     * Gets an immutable view of the leaderboard entries.
     * 
     * @return list of entries sorted in descending score order (never null)
     */
    List<X> getLeaderBoard();

    /**
     * Gets the highest score from the leaderboard.
     * 
     * @return the top score value (0 if leaderboard is empty)
     * @throws NoSuchElementException if leaderboard is empty
     */
    int getHighestScore();

    /**
     * Updates the bonus value according to game-specific rules.
     * Typically reduces the bonus over time.
     */
    void calculateBonus();

    /**
     * Adds points to the current score.
     * 
     * @param amount the positive value to add (must be ≥ 0)
     * @throws IllegalArgumentException if amount is negative
     */
    void earnPoints(int amount);

    /**
     * Initializes bonus for a new game map/level.
     * 
     * @param value the starting bonus value (must be ≥ 0)
     * @throws IllegalArgumentException if value is negative
     */
    void startMap(int value);

    /**
     * Converts remaining bonus to points and completes the current map/level.
     * Equivalent to calling {@code earnPoints(getCurrentBonus())}.
     */
    void endMap();

    /**
     * Finalizes the game session and saves results.
     * 
     * @param name the player name for leaderboard (cannot be null or blank)
     * @throws NullPointerException     if name is null
     * @throws IllegalArgumentException if name is blank
     */
    void endGame(String name);
}
