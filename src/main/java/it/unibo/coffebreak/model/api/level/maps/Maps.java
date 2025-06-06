package it.unibo.coffebreak.model.api.level.maps;

import java.util.List;

public interface Maps {

    List<String> loadNextMap();

    List<String> resetCurrentMap();

    int getMapBonus();

    void updateMaps(final int levelID);
}
