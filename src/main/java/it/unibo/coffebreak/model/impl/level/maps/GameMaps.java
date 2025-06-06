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

public class GameMaps implements Maps {

    private final List<String> availableMaps;
    private final Map<String, List<String>> loadedMaps;

    private int index;

    public GameMaps() {
        this.availableMaps = new ArrayList<>();
        this.loadedMaps = new HashMap<>();

        this.index = 0;
    }

    @Override
    public List<String> loadNextMap() {
        return loadedMaps.computeIfAbsent(this.getNext(), k -> {
            try {
                final Path path = Paths.get(this.getNext() + ".txt");
                if (!Files.exists(path)) {
                    throw new IOException("Map file not found: " + path);
                }
                return Files.readAllLines(path);
            } catch (final IOException e) {
                throw new RuntimeException("Failed to load map: " + this.getNext(), e);
            }
        });
    }

    @Override
    public List<String> resetCurrentMap() {
        return this.loadedMaps.get(this.availableMaps.get(this.index));
    }

    @Override
    public void updateMaps(final int levelID) {
        this.availableMaps.clear();
        this.index = 0;

        switch (levelID) {
            case 0 -> {
                this.availableMaps.add("maps/Map1");
                this.availableMaps.add("maps/Map4");
            }
        }
    }

    private String getNext() {
        if (this.availableMaps.isEmpty()) {
            throw new IllegalStateException("No maps available");
        }
        return this.availableMaps.get(index++ % this.availableMaps.size());
    }
}
