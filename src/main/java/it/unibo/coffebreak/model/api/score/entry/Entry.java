package it.unibo.coffebreak.model.api.score.entry;

import java.io.Serializable;

/**
 * Represents an entry in a scoreboard, containing a player name and associated
 * score.
 * Implementations should be immutable to ensure thread safety in scoreboard
 * operations.
 * 
 * @author Alessandro Rebosio
 */
public interface Entry extends Serializable {

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
