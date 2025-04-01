package it.unibo.coffebreak.model.score.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.unibo.coffebreak.model.score.api.Entry;
import it.unibo.coffebreak.model.score.api.LeaderBoard;

/**
 * Implementation of an optimized game leaderboard that maintains the top
 * scoring entries.
 * The leaderboard has a maximum capacity and entries are automatically sorted
 * in descending order of score.
 */
public class GameLeaderBoard implements LeaderBoard<Entry> {

    /** The maximum number of entries allowed in the leaderboard. */
    public static final int MAX_ENTRIES = 5;

    /**
     * The list containing the leaderboard entries.
     * Maintained in descending order based on score.
     */
    private final List<Entry> leaderBoard;

    /** Flag to track if the leaderboard was modified. */
    private final AtomicBoolean isModified = new AtomicBoolean(false);

    /**
     * Constructs an empty leaderboard pre-sized to maximum capacity.
     */
    public GameLeaderBoard() {
        this.leaderBoard = createDefaultEntries();
    }

    /**
     * Constructs a leaderboard initialized with the provided entries.
     * The entries will be sorted and trimmed to the maximum capacity.
     *
     * @param leaderBoard the initial list of entries
     * @throws NullPointerException if the provided list is null
     */
    public GameLeaderBoard(final List<Entry> leaderBoard) {
        Objects.requireNonNull(leaderBoard, "The list cannot be null");
        this.leaderBoard = leaderBoard.isEmpty()
                ? createDefaultEntries()
                : leaderBoard;
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
    public void addEntry(final Entry entry) {
        Objects.requireNonNull(entry);

        if (this.isEligible(entry)) {
            this.leaderBoard.add(entry);
            this.sortAndTrim();
            this.isModified.set(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWritten() {
        return this.isModified.getAndSet(false);
    }

    /**
     * Creates and returns a list of default {@link Entry} instances initialized
     * with empty names and zero scores.
     * The list is pre-sized to the maximum capacity of the leaderboard
     * ({@link #MAX_ENTRIES}) to optimize memory allocation.
     * 
     * @return a new mutable {@link List} containing {@code MAX_ENTRIES} default
     *         entries, where each entry has:
     *         <ul>
     *         <li>An empty string ("") as the name</li>
     *         <li>Zero (0) as the score</li>
     *         </ul>
     * @implNote This implementation uses {@link IntStream} to generate the entries,
     *           which provides
     *           clear intent for creating a sequence of default values. The
     *           collector explicitly
     *           specifies the initial capacity for optimal memory usage.
     */
    private List<Entry> createDefaultEntries() {
        return IntStream.range(0, MAX_ENTRIES)
                .mapToObj(i -> new ScoreEntry("", 0))
                .collect(Collectors.toList());
    }

    /**
     * Checks if an entry is eligible to be added to the leaderboard.
     * An entry is eligible if:
     * - The leaderboard isn't full, or
     * - The entry has a higher score than the lowest score in the leaderboard
     *
     * @param entry the entry to check
     * @return true if the entry is eligible, false otherwise
     */
    private boolean isEligible(final Entry entry) {
        return this.leaderBoard.size() < MAX_ENTRIES
                || entry.compareTo(this.leaderBoard.getLast()) < 0;
    }

    /**
     * Sorts the leaderboard in descending order and trims it to the maximum allowed
     * size.
     * Uses reverse order comparator for better readability.
     */
    private void sortAndTrim() {
        this.leaderBoard.sort(Entry::compareTo);

        if (this.leaderBoard.size() > MAX_ENTRIES) {
            this.leaderBoard.subList(MAX_ENTRIES, this.leaderBoard.size()).clear();
        }
    }
}
