package it.unibo.coffebreak.api.model.level.maps;

import java.util.List;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.level.maps.state.MapState;

/**
 * Interface for managing the sequence and logic of game maps (levels).
 * Provides methods to access the current map, add new maps, and determine
 * if the level should advance based on the current entities.
 * 
 * @author Filippo Ricciotti
 */
public interface MapsManager {
    /**
     * Returns the list of strings representing the current map layout.
     * 
     * @return the current map data as a list of strings
     */
    List<String> currentMap();

    /**
     * Advances the map index if possible, or loops back to the first map if at the
     * end.
     * Optionally, can be used to add new maps if more levels are required.
     *
     * @param entities the list of entities in the level
     * @return true if the map index was advanced, false if it looped or could not
     *         advance
     */
    boolean advance(List<Entity> entities);

    /**
     * Adds a new map to the sequence of maps.
     * 
     * @param mapToAdd the map state to add
     * @return true if the map was added successfully, false otherwise
     */
    boolean addMap(MapState mapToAdd);

    /**
     * Determines if the level should advance based on the current entities.
     * 
     * @param entities the list of entities in the level
     * @return true if the level should advance, false otherwise
     */
    boolean shouldAdvance(List<Entity> entities);

    /**
     * Determines whether the level index should be increased.
     * 
     * @return true if the level index should be increased, false otherwise
     */
    boolean shouldIncreaseLevelIndex();

    /**
     * Determines whether Donkey is allowed to throw a barrel in the current map
     * state.
     * This method must be implemented by subclasses to specify the conditions under
     * which
     * Donkey can perform the barrel-throwing action.
     *
     * @return true if Donkey can throw a barrel in this map state, false otherwise
     */
    boolean canDonkeyThrowBarrel();
}
