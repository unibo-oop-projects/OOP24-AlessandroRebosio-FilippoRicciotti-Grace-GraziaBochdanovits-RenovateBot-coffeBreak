package it.unibo.coffebreak.impl.model.level.maps.state;

import java.util.Collections;
import java.util.List;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.level.maps.state.MapState;

/**
 * Abstract base class for map states, providing common logic for loading
 * and storing map data from a resource file. Subclasses must implement
 * the advancement condition and provide the map index.
 *
 * The map file is loaded from the path "/maps/Map{index}.txt" where
 * {index} is provided by the subclass via the constructor.
 *
 * @author Filippo Ricciotti
 */
public abstract class AbstractMapState implements MapState {

    private static final String RESOURCE_PATH_PREFIX = "/maps/Map";
    private static final String RESOURCE_PATH_SUFFIX = ".txt";

    private final List<String> map;
    private final int index;

    /**
     * Constructs the map state and loads the map data from the resource path
     * based on the map index.
     *
     * @param index  the index of the map (used to build the resource path)
     * @param loader the loader used to load map resources
     * @throws IllegalStateException if the map file cannot be loaded
     */
    public AbstractMapState(final int index, final Loader loader) {
        this.map = loader.loadMap(RESOURCE_PATH_PREFIX + index + RESOURCE_PATH_SUFFIX);
        this.index = index;
    }

    /**
     * Returns the loaded map data.
     *
     * @return the map as a list of strings
     */
    @Override
    public List<String> currentMap() {
        return Collections.unmodifiableList(this.map);
    }

    /**
     * Returns the index of this map in the sequence of levels.
     *
     * @return the index of the map
     */
    @Override
    public final int getIndex() {
        return this.index;
    }

    /**
     * Determines if the level should advance based on the current entities.
     * This must be implemented by subclasses.
     *
     * @param entities the list of entities in the level
     * @return true if the level should advance, false otherwise
     */
    @Override
    public abstract boolean shouldAdvance(List<Entity> entities);

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
    public abstract boolean canDonkeyThrowBarrel();
}
