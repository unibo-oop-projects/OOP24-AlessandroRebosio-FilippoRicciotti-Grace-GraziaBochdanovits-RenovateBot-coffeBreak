package it.unibo.coffebreak.impl.model.level.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.collectible.Collectible;
import it.unibo.coffebreak.api.model.entities.enemy.Enemy;
import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.collectible.coin.Coin;
import it.unibo.coffebreak.impl.model.entities.collectible.hammer.Hammer;
import it.unibo.coffebreak.impl.model.entities.enemy.fire.GameFire;
import it.unibo.coffebreak.impl.model.entities.mario.Mario;
import it.unibo.coffebreak.impl.model.entities.npc.donkeykong.DonkeyKong;
import it.unibo.coffebreak.impl.model.entities.npc.pauline.Pauline;
import it.unibo.coffebreak.impl.model.entities.structure.ladder.normal.NormalLadder;
import it.unibo.coffebreak.impl.model.entities.structure.platform.breakable.BreakablePlatform;
import it.unibo.coffebreak.impl.model.entities.structure.platform.normal.NormalPlatform;
import it.unibo.coffebreak.impl.model.entities.structure.tank.GameTank;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.model.level.entity.EntityManager;

/**
 * Implementation of the {@link EntityManager} interface for managing game
 * entities.
 * 
 * @author Filippo Ricciotti
 */
public class GameEntityManager implements EntityManager {

    private static final float SLOPE_VAL = 0.06f;
    private final List<Entity> entities = new LinkedList<>();
    private final MainCharacter character = new Mario(new Position(0, 0), new BoundigBox());

    private int row;
    private int column;

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
    public void loadEntities(final List<String> map, final boolean canDonkeyThrowBarrel) {
        this.entities.clear();

        this.row = map.size();
        this.column = map.isEmpty() ? 0 : map.get(0).length();
        final List<Character> ids = List.of('M', 'D', 'R', 'T');
        final Map<Character, List<Integer>> sizes = findSize(map, ids);

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
                    trueY -= (x % 2 == 0) ? offsetY : 0; // TODO: Consider making the sloping platform always an even
                                                         // number inside map
                    c = activeSlope ? c : 'P';
                    activeSlope = true;

                }

                final Position position = new Position(x, trueY).scalePosition(new BoundigBox());
                BoundigBox bb = new BoundigBox();

                if (sizes.containsKey(c) && ids.contains(c)) {
                    final int w = sizes.get(c).get(0);
                    final int h = sizes.get(c).get(1);
                    bb = new BoundigBox().scaleWidth(w).scaleHeight(h);
                    sizes.remove(c);
                } else if (ids.contains(c)) {
                    c = '.';
                }

                switch (Character.toUpperCase(c)) {
                    case 'R' -> this.addEntity(new Pauline(position, bb));
                    case 'P' -> this.addEntity(new NormalPlatform(position, bb));
                    case '!' -> this.addEntity(new BreakablePlatform(position, bb));
                    // TODO: metter flag sensati
                    case 'M' -> {
                        this.character.setPosition(position); // TODO: ask rebo if its ok to move here new Mario()
                        this.addEntity(this.character);
                    }
                    case 'D' ->
                        this.addEntity(new DonkeyKong(position, bb, canDonkeyThrowBarrel));
                    case 'T' -> this.addEntity(new GameTank(position, bb));
                    case 'H' -> this.addEntity(new Hammer(position, bb));
                    case 'C' -> this.addEntity(new Coin(position, bb));
                    case 'L' -> this.addEntity(new NormalLadder(position, bb));
                    default -> {
                    }
                }
            }
        }
    }

    /**
     * Method used to find sizes of the entities by counting how many times is their
     * corresponding char present inside the map.
     * 
     * @param map the map representation to load entities from
     * @param c   List of Character of which we have to find width and height
     * @return Map with the character and the corresponding width and height
     */
    private Map<Character, List<Integer>> findSize(final List<String> map, final List<Character> c) {
        final Map<Character, List<Integer>> sizes = new HashMap<>();
        for (int y = 0; y < map.size(); y++) {
            final String line = map.get(y);
            for (int x = 0; x < line.length(); x++) {
                final char k = line.charAt(x);

                if (c.contains(k) && !sizes.containsKey(k)) {
                    int width = 1;
                    while (x + width < line.length() && line.charAt(x + width) == k) {
                        width++;
                    }

                    int height = 1;
                    while (y + height < row && map.get(y + height).charAt(x) == k) {
                        height++;
                    }
                    x += width;
                    sizes.put(k, List.of(width, height));
                }

            }
        }

        return sizes;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColumn() {
        return column;
    }
}
