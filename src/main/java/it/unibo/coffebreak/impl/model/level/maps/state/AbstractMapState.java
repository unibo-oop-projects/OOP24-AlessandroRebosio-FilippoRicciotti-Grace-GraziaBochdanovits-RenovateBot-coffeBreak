package it.unibo.coffebreak.impl.model.level.maps.state;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.level.maps.state.MapState;

/**
 * Abstract base class for map states, providing common logic for loading
 * and storing map data from a resource file. Subclasses must implement
 * the advancement condition and provide the map index.
 *
 * The map file is loaded from the path "maps/map{index}.txt" where
 * {index} is provided by the subclass via the constructor.
 *
 * @author Filippo Ricciotti
 */
public abstract class AbstractMapState implements MapState {

    private static final String RESOURCE_PATH_PREFIX = "maps/map";
    private static final String RESOURCE_PATH_SUFFIX = ".txt";

    private final List<String> map;
    private final int index;

    /**
     * Constructs the map state and loads the map data from the resource path
     * based on the map index.
     *
     * @param index the index of the map (used to build the resource path)
     * @throws IllegalStateException if the map file cannot be loaded
     */
    public AbstractMapState(final int index) {
        this.map = this.loadMapFromResource(index);
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

    /**
     * Loads the map data from the resource file using the index directly.
     * The resource path is constructed as "maps/map{index}.txt".
     *
     * @param index the index of the map (used to build the resource path)
     * @return the map data as a list of strings
     * @throws MapLoadingException if the file cannot be loaded
     */
    private List<String> loadMapFromResource(final int index) {
        final String resourcePath = RESOURCE_PATH_PREFIX + index + RESOURCE_PATH_SUFFIX;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.toList());
        } catch (final IOException e) {
            throw new MapLoadingException("Could not load map file: " + resourcePath, e);
        }
    }

    /**
     * Add commentMore actions
     * Indicates map-specific errors during persistence operations.
     * Wraps lower-level IO and serialization exceptions.
     */
    public static class MapLoadingException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        /**
         * Creates an exception with detailed context.
         * 
         * @param message the operational context
         */
        public MapLoadingException(final String message) {
            super(message);
        }

        /**
         * Creates an exception with detailed context and cause.
         * 
         * @param message the operational context
         * @param cause   the root failure cause
         */
        public MapLoadingException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
