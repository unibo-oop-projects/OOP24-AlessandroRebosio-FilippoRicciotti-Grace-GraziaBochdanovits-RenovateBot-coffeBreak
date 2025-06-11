package it.unibo.coffebreak.impl.model.level.maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import it.unibo.coffebreak.api.model.level.bonus.Bonus;
import it.unibo.coffebreak.api.model.level.maps.MapsManager;
import it.unibo.coffebreak.impl.model.level.bonus.GameBonus;

/**
 * Concrete implementation of {@link MapsManager} that manages game maps and
 * their
 * properties.
 * Handles loading, resetting, and updating maps based on game progression.
 * 
 * @author Filippo Ricciotti
 */
public class GameMapsManager implements MapsManager {

    private static final long BONUS_INTERVAL = 2;
    private static final List<Integer> MAP_BONUSES = List.of(5000, 6000, 7000, 8000);
    private static final List<List<String>> LEVEL_MAPS = List.of(
            List.of("/maps/Map1.txt", "/maps/Map4.txt"));

    private final Map<String, List<String>> mapCache = new HashMap<>();

    private final Bonus bonus = new GameBonus();

    private float bonusElapsed;
    private int levelIndex;
    private int mapIndex;

    private final Function<String, List<String>> loadMapFromFile = path -> {
        try (var is = GameMapsManager.class.getResourceAsStream(path);
                var reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            if (is == null) {
                throw new MapLoadingException("Map file not found: " + path);
            }
            this.bonus.setBonus(this.getLevelBonus());
            return reader.lines().toList();
        } catch (final IOException e) {
            throw new MapLoadingException("Error loading map: " + path, e);
        }
    };

    /**
     * Constructs a new {@code GameMapsManager}.
     */
    public GameMapsManager() {
        this.bonusElapsed = 0;
        this.levelIndex = 0;
        this.mapIndex = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLevelIndex() {
        return this.levelIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBonusValue() {
        return this.bonus.getBonus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void advanceMap() {
        this.mapIndex++;

        final List<String> currentLevelMaps = LEVEL_MAPS.get(Math.min(this.levelIndex, LEVEL_MAPS.size() - 1));

        if (this.mapIndex >= currentLevelMaps.size()) {
            this.mapIndex = 0;
            if (this.levelIndex < LEVEL_MAPS.size() - 1) {
                this.levelIndex++;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> loadCurrentMap() {
        final List<String> currentLevelMaps = LEVEL_MAPS.get(Math.min(levelIndex, LEVEL_MAPS.size() - 1));
        final String mapPath = currentLevelMaps.get(mapIndex);

        return mapCache.computeIfAbsent(mapPath, loadMapFromFile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void calculateBonus(final float deltaTime) {
        this.bonusElapsed += deltaTime;
        if (this.bonusElapsed >= BONUS_INTERVAL) {
            this.bonus.calculate();
            this.bonusElapsed = 0;
        }
    }

    private int getLevelBonus() {
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
