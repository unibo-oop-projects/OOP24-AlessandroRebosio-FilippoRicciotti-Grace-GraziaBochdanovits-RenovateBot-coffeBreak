package it.unibo.coffebreak.model.impl.level;

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
import it.unibo.coffebreak.model.api.level.LevelManager;
import it.unibo.coffebreak.model.api.level.maps.Maps;
import it.unibo.coffebreak.model.impl.entities.GameEntityFactory;
import it.unibo.coffebreak.model.impl.level.maps.GameMaps;

/**
 * Implementation of the {@link LevelManager} interface.
 * Manages entities for the current game level and handles entity cleanup,
 * reset,
 * and transitions between maps and levels.
 * 
 * @author Filippo Ricciotti
 */
public class GameLevelManager implements LevelManager {

    public static final int MAX_LEVELID = 0;

    private final EntityFactory factory;
    private final Maps maps;

    private final List<Entity> entities;
    int currentLevel;

    /**
     * Constructs a new {@code GameLevelManager} with an empty entity list.
     */
    public GameLevelManager() {
        this.factory = new GameEntityFactory();
        this.maps = new GameMaps();

        this.entities = new ArrayList<>();
        this.getNextMap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(this.entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentMapBonus() {
        return this.maps.getMapBonus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadNextEnitites() {
        this.entities.clear();
        // TODO: complete loadEntities
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEntity(final Entity entity) {
        return this.entities.add(Objects.requireNonNull(entity, "The entity cannot be null"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void transformEntities() {
        this.entities.stream()
                .filter(Barrel.class::isInstance)
                .map(Barrel.class::cast)
                .filter(Barrel::canTransformToFire)
                .forEach(e -> addEntity(this.factory.createFire(e.getPosition())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cleanEntities() {
        this.removeEntities(entities, Collectible.class, Collectible::isCollected);
        this.removeEntities(entities, Enemy.class, Enemy::isDestroyed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetEntities() {
        this.loadNextEnitites();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getNextMap() {

    }

    /**
     * Removes entities from the list that match the given type and condition.
     *
     * @param <T>              the type of entity to remove
     * @param entities         the list of entities to filter
     * @param type             the class object representing the type of entities to
     *                         remove
     * @param removalCondition predicate to determine if entity should be removed
     * @return true if any entities were removed, false otherwise
     */
    private <T extends Entity> boolean removeEntities(final List<Entity> entities, final Class<T> type,
            final Predicate<T> removalCondition) {
        return entities.removeAll(entities.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .filter(removalCondition)
                .toList());
    }
}
