package it.unibo.coffebreak.model.impl.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.level.LevelManager;
import it.unibo.coffebreak.model.api.level.cleaner.Cleaner;
import it.unibo.coffebreak.model.impl.level.cleaner.EntityCleaner;

public class GameLevelManager implements LevelManager {

    private final List<Entity> entities;
    private final Cleaner cleanup;

    public GameLevelManager() {
        this.cleanup = new EntityCleaner();
        this.entities = new ArrayList<>();
    }

    @Override
    public void loadEntities() {
        // TODO: take entity from a file
    }

    @Override
    public boolean addEntity(Entity entity) {
        Objects.requireNonNull(entity, "The entity cannot be null");
        return this.entities.add(entity);
    }

    @Override
    public boolean removeEntity(Entity entity) {
        Objects.requireNonNull(entity, "The entity cannot be null");
        return this.entities.remove(entity);
    }

    @Override
    public void resetEntities() {
        this.loadEntities();
    }

    @Override
    public void cleanEntities() {
        this.cleanup.clean(null);
    }

    @Override
    public void nextMap() {
        // TODO:
    }

    @Override
    public void nextLevel() {
        // TODO:
    }

}
