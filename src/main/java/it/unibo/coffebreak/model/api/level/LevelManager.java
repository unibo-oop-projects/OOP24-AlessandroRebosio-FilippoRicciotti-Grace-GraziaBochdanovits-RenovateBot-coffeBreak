package it.unibo.coffebreak.model.api.level;

public interface LevelManager {
    void leadEntities();
    
    void resetEntities();

    void nextMap();

    void cleanEntities();

    void nextLevel();
}
