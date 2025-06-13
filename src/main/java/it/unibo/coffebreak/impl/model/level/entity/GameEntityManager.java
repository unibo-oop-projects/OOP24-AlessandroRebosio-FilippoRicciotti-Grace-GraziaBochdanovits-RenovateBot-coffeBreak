package it.unibo.coffebreak.impl.model.level.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.EntityFactory;
import it.unibo.coffebreak.api.model.entities.collectible.Collectible;
import it.unibo.coffebreak.api.model.entities.enemy.Enemy;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.api.model.entities.npc.Antagonist;
import it.unibo.coffebreak.api.model.entities.npc.Princess;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.api.model.level.entity.EntityManager;
import it.unibo.coffebreak.impl.common.Dimension;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;
import it.unibo.coffebreak.impl.model.entities.GameEntityFactory;
import it.unibo.coffebreak.impl.model.entities.collectible.hammer.Hammer;
import it.unibo.coffebreak.impl.model.entities.npc.donkeykong.DonkeyKong;
import it.unibo.coffebreak.impl.model.entities.npc.pauline.Pauline;
import it.unibo.coffebreak.impl.model.entities.structure.ladder.normal.NormalLadder;
import it.unibo.coffebreak.impl.model.entities.structure.platform.breakable.BreakablePlatform;
import it.unibo.coffebreak.impl.model.entities.structure.platform.normal.NormalPlatform;
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
    public Optional<MainCharacter> getPlayer() {
        return Optional.of(this.player);
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
                final Position position = new Position(x * 25, y * 25);
                final char c = line.charAt(x);

                switch (Character.toUpperCase(c)) {
                    case 'P' -> this.addEntity(new NormalPlatform(position, new Dimension()));
                    case '!' -> this.addEntity(new BreakablePlatform(position, new Dimension()));
                    case 'L' -> this.addEntity(new NormalLadder(position, new Dimension()));
                    case 'T' -> {
                        if (!this.hasEntityOfClass(Tank.class)) {
                            this.addEntity(factory.createTank(position, new Dimension().mulHeight(2)));
                        }
                    }
                    case 'H' -> this.addEntity(new Hammer(position, new Dimension()));
                    case 'C' -> this.addEntity(factory.createCoin(position));
                    case 'M' -> {
                        if (!this.hasEntityOfClass(MainCharacter.class)) {
                            this.player = factory.createMario(position, new Dimension().mulHeight(2));
                            this.addEntity(this.player);
                        }
                    }
                    case 'D' -> {
                        if (!this.hasEntityOfClass(Antagonist.class)) {
                            this.addEntity(new DonkeyKong(position, new Dimension().mul(5), false));
                        }
                    }
                    case 'R' -> {
                        if (!this.hasEntityOfClass(Princess.class)) {
                            this.addEntity(new Pauline(position, new Dimension().mulHeight(3).mulWidth(2)));
                        }
                    }
                    case '.' -> new AbstractEntity(position, new Dimension()) {

                        @Override
                        public void onCollision(Entity other) {
                            // TODO Auto-generated method stub
                            throw new UnsupportedOperationException("Unimplemented method 'onCollision'");
                        }

                    };
                    default -> {
                        // TODO: fix Slope(: and ;) and Position of the entitiy dont use
                        // GameEntityFactory.DEFAULT_BOUNDINGBOX use default dim of entity
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
        this.removeEntities(Platform.class, Platform::isBroken);
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
        final List<Barrel> barrelsToTransform = this.entities.stream()
                .filter(Barrel.class::isInstance)
                .map(Barrel.class::cast)
                .filter(Barrel::canTransformToFire)
                .toList();
        barrelsToTransform.forEach(t -> this.addEntity(factory.createFire(t.getPosition())));
    }

    /**
     * Checks if there is already an entity of the given class present.
     *
     * @param type the class to check for
     * @return true if at least one entity of the given class exists, false
     *         otherwise
     */
    private boolean hasEntityOfClass(final Class<? extends Entity> type) {
        return entities.stream().anyMatch(type::isInstance);
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
