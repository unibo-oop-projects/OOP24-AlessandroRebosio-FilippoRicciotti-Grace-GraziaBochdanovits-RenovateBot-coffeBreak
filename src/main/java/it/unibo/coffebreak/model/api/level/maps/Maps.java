package it.unibo.coffebreak.model.api.level.maps;

import java.util.List;

public interface Maps {

    List<String> loadNextMap();

    void updateMaps(final int levelID);
}
