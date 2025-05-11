package it.unibo.coffebreak.model.api.entities.ladder;

import it.unibo.coffebreak.model.api.entities.Entity;

/**
 * Represents a ladder that the player can climb to move between platforms.
 * 
 * @author Alessandro Rebosio
 */
public interface Ladder extends Entity {

    /**
     * @return true if this ladder can be currently climbed (e.g., not blocked by an
     *         enemy)
     */
    boolean isClimbable();
}
