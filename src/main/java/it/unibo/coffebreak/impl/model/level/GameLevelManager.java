package it.unibo.coffebreak.impl.model.level;

import java.util.List;
import java.util.Optional;

import it.unibo.coffebreak.api.common.Loader;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.level.LevelManager;
import it.unibo.coffebreak.api.model.level.bonus.Bonus;
import it.unibo.coffebreak.api.model.level.entity.EntityManager;
import it.unibo.coffebreak.api.model.level.maps.MapsManager;
import it.unibo.coffebreak.impl.model.level.bonus.GameBonus;
import it.unibo.coffebreak.impl.model.level.entity.GameEntityManager;
import it.unibo.coffebreak.impl.model.level.maps.GameMapsManager;

/**
 * Concrete implementation of the LevelManager interface.
 * Handles the logic and state of a game level, including entity management,
 * main character access, bonus calculation, and level progression.
 * Delegates entity and map management to specialized managers.
 * 
 * @author Filippo Ricciotti
 */
public class GameLevelManager implements LevelManager {

    private static final float BONUS_INTERVAL = 2f;
    private static final int BONUS = 5000;

    private final EntityManager entityManager = new GameEntityManager();
    private final Bonus levelBonus = new GameBonus();
    private final MapsManager mapsManager;

    private int levelIndex;
    private float bonusElapsedTime;

    /**
     * Constructs a new {@code GameLevelManager} using the specified {@link Loader}.
     * Initializes the internal {@link GameMapsManager} with the provided loader.
     *
     * @param loader the loader used to initialize the game maps manager
     */
    public GameLevelManager(final Loader loader) {
        this.mapsManager = new GameMapsManager(loader);
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
    public Optional<MainCharacter> getMainCharacter() {
        return this.entityManager.getMainCharacter();
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
        this.entityManager.transformEntities();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadCurrentEntities() {
        this.levelBonus.setBonus(BONUS);
        this.entityManager.loadEntities(this.mapsManager.currentMap(), this.mapsManager.canDonkeyThrowBarrel());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBonusValue() {
        return this.levelBonus.getBonus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void calculateBonus(final float deltaTime) {
        this.bonusElapsedTime += deltaTime;
        if (this.bonusElapsedTime >= BONUS_INTERVAL) {
            this.levelBonus.calculate();
            this.bonusElapsedTime = 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLevelIndex() {
        return this.levelIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void advance() {
        if (this.mapsManager.advance(this.getEntities())) {
            this.getMainCharacter().ifPresent(p -> p.earnPoints(this.getBonusValue()));
            this.levelIndex++;
            this.loadCurrentEntities();
        }
    }
}
