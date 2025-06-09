package it.unibo.coffebreak.api.model.level.maps;

import java.util.List;

/**
 * Manages game maps, providing functionality to load, reset, and update maps.
 * Also handles bonus calculation and level indexing.
 * <p>
 * This interface abstracts the logic related to the progression of maps
 * across levels in the game.
 * </p>
 * 
 * @author Filippo Ricciotti
 */
public interface MapsManager {
    /**
     * Gets the current level index.
     * 
     * @return the index of the current level
     */
    int getLevelIndex();

    /**
     * Retrieves the current bonus value for the level.
     * 
     * @return the bonus value
     */
    int getBonusValue();

    /**
     * Loads the next map in the current level sequence.
     * If the sequence ends, it progresses to the next level.
     * 
     * @return the lines representing the next map layout
     */
    List<String> loadNextMap();

    /**
     * Resets the current map to its original state.
     * 
     * @return the lines representing the reset map layout
     */
    List<String> resetCurrentMap();

    /**
     * Calculates and applies time-based changes to the bonus value.
     * 
     * @param deltaTime the time elapsed since the last frame (in seconds)
     * @throws IllegalArgumentException if deltaTime is negative
     */
    void calculateBonus(float deltaTime);
}
