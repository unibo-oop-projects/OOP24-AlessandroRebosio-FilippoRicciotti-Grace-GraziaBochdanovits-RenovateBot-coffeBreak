package it.unibo.coffebreak.impl.model.leaderboard.entry;

import java.util.Objects;

import it.unibo.coffebreak.api.model.leaderboard.entry.Entry;

/**
 * Immutable implementation of {@link Entry} representing a player's score
 * entry.
 * Natural ordering is based on score (descending), with name used for
 * tie-breaking in equality checks.
 * 
 * @param name  the player name
 * @param score the score made by player
 * 
 * @see Comparable
 * @see Serializable
 * 
 * @author Alessandro Rebosio
 */
public record ScoreEntry(String name, int score) implements Entry {

    /** The serial version UID for consistent serialization/deserialization. */
    private static final long serialVersionUID = 1L;

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
