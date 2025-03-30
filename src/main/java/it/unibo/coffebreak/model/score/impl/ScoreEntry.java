package it.unibo.coffebreak.model.score.impl;

import java.io.Serializable;
import java.util.Objects;

import it.unibo.coffebreak.model.score.api.Entry;

/**
 * Concrete implementation of an {@link Entry} that represents a score entry
 * with a player name and score value. This class is immutable and thread-safe.
 */
public class ScoreEntry implements Entry, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The name of the player associated with this score entry.
     * Cannot be null and is treated in a case-sensitive manner.
     */
    private final String name;

    /**
     * The numeric score value of this entry.
     * Higher values indicate better performance.
     */
    private final int score;

    /**
     * Creates a new ScoreEntry with the specified name and score.
     * 
     * @param name  the player name (cannot be null)
     * @param score the score value
     * @throws NullPointerException if name is null
     */
    public ScoreEntry(final String name, final int score) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.score = score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScore() {
        return this.score;
    }

    /**
     * Compares this entry with another entry based on score (descending order).
     * 
     * @param o the entry to be compared
     * @return a negative integer, zero, or a positive integer if this entry's score
     *         is greater than, equal to, or less than the specified entry's score
     * @throws NullPointerException if the specified entry is null
     */
    @Override
    public int compareTo(final Entry o) {
        return Integer.compare(Objects.requireNonNull(o).getScore(), this.score);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final ScoreEntry that = (ScoreEntry) obj;
        return this.score == that.score && Objects.equals(this.name, that.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.score);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new StringBuilder("ScoreEntry[")
                .append("name=").append(this.name)
                .append(", score=").append(this.score)
                .append(']')
                .toString();
    }

}
