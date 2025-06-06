package it.unibo.coffebreak.model.impl.level;

import java.util.List;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.level.LevelManager;
import it.unibo.coffebreak.model.api.level.entity.EntityManager;
import it.unibo.coffebreak.model.api.level.maps.MapsManager;
import it.unibo.coffebreak.model.impl.level.entity.GameEntityManager;
import it.unibo.coffebreak.model.impl.level.maps.GameMapsManager;

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
    private final MapsManager maps;

    /**
     * Constructs a new {@code GameLevelManager}.
     */
    public GameLevelManager() {
        this.entityManager = new GameEntityManager();
        this.maps = new GameMapsManager();
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
        this.entityManager.loadEntitiesFromMap(this.maps.loadNextMap());
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
        this.entityManager.resetEntities(this.maps.resetCurrentMap());
    }
}
