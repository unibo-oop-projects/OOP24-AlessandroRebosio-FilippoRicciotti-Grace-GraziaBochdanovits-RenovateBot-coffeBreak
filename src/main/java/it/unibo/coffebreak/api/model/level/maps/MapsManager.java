package it.unibo.coffebreak.api.model.level.maps;

import java.util.List;

/**
 * Manages game maps, providing functionality to load, reset, and update maps.
 * 
 * @author Filippo Ricciotti
 */
public interface MapsManager {
    
    int getLevelIndex();

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
     * Calculates and applies a time-based bonus duration is decremented by
     * {@code deltaTime}.
     * 
     * @param deltaTime the time elapsed since the last frame (in seconds).
     */
    void calculateBonus(float deltaTime);

    /**
     * Method for get bonus value.
     * 
     * @return the bonus value
     */
    int getBonusValue();
}
