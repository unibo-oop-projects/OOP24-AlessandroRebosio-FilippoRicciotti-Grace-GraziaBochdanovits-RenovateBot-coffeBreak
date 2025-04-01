package it.unibo.coffebreak.model.score.impl;

import java.io.Serializable;
import java.util.Objects;

import it.unibo.coffebreak.model.score.api.Entry;

/**
 * Immutable implementation of {@link Entry} representing a player's score
 * entry.
 * This class is thread-safe and serializable, suitable for storage and network
 * transfer.
 * Natural ordering is based on score (descending), with name used for
 * tie-breaking in equality checks.
 * 
 * @see Comparable
 * @see Serializable
 * 
 * @autor Alessandro Rebosio
 */
public class ScoreEntry implements Entry, Serializable {
    /**
     * The serial version UID for consistent serialization/deserialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The player's name for this entry.
     * 
     * @implSpec This field is:
     *           - final (immutable)
     *           - case-sensitive
     *           - never null (validated in constructor)
     */
    private final String name;

    /**
     * The numeric score value.
     * 
     * @implSpec Higher values represent better performance. The field is final
     *           (immutable).
     */
    private final int score;

    /**
     * Creates a new immutable score entry.
     * 
     * @param name  the player's name (must not be null)
     * @param score the score value (any integer)
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
     * Compares entries by score in descending order.
     * 
     * @implNote Consistent with equals as required by {@link Comparable}
     * @throws NullPointerException if the argument is null
     */
    @Override
    public int compareTo(final Entry o) {
        return Integer.compare(Objects.requireNonNull(o).getScore(), this.score);
    }

    /**
     * Tests for equality based on both name and score.
     * 
     * @return true if the other object is a ScoreEntry with same name and score
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
     * Returns a hash code combining name and score.
     * 
     * @return hash code value consistent with equals
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.score);
    }

    /**
     * Returns a string representation in format. "ScoreEntry[name=X, score=Y]"
     * 
     * @return descriptive string representation
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
