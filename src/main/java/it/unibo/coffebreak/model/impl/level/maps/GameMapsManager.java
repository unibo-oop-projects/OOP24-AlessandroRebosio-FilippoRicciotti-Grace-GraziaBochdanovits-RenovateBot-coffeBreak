package it.unibo.coffebreak.model.impl.level.maps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import it.unibo.coffebreak.model.api.level.maps.MapsManager;

/**
 * Concrete implementation of {@link MapsManager} that manages game maps and
 * their
 * properties.
 * Handles loading, resetting, and updating maps based on game progression.
 * 
 * @author Filippo Ricciotti
 */
public class GameMapsManager implements MapsManager {

    private static final List<Integer> MAP_BONUSES = List.of(5000, 6000, 7000, 8000);
    private static final List<List<String>> LEVEL_MAPS = List.of(
            List.of("maps/Map1.txt", "maps/Map4.txt"));

    private final Map<String, List<String>> mapCache = new HashMap<>();

    private int levelIndex;
    private int mapIndex;

    private final Function<String, List<String>> loadMapFromFile = path -> {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (final IOException e) {
            throw new MapLoadingException("Failed to load map: " + path, e);
        }
    };

    /**
     * Constructs a new {@code GameMapsManager}.
     */
    public GameMapsManager() {
        this.levelIndex = 0;
        this.mapIndex = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> loadNextMap() {
        final List<String> current = resetCurrentMap();

        this.mapIndex++;
        if (this.mapIndex >= LEVEL_MAPS.get(this.levelIndex).size()) {
            this.levelIndex = Math.min(this.levelIndex + 1, LEVEL_MAPS.size() - 1);
            this.mapIndex = 0;
        }
        return current;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> resetCurrentMap() {
        return this.mapCache.computeIfAbsent(LEVEL_MAPS.get(this.levelIndex).get(this.mapIndex),
                loadMapFromFile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMapBonus() {
        return MAP_BONUSES.get(Math.min(this.levelIndex, MAP_BONUSES.size() - 1));
    }

    /**
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
