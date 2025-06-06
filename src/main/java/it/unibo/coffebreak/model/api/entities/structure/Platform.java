package it.unibo.coffebreak.model.api.entities.structure;

import it.unibo.coffebreak.model.api.entities.Entity;

/**
 * Represents a platform where entities can stand or move on.
 * Platforms may have different slopes affecting movement.
 * 
 * @author Alessandro Rebosio
 */
public interface Platform extends Entity {
    /**
     * Checks if this platform can support the given entity.
     * 
     * @param entity the entity to check
     * @return true if the entity can stand on this platform, false otherwise
     */
    boolean isSupporting(Entity entity);

    /**
     * Checks if this platform is breakable.
     * 
     * @return true if the platform can break, false otherwise
     */
    boolean canBreak();
}
