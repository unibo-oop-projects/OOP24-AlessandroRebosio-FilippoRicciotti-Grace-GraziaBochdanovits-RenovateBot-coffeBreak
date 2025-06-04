package it.unibo.coffebreak.model.impl.level.cleaner;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

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
 * 
 * @author Filippo Ricciotti
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

        this.removeEntities(entities, Enemy.class, Enemy::isDestroyed);
        this.removeEntities(entities, Collectible.class, Collectible::isCollected);
    }

    /**
     * Removes entities from the list that match the given type and condition.
     *
     * @param <T>              the type of entity to remove
     * @param entities         the list of entities to filter
     * @param typeFilter       predicate to filter by type
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
