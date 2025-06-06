package it.unibo.coffebreak.model.impl.level;

import java.util.List;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.level.LevelManager;
import it.unibo.coffebreak.model.api.level.entity.EntityManager;
import it.unibo.coffebreak.model.api.level.maps.Maps;
import it.unibo.coffebreak.model.impl.level.entity.GameEntityManager;
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

    private final EntityManager entityManager;
    private final Maps maps;

    /**
     * Constructs a new {@code GameLevelManager}.
     */
    public GameLevelManager() {
        this.entityManager = new GameEntityManager();
        this.maps = new GameMaps();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entity> getEntities() {
        return this.entityManager.getEntities();
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
        // TODO: complete loadEntities
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEntity(final Entity entity) {
        return this.entityManager.addEntity(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void transformEntities() {
        this.entityManager.transformBarrels();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cleanEntities() {
        this.entityManager.cleanEntities();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetEntities() {
        this.entityManager.resetEntities(null);
    }
}
