package it.unibo.coffebreak.model.impl.score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import it.unibo.coffebreak.model.api.score.Entry;
import it.unibo.coffebreak.model.api.score.LeaderBoard;

/**
 * Basic implementation of {@link LeaderBoard} maintaining top
 * {@value #MAX_ENTRIES} scores. Entries are automatically sorted in descending
 * order and trimmed to capacity when modified.
 * 
 * @author Alessandro Rebosio
 */
public class GameLeaderBoard implements LeaderBoard<Entry> {

    /**
     * Maximum capacity of the leaderboard (currently {@value}).
     * When full, new entries must exceed the lowest score to qualify.
     */
    public static final int MAX_ENTRIES = 5;

    /** Main storage for entries, maintained in descending score order. */
    private final List<Entry> leaderBoard;

    /**
     * Initializes leaderboard with provided entries, sorting and trimming to
     * capacity.
     *
     * @param leaderBoard initial entries (null triggers NPE, empty list uses
     *                    defaults)
     * @throws NullPointerException if leaderBoard is null
     * @apiNote If input contains duplicates, all will be retained until trimming
     */
    public GameLeaderBoard(final List<Entry> leaderBoard) {
        Objects.requireNonNull(leaderBoard, "The list cannot be null");
        this.leaderBoard = new ArrayList<>(leaderBoard);
        this.sortAndTrim();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entry> getLeaderBoard() {
        return Collections.unmodifiableList(this.leaderBoard);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEntry(final Entry entry) {
        Objects.requireNonNull(entry, "The entry cannot be null");

        if (!this.isEligible(entry)) {
            return false;
        }

        this.leaderBoard.add(entry);
        this.sortAndTrim();
        return true;
    }

    /**
     * Determines if an entry qualifies for inclusion in the leaderboard.
     * 
     * @param entry The entry to evaluate (must not be null)
     * @return true if either:
     *         <ul>
     *         <li>Leaderboard has available capacity (empty slots), OR</li>
     *         <li>Entry's score exceeds the current lowest non-empty score</li>
     *         </ul>
     * @throws NullPointerException if entry is null
     */
    private boolean isEligible(final Entry entry) {
        Objects.requireNonNull(entry, "Entry cannot be null");

        return this.leaderBoard.size() < MAX_ENTRIES
                || entry.compareTo(this.leaderBoard.getLast()) < 0;
    }

    /**
     * Sorts entries in descending order and enforces capacity limit.
     */
    private void sortAndTrim() {
        this.leaderBoard.sort(Entry::compareTo);

        if (this.leaderBoard.size() > MAX_ENTRIES) {
            this.leaderBoard.subList(MAX_ENTRIES, this.leaderBoard.size()).clear();
        }
    }
}
