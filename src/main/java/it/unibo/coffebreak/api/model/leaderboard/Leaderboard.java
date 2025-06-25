package it.unibo.coffebreak.api.model.leaderboard;

import java.util.List;

import it.unibo.coffebreak.api.model.leaderboard.entry.Entry;

/**
 * Represents a ranked collection of {@link Entry}, typically used to track top
 * scores in a game.
 * <p>
 * The leaderboard maintains entries in descending order of their score/ranking.
 * Implementations may impose additional constraints like maximum capacity or
 * minimum score requirements.
 * 
 * @author Alessandro Rebosio
 */
public interface Leaderboard {
    /**
     * Returns an unmodifiable view of entries ranked from highest to lowest.
     * The returned list is a snapshot and won't reflect subsequent modifications.
     *
     * @return immutable list of entries in descending order, never null but may be
     *         empty
     */
    List<Entry> getTopScores();

    /**
     * Returns the highest score currently present in the leaderboard.
     *
     * @return the top score as an integer
     */
    int getTopScore();

    /**
     * Attempts to add a new entry to the leaderboard if it meets ranking criteria.
     * <p>
     * Specific conditions for eligibility are implementation-dependent
     * (e.g., minimum score or available capacity).
     * Duplicate entries may or may not be allowed depending on the implementation.
     * </p>
     *
     * @param entry the entry to add, must not be null
     * @return true if the entry was successfully added, false otherwise
     * @throws NullPointerException if entry is null
     */
    boolean addEntry(Entry entry);

    /**
     * Persists the leaderboard data to a stable storage medium.
     * <p>
     * Implementations may choose to store data to disk, cloud storage, or any
     * other backend.
     * </p>
     * 
     * @return true if the save operation was successful, false otherwise
     */
    boolean save();
}
