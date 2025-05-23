package it.unibo.coffebreak.model.api.level.cleaner;

import java.util.List;

import it.unibo.coffebreak.model.api.entities.Entity;


public interface Cleaner {
    void clean(List<Entity> entities);
}
