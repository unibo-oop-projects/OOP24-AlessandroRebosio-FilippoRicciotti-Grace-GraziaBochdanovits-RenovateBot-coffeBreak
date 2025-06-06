package it.unibo.coffebreak.model.api.level.entity;

import java.util.List;

import it.unibo.coffebreak.model.api.entities.Entity;

public interface EntityManager {
    public List<Entity> getEntities();

    public void loadEntitiesFromMap(final List<String> mapLines);

    public boolean addEntity(final Entity entity);

    public void cleanEntities();

    public void resetEntities(final List<String> mapLines);

    public void transformBarrels();
}
