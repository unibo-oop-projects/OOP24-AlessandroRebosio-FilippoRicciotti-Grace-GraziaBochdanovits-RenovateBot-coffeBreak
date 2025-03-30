package it.unibo.coffeBreak.model.score.api;

/**
 * Represents an entry in a scoreboard, containing a name and a score.
 * Entries can be compared based on their score values (higher scores come
 * first).
 */
public interface Entry extends Comparable<Entry> {

    /**
     * Gets the name associated with this entry.
     * 
     * @return the name of the entry
     */
    String getName();

    /**
     * Gets the score value of this entry.
     * 
     * @return the score value
     */
    int getScore();
    
}
