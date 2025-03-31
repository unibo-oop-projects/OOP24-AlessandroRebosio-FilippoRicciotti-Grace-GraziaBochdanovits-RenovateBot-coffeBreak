package it.unibo.coffebreak.model.score.api;

import java.util.List;

/**
 * Represents a leaderboard that maintains a collection of entries in ranked
 * order.
 * The leaderboard typically keeps track of top scores and provides methods to
 * add new entries and retrieve the current ranking.
 *
 * @param <X> the type of entries maintained by this leaderboard, must implement
 *            comparable or provide a way to determine ranking
 */
public interface LeaderBoard<X> {

    /**
     * Retrieves an unmodifiable view of the current leaderboard entries.
     * The entries are ordered from highest to lowest score/ranking.
     *
     * @return an unmodifiable list of entries in descending order of score,
     *         empty list if no entries are present
     */
    List<X> getLeaderBoard();

    /**
     * Attempts to add a new entry to the leaderboard.
     * The entry will only be added if it qualifies based on the leaderboard's
     * criteria (e.g., has a high enough score or there's available space).
     * Implementations may have size limits or minimum score requirements.
     *
     * @param entry the entry to be added to the leaderboard
     * @throws NullPointerException if the entry is null
     */
    void addEntry(X entry);

    /**
     * Checks if the leaderboard has been modified since the last check.
     * This is typically used to determine if the leaderboard needs to be persisted
     * or if changes need to be processed further.
     *
     * @return {@code true} if the leaderboard was modified since creation or
     *         since the last time this flag was checked, {@code false} otherwise.
     *         After this call, the flag should be reset to {@code false}.
     */
    boolean isWritten();
}
