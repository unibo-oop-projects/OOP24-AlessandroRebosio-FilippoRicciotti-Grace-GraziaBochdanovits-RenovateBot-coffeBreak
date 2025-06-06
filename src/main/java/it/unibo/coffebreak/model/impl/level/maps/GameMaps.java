package it.unibo.coffebreak.model.impl.level.maps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.coffebreak.model.api.level.maps.Maps;

public class GameMaps implements Maps {

    private final List<String> avaiableMaps;

    public GameMaps() {
        this.avaiableMaps = new ArrayList<>();
    }

    @Override
    public List<String> getAvaiableMaps() {
        return Collections.unmodifiableList(this.avaiableMaps);
    }

    @Override
    public void updateMaps(final int levelID) {
        this.avaiableMaps.clear();

        switch (levelID) {
            case 0 -> {
                this.avaiableMaps.add("maps/Map1");
            }
        }
    }

}
