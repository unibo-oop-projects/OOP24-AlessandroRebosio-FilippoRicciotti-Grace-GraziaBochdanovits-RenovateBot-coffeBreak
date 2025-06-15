package it.unibo.coffebreak.impl.model.level.entity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.collectible.Collectible;
import it.unibo.coffebreak.api.model.entities.enemy.Enemy;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.impl.common.Dimension;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.enemy.fire.GameFire;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.model.entities.npc.pauline.Pauline;
import it.unibo.coffebreak.impl.model.entities.structure.ladder.normal.NormalLadder;
import it.unibo.coffebreak.impl.model.entities.structure.platform.breakable.BreakablePlatform;
import it.unibo.coffebreak.impl.model.entities.structure.platform.normal.NormalPlatform;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.model.level.entity.EntityManager;

/**
 * Implementation of the {@link EntityManager} interface for managing game
 * entities.
 * 
 * @author Filippo Ricciotti
 */
public class GameEntityManager implements EntityManager {

    private final List<Entity> entities = new LinkedList<>();
    private static final float SLOPE_VAL = 0.06f;

    private MainCharacter character;

    /**
     * {@inheritDoc}
     * Returns an unmodifiable list of all entities managed by this manager.
     */
    @Override
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    /**
     * {@inheritDoc}
     * Returns an Optional containing the main character if present.
     */
    @Override
    public Optional<MainCharacter> getMainCharacter() {
        return Optional.ofNullable(this.character);
    }

    /**
     * {@inheritDoc}
     * Loads entities from the provided map representation.
     * Not yet implemented.
     *
     * @param map the map representation to load entities from
     */
    @Override
    public void loadEntities(final List<String> map) {
        this.entities.clear();
        for (int y = 0; y < map.size(); y++) {
            final String line = map.get(y);
            float offsetY = 0;
            boolean activeSlope = false;
            float trueY = y;

            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c == ';' || c == ':' || activeSlope) {
                    if (c == ':') {
                        offsetY = SLOPE_VAL;
                    } else if (c == ';') {
                        offsetY = -SLOPE_VAL;
                    }
                    trueY -= offsetY;
                    c = (activeSlope) ? c : 'P';
                    activeSlope = true;

                }

                final Position position = new Position(x, trueY).scalePosition(new Dimension());

                switch (Character.toUpperCase(c)) {
                    case 'R' -> this.addEntity(new Pauline(position, new Dimension()));
                    case 'P' -> this.addEntity(new NormalPlatform(position, new Dimension()));
                    case '!' -> this.addEntity(new BreakablePlatform(position, new Dimension()));
                    case 'M' -> {
                        this.character = new Mario(position, new Dimension());
                        this.addEntity(this.character);
                    }
                    case 'L' -> this.addEntity(new NormalLadder(position, new Dimension()));
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
     * Adds a non-null entity to the manager.
     *
     * @param entity the entity to add
     * @return true if the entity was added successfully
     * @throws NullPointerException if the entity is null
     */
    @Override
    public boolean addEntity(final Entity entity) {
        return this.entities.add(Objects.requireNonNull(entity, "The entity cannot be null"));
    }

    /**
     * {@inheritDoc}
     * Transforms certain entities (e.g., barrels that can transform to fire),
     * removes collected collectibles, broken platforms, and destroyed enemies,
     * and adds new entities as needed.
     */
    @Override
    public void transformEntities() {
        final List<? extends Entity> toAdd = entities.stream()
                .filter(e -> e instanceof final Barrel barrel && barrel.canTransformToFire())
                .map(barrel -> new GameFire(barrel.getPosition(), barrel.getDimension()))
                .toList();

        this.entities.removeIf(e -> (e instanceof final Collectible collectible && collectible.isCollected())
                || (e instanceof final Platform platform && platform.isBroken())
                || (e instanceof final Enemy enemy && enemy.isDestroyed()));

        this.entities.addAll(toAdd);
    }
}
