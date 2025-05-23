package it.unibo.coffebreak.model.impl.level.cleaner;

import java.util.List;
import java.util.Objects;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.collectible.Collectible;
import it.unibo.coffebreak.model.api.entities.enemy.Enemy;
import it.unibo.coffebreak.model.api.level.cleaner.Cleaner;

/**
 * Implementation of {@link Cleaner} that removes specific types of entities.
 * <ul>
 * <li>Enemies that have been destroyed</li>
 * <li>Collectibles that have been collected</li>
 * </ul>
 */
public class EntityCleaner implements Cleaner {

    /**
     * Cleans the given list of entities by removing.
     * <ul>
     * <li>{@link Enemy} instances that are destroyed</li>
     * <li>{@link Collectible} instances that are collected</li>
     * </ul>
     *
     * @param entities the list of entities to clean (must not be null)
     * @throws NullPointerException if the entities list is null
     */
    @Override
    public void clean(final List<Entity> entities) {
        Objects.requireNonNull(entities, "The entities cannot be null");

        this.removeCollectible(entities);
        this.removeEnemy(entities);
    }

    /**
     * Removes all {@link Enemy} instances from the list that are marked as
     * destroyed.
     *
     * @param entities the list of entities to filter
     * @return true if any enemies were removed, false otherwise
     */
    private boolean removeEnemy(final List<Entity> entities) {
        return entities.removeAll(entities.stream()
                .filter(Enemy.class::isInstance)
                .map(Enemy.class::cast)
                .filter(Enemy::isDestroyed)
                .toList());
    }

    /**
     * Removes all {@link Collectible} instances from the list that have been
     * collected.
     *
     * @param entities the list of entities to filter
     * @return true if any collectibles were removed, false otherwise
     */
    private boolean removeCollectible(final List<Entity> entities) {
        return entities.removeAll(entities.stream()
                .filter(Collectible.class::isInstance)
                .map(Collectible.class::cast)
                .filter(Collectible::isCollected)
                .toList());
    }
}
