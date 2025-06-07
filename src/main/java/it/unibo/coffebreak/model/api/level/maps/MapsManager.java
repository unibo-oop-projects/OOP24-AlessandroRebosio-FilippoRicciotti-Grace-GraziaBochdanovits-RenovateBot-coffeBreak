package it.unibo.coffebreak.model.api.level.maps;

import java.util.List;

/**
 * Manages game maps, providing functionality to load, reset, and update maps.
 * 
 * @author Filippo Ricciotti
 */
public interface MapsManager {
    /**
     * Loads the next map in sequence.
     * 
     * @return the lines representing the next map layout
     */
    List<String> loadNextMap();

    /**
     * Resets the current map to its initial state.
     * 
     * @return the lines representing the reset map layout
     */
    List<String> resetCurrentMap();

    /**
     * Gets the bonus score associated with the current map.
     * 
     * @return the map bonus score
     */
    int getLevelBonus();
}
