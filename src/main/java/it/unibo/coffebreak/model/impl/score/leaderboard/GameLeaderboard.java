package it.unibo.coffebreak.model.impl.score.leaderboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import it.unibo.coffebreak.api.model.score.entry.Entry;
import it.unibo.coffebreak.api.model.score.leaderboard.Leaderboard;

/**
 * Basic implementation of {@link Leaderboard} maintaining top
 * {@value #MAX_ENTRIES} scores. Entries are automatically sorted in descending
 * order and trimmed to capacity when modified.
 * 
 * @author Alessandro Rebosio
 */
public class GameLeaderboard implements Leaderboard {

    /**
     * Maximum capacity of the leaderboard (currently {@value}).
     * When full, new entries must exceed the lowest score to qualify.
     */
    public static final int MAX_ENTRIES = 5;

    /** Main storage for entries, maintained in descending score order. */
    private final List<Entry> entries;

    /**
     * Initializes leaderboard with provided entries, sorting and trimming to
     * capacity.
     *
     * @param entries initial entries (null triggers NPE, empty list uses
     *                defaults)
     * @throws NullPointerException if leaderBoard is null
     * @apiNote If input contains duplicates, all will be retained until trimming
     */
    public GameLeaderboard(final List<Entry> entries) {
        Objects.requireNonNull(entries, "The list cannot be null");
        this.entries = new ArrayList<>(entries);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entry> getTopScores() {
        return this.entries.stream().limit(MAX_ENTRIES).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEntry(final Entry entry) {
        Objects.requireNonNull(entry, "The entry cannot be null");
        final boolean isAdded = this.entries.add(entry);
        this.entries.sort(Comparator.comparingInt(Entry::getScore).reversed());

        return isAdded;
    }
}
