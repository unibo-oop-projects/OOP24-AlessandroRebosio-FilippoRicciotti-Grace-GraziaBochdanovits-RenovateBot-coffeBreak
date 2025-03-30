package it.unibo.coffeBreak.model.score.api;

import java.util.List;

/**
 * Represents a leaderboard that maintains a collection of entries in a ranked
 * order.
 *
 * @param <X> the type of entries maintained by this leaderboard
 */
public interface LeaderBoard<X> {

    /**
     * Retrieves an unmodifiable view of the current leaderboard entries.
     * The entries are ordered from highest to lowest score.
     *
     * @return an unmodifiable list of entries in descending order of score
     */
    List<X> getLeaderBoard();

    /**
     * Attempts to add a new entry to the leaderboard.
     * The entry will only be added if it qualifies for the leaderboard
     * (e.g., has a high enough score or there's available space).
     *
     * @param entry the entry to be added to the leaderboard
     * @throws NullPointerException if the entry is null
     */
    void addEntry(X entry);
    
}
