package it.unibo.coffebreak.model.impl.level.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.EntityFactory;
import it.unibo.coffebreak.model.api.entities.collectible.Collectible;
import it.unibo.coffebreak.model.api.entities.enemy.Enemy;
import it.unibo.coffebreak.model.api.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.model.api.level.entity.EntityManager;
import it.unibo.coffebreak.model.impl.entities.GameEntityFactory;

public class GameEntityManager implements EntityManager {

    private final EntityFactory factory = new GameEntityFactory();
    private final List<Entity> entities = new ArrayList<>();

    @Override
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    @Override
    public void loadEntitiesFromMap(final List<String> mapLines) {
        entities.clear();
        // TODO: loadEntities
    }

    @Override
    public boolean addEntity(final Entity entity) {
        return entities.add(Objects.requireNonNull(entity));
    }

    @Override
    public void cleanEntities() {
        this.removeEntities(Collectible.class, Collectible::isCollected);
        this.removeEntities(Enemy.class, Enemy::isDestroyed);
    }

    @Override
    public void resetEntities(final List<String> mapLines) {
        loadEntitiesFromMap(mapLines);
    }

    @Override
    public void transformBarrels() {
        entities.stream()
                .filter(Barrel.class::isInstance)
                .map(Barrel.class::cast)
                .filter(Barrel::canTransformToFire)
                .forEach(b -> addEntity(factory.createFire(b.getPosition())));
    }

    private <T extends Entity> void removeEntities(final Class<T> type, final Predicate<T> condition) {
        entities.removeIf(e -> type.isInstance(e) && condition.test(type.cast(e)));
    }
}
