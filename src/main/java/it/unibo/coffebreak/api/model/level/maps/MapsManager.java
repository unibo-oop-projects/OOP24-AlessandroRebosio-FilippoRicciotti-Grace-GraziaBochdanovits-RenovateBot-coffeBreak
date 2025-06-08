package it.unibo.coffebreak.api.model.level.maps;

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

    void calculateBonus(float deltaTime);

    int getBonusValue();
}
