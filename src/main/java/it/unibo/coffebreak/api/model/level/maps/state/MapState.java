package it.unibo.coffebreak.api.model.level.maps.state;

import java.util.List;

import it.unibo.coffebreak.api.model.entities.Entity;

/**
 * Represents the state of a game map, providing access to map data,
 * advancement conditions, and the resource path for the map file.
 * 
 * @author Filippo Ricciotti
 */
public interface MapState {
    /**
     * Returns the list of strings representing the map layout.
     * 
     * @return the map data as a list of strings
     */
    List<String> currentMap();

    /**
     * Determines if the level should advance based on the current entities.
     * 
     * @param entities the list of entities in the level
     * @return true if the level should advance, false otherwise
     */
    boolean shouldAdvance(List<Entity> entities);

    /**
     * Returns the index of this map in the sequence of levels.
     * This can be used to identify or order maps within the game.
     *
     * @return the index of the map
     */
    int getIndex();
}
