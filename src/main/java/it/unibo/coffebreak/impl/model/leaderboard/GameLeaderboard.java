package it.unibo.coffebreak.impl.model.leaderboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import it.unibo.coffebreak.api.model.leaderboard.Leaderboard;
import it.unibo.coffebreak.api.model.leaderboard.entry.Entry;
import it.unibo.coffebreak.api.repository.Repository;
import it.unibo.coffebreak.impl.repository.ScoreRepository;

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

    private final Repository<List<Entry>> repository = new ScoreRepository();
    private final List<Entry> entries;

    /**
     * Initializes leaderboard with provided entries, sorting and trimming to
     * capacity.
     */
    public GameLeaderboard() {
        this.entries = new ArrayList<>(this.repository.load());
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
        final boolean isAdded = this.entries.add(Objects.requireNonNull(entry, "The entry cannot be null"));
        this.entries.sort(Comparator.comparingInt(Entry::score).reversed());

        return isAdded;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save() {
        return this.repository.save(this.entries);
    }
}
