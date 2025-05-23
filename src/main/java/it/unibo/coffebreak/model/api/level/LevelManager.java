package it.unibo.coffebreak.model.api.level;

import it.unibo.coffebreak.model.api.entities.Entity;

public interface LevelManager {
    void loadEntities();

    boolean addEntity(Entity entity);

    boolean removeEntity(Entity entity);

    void resetEntities();

    void nextMap();

    void cleanEntities();

    void nextLevel();
}
