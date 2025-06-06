package it.unibo.coffebreak.model.impl.level.maps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.coffebreak.model.api.level.maps.Maps;
import it.unibo.coffebreak.model.impl.level.GameLevelManager;

public class GameMaps implements Maps {

    private final List<String> availableMaps;
    private final Map<String, List<String>> loadedMaps;

    private boolean hasNextLevel;
    private int mapIndex;
    private int levelID;
    private int bonusMap;

    public GameMaps() {
        this.availableMaps = new ArrayList<>();
        this.loadedMaps = new HashMap<>();

        this.hasNextLevel = true;
        this.mapIndex = 0;
        this.levelID = 0;
        this.bonusMap = 0;

        updateMaps(this.levelID);
    }

    @Override
    public List<String> loadNextMap() {
        if (this.mapIndex >= this.availableMaps.size()) {
            if (this.hasNextLevel) {
                this.updateMaps(++this.levelID);
            }
        }

        return loadedMaps.computeIfAbsent(availableMaps.get(mapIndex++), this::loadMapFromFile);
    }

    @Override
    public List<String> resetCurrentMap() {
        return loadedMaps.get(availableMaps.get(Math.max(0, mapIndex - 1)));
    }

    @Override
    public int getMapBonus() {
        return this.bonusMap;
    }

    @Override
    public void updateMaps(final int newLevelID) {
        this.availableMaps.clear();
        this.mapIndex = 0;

        switch (newLevelID) {
            case 0 -> {
                this.availableMaps.add("maps/Map1");
                this.availableMaps.add("maps/Map4");
            }
            default -> {
                this.hasNextLevel = false;
                this.levelID = GameLevelManager.MAX_LEVELID;
                this.updateMaps(levelID);
            }
        }
    }

    private List<String> loadMapFromFile(final String mapName) {
        try {
            final Path path = Paths.get(mapName + ".txt");

            if (!Files.exists(path)) {
                throw new IOException("Map file not found: " + path);
            }

            final List<String> lines = new ArrayList<>(Files.readAllLines(path));
            if (lines.isEmpty()) {
                throw new IOException("Map file is empty: " + path);
            }

            this.bonusMap = Integer.parseInt(lines.removeFirst());
            return lines;

        } catch (final IOException e) {
            throw new RuntimeException("Failed to load map: " + mapName, e);
        } catch (final NumberFormatException e) {
            throw new RuntimeException("Invalid bonus format in map: " + mapName, e);
        }
    }
}
