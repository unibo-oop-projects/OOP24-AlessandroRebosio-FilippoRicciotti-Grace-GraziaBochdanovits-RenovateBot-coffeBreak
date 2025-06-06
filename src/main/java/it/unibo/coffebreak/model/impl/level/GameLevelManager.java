package it.unibo.coffebreak.model.impl.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.collectible.Collectible;
import it.unibo.coffebreak.model.api.entities.enemy.Enemy;
import it.unibo.coffebreak.model.api.level.LevelManager;

/**
 * Implementation of the {@link LevelManager} interface.
 * Manages entities for the current game level and handles entity cleanup,
 * reset,
 * and transitions between maps and levels.
 * 
 * @author Filippo Ricciotti
 */
public class GameLevelManager implements LevelManager {

    private final List<Entity> entities;

    /**
     * Constructs a new {@code GameLevelManager} with an empty entity list.
     */
    public GameLevelManager() {
        this.entities = new ArrayList<>();
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
    public void loadEntities() {
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
    public boolean removeEntity(final Entity entity) {
        return this.entities.remove(Objects.requireNonNull(entity, "The entity cannot be null"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAll(final Predicate<Entity> filter) {
        // TODO: impl
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
        this.loadEntities();
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
