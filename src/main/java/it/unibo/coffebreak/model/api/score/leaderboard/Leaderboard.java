package it.unibo.coffebreak.model.api.score.leaderboard;

import java.util.List;

import it.unibo.coffebreak.model.api.score.entry.Entry;

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
     * Attempts to add a new entry to the leaderboard if it meets ranking criteria.
     * Specific conditions for eligibility are implementation-dependent (e.g.,
     * minimum score
     * or available capacity). Duplicate entries may or may not be allowed depending
     * on
     * implementation.
     *
     * @param entry the entry to add, must not be null
     * @throws NullPointerException if entry is null
     * 
     * @return a boolean, if it is true the element was added, otherwise false
     */
    boolean addEntry(Entry entry);
}
