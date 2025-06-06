package it.unibo.coffebreak.model.api.level.maps;

import java.util.List;

public interface Maps {

    List<String> loadNextMap();

    int getMapBonus();

    List<String> resetCurrentMap();

    void updateMaps(final int levelID);
}
