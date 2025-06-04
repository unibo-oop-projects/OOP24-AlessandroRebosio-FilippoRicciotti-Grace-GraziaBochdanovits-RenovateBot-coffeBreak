package it.unibo.coffebreak.model.api.level.cleaner;

import java.util.List;

import it.unibo.coffebreak.model.api.entities.Entity;

/**
 * Strategy interface for cleaning entities from a level.
 * Implementations define rules to remove certain types of entities,
 * such as destroyed enemies or collected collectibles.
 * 
 * @author Filippo Ricciotti
 */
public interface Cleaner {

    /**
     * Cleans the given list of entities by removing specific ones
     * based on custom rules (e.g., destroyed enemies, collected items).
     *
     * @param entities the list of entities to clean (must not be null)
     */
    void clean(List<Entity> entities);
}
