package it.unibo.coffebreak.impl.model.level.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.EntityFactory;
import it.unibo.coffebreak.api.model.entities.collectible.Collectible;
import it.unibo.coffebreak.api.model.entities.enemy.Enemy;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.api.model.level.entity.EntityManager;
import it.unibo.coffebreak.impl.common.Position2D;
import it.unibo.coffebreak.impl.model.entities.GameEntityFactory;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;

/**
 * Concrete implementation of {@link EntityManager} that manages game entities.
 * Handles creation, storage, and lifecycle of all entities in the game level,
 * including enemies, collectibles, and environmental objects.
 * 
 * @author Filippo Ricciotti
 */
public class GameEntityManager implements EntityManager {

    private final EntityFactory factory = new GameEntityFactory();
    private final List<Entity> entities = new ArrayList<>();

    private MainCharacter player;

    /**
     * {@inheritDoc}
     * 
     * @return an unmodifiable view of the current entity list
     */
    @Override
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MainCharacter getPlayer() {
        return Objects.requireNonNull(this.player, "The player cannot be null");
    }

    /**
     * {@inheritDoc}
     * Clears current entities and loads new ones from the specified map
     * representation.
     * 
     * @param mapLines the textual representation of the game map
     */
    @Override
    public void loadEntitiesFromMap(final List<String> mapLines) {
        this.entities.clear();
        for (int y = 0; y < mapLines.size(); y++) {
            final String line = mapLines.get(y);
            for (int x = 0; x < line.length(); x++) {
                final Position2D position = new Position2D(x * GameEntityFactory.DEFAULT.width(),
                        y * GameEntityFactory.DEFAULT.height());
                final char c = line.charAt(x);

                switch (Character.toUpperCase(c)) {
                    case 'P' -> this.addEntity(factory.createNormalPlatform(position));
                    case 'B' -> this.addEntity(factory.createBreakablePlatform(position));
                    case 'L' -> this.addEntity(factory.createNormalLadder(position));
                    case 'T' -> this.addEntity(factory.createTank(position));
                    case 'H' -> this.addEntity(factory.createHammer(position));
                    case 'C' -> this.addEntity(factory.createCoin(position));
                    case 'M' -> {
                        this.player = factory.createMario(position);
                        this.addEntity(player);
                    }
                    case 'D' -> this.addEntity(factory.createDonkeyKong(position));
                    case 'R' -> this.addEntity(factory.createPrincess(position));
                    default -> {
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @param entity the entity to add (cannot be null)
     * @return true if the entity was successfully added
     * @throws NullPointerException if the entity parameter is null
     */
    @Override
    public boolean addEntity(final Entity entity) {
        return this.entities.add(Objects.requireNonNull(entity, "The Entity cannot be null."));
    }

    /**
     * {@inheritDoc}
     * Removes all collected collectibles and destroyed enemies from the entity
     * list.
     */
    @Override
    public void cleanEntities() {
        this.removeEntities(Collectible.class, Collectible::isCollected);
        this.removeEntities(Enemy.class, Enemy::isDestroyed);
    }

    /**
     * {@inheritDoc}
     * Resets the entity list by reloading entities from the specified map.
     * 
     * @param mapLines the textual representation of the game map
     */
    @Override
    public void resetEntities(final List<String> mapLines) {
        this.loadEntitiesFromMap(mapLines);
    }

    /**
     * {@inheritDoc}
     * Transforms all barrels that can be transformed into fire entities.
     * Creates new fire entities at the barrels' positions.
     */
    @Override
    public void transformBarrels() {
        entities.stream()
                .filter(Barrel.class::isInstance)
                .map(Barrel.class::cast)
                .filter(Barrel::canTransformToFire)
                .map(b -> factory.createFire(b.getPosition()))
                .forEach(this::addEntity);
    }

    /**
     * Removes entities of a specific type that match a given condition.
     * 
     * @param <T>       the entity type to filter for
     * @param type      the class object representing the entity type
     * @param condition the predicate that determines which entities to remove
     */
    private <T extends Entity> void removeEntities(final Class<T> type, final Predicate<T> condition) {
        this.entities.removeIf(e -> type.isInstance(e) && condition.test(type.cast(e)));
    }
}
