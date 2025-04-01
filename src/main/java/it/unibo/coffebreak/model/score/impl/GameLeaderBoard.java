package it.unibo.coffebreak.model.score.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.unibo.coffebreak.model.score.api.Entry;
import it.unibo.coffebreak.model.score.api.LeaderBoard;

/**
 * Thread-safe implementation of {@link LeaderBoard} maintaining top
 * {@value #MAX_ENTRIES} scores.
 * Entries are automatically sorted in descending order and trimmed to capacity
 * when modified.
 * The implementation uses atomic operations for thread safety and includes:
 * <ul>
 * <li>Pre-allocated storage for memory efficiency</li>
 * <li>Automatic trimming of excess entries</li>
 * <li>Modification tracking via atomic flag</li>
 * </ul>
 * 
 * <p>
 * <b>Implementation Requirements:</b>
 * <ul>
 * <li>Mutable but thread-safe for concurrent access</li>
 * <li>Not persistent - contents are lost after JVM shutdown</li>
 * <li>Case-sensitive for entry names</li>
 * </ul>
 */
public class GameLeaderBoard implements LeaderBoard<Entry> {

    /**
     * Maximum capacity of the leaderboard (currently {@value}).
     * When full, new entries must exceed the lowest score to qualify.
     */
    public static final int MAX_ENTRIES = 5;

    /**
     * Main storage for entries, maintained in descending score order.
     * 
     * @implNote The list implementation is:
     *           <ul>
     *           <li>Mutable for internal operations</li>
     *           <li>Wrapped in unmodifiable view for public access</li>
     *           <li>Initialized with default entries on construction</li>
     *           </ul>
     */
    private final List<Entry> leaderBoard;

    /**
     * Tracks whether unsaved changes exist.
     * 
     * @implNote AtomicBoolean ensures thread-safe check-and-reset operations
     */
    private final AtomicBoolean isModified = new AtomicBoolean(false);

    /**
     * Creates a leaderboard pre-filled with default entries (empty names, zero
     * scores).
     */
    public GameLeaderBoard() {
        this.leaderBoard = createDefaultEntries();
    }

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
     * Generates default placeholder entries for new leaderboards.
     *
     * @return mutable list containing {@value #MAX_ENTRIES} entries with:
     *         <ul>
     *         <li>Empty string ("") as name</li>
     *         <li>Zero (0) as score</li>
     *         </ul>
     * @implSpec The implementation:
     *           <ul>
     *           <li>Pre-allocates exact capacity for memory efficiency</li>
     *           <li>Uses {@link ScoreEntry} as concrete implementation</li>
     *           </ul>
     */
    private List<Entry> createDefaultEntries() {
        return IntStream.range(0, MAX_ENTRIES)
                .mapToObj(i -> new ScoreEntry("", 0))
                .collect(Collectors.toCollection(() -> new ArrayList<>(MAX_ENTRIES)));
    }

    /**
     * Determines if an entry qualifies for inclusion in the leaderboard.
     *
     * @param entry the entry to evaluate
     * @return true if either:
     *         <ul>
     *         <li>Leaderboard has available capacity, OR</li>
     *         <li>Entry's score exceeds the current lowest score</li>
     *         </ul>
     * @implNote Comparison uses {@link Entry#compareTo} for consistency with
     *           sorting
     */
    private boolean isEligible(final Entry entry) {
        return this.leaderBoard.size() < MAX_ENTRIES
                || entry.compareTo(this.leaderBoard.getLast()) < 0;
    }

    /**
     * Sorts entries in descending order and enforces capacity limit.
     * 
     * @implNote Operation sequence:
     *           <ol>
     *           <li>Sorts using natural ordering (highest first)</li>
     *           <li>Discards excess entries beyond {@value #MAX_ENTRIES}</li>
     *           </ol>
     */
    private void sortAndTrim() {
        this.leaderBoard.sort(Entry::compareTo);

        if (this.leaderBoard.size() > MAX_ENTRIES) {
            this.leaderBoard.subList(MAX_ENTRIES, this.leaderBoard.size()).clear();
        }
    }
}
