package it.unibo.coffebreak.model.api.level;

public interface LevelManager {
    void loadEntities();

    void resetEntities();

    void nextMap();

    void cleanEntities();

    void nextLevel();
}
