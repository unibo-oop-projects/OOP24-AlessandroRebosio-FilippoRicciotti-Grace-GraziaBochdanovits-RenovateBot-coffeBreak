package it.unibo.coffebreak.impl.model.level.maps;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.level.maps.MapsManager;
import it.unibo.coffebreak.api.model.level.maps.state.MapState;
import it.unibo.coffebreak.impl.model.level.maps.state.map1.GameMapOne;
import it.unibo.coffebreak.impl.model.level.maps.state.map4.GameMapFour;

/**
 * Concrete implementation of the {@link MapsManager} interface.
 * Manages the sequence of game maps (levels), allowing access to the current
 * map,
 * adding new maps, and determining if the level should advance based on
 * entities.
 * Maps are kept sorted by their index.
 * 
 * @author Filippo Ricciotti
 */
public class GameMapsManager implements MapsManager {

    private final List<MapState> maps = new ArrayList<>(List.of(new GameMapOne(), new GameMapFour()));

    private int mapIndex;

    /**
     * Returns the list of strings representing the current map layout.
     * 
     * @return the current map data as a list of strings
     */
    @Override
    public List<String> currentMap() {
        return this.getMaps().currentMap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean advance(final List<Entity> entities) {
        if (this.shouldAdvance(entities)) {
            this.mapIndex++;
            if (this.mapIndex >= this.maps.size()) {
                this.mapIndex = 0;
            }
            return true;
        }
        return false;
    }

    /**
     * Adds a new map to the sequence and sorts the list by map index.
     * 
     * @param mapToAdd the map state to add
     * @return true if the map was added successfully, false otherwise
     */
    @Override
    public boolean addMap(final MapState mapToAdd) {
        final boolean isAdded = this.maps.add(mapToAdd);
        this.maps.sort(Comparator.comparingInt(MapState::getIndex));

        return isAdded;
    }

    /**
     * Determines if the level should advance based on the current entities.
     * 
     * @param entities the list of entities in the level
     * @return true if the level should advance, false otherwise
     */
    @Override
    public boolean shouldAdvance(final List<Entity> entities) {
        return this.getMaps().shouldAdvance(entities);
    }

    /**
     * Returns the current MapState object.
     * 
     * @return the current map state
     */
    private MapState getMaps() {
        return this.maps.get(this.mapIndex);
    }

    /**
     * Determines whether Donkey is allowed to throw a barrel in the current map
     * state.
     * This method must be implemented by subclasses to specify the conditions under
     * which
     * Donkey can perform the barrel-throwing action.
     *
     * @return true if Donkey can throw a barrel in this map state, false otherwise
     */
    @Override
    public boolean canDonkeyThrowBarrel() {
        return this.maps.get(this.mapIndex).canDonkeyThrowBarrel();
    }
}
