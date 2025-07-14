package it.unibo.coffebreak.impl.model.level.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

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

    private final List<Entity> entities = new LinkedList<>();
    private MainCharacter character;

    private int row;
    private int column;

    /**
     * Constructs a new {@code GameEntityManager} and initializes the character
     * state
     * by invoking {@link #resetCharacter()}.
     */
    public GameEntityManager() {
        this.resetCharacter();
    }

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

            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);

                final Position position = new Position(x, y).scalePosition(new BoundigBox());
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
                    case 'P' -> {
                        final int nextRow = y + 1;
                        final boolean canGoDown = nextRow < map.size() && hasLadder(map.get(nextRow), x);

                        if (canGoDown) {
                            this.addEntity(new NormalPlatform(position, bb, canGoDown));
                        } else {
                            this.addEntity(new NormalPlatform(position, bb));
                        }

                    }
                    case '!' -> this.addEntity(new BreakablePlatform(position, bb));
                    case 'M' -> {
                        this.character.setPosition(position);
                        this.addEntity(this.character);
                    }
                    case 'D' ->
                        this.addEntity(new DonkeyKong(position, bb, canDonkeyThrowBarrel));
                    case 'F' -> this.addEntity(new GameFire(position, bb));
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
    public final void resetCharacter() {
        this.character = new Mario(new Position(0, 0), new BoundigBox());
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
        IntStream.range(0, map.size()).forEach(y -> IntStream.range(0, map.get(y).length()).forEach(x -> {
            final char k = map.get(y).charAt(x);

            if (c.contains(k) && !sizes.containsKey(k)) {
                final int startX = x;

                final int width = (int) map.get(y).substring(startX) // counts consecutive chars on the x-coord
                        .chars()
                        .takeWhile(ch -> ch == k)
                        .count();

                final int height = (int) map.subList(y, map.size()) // counts consecutive chars on the y-coord
                        .stream()
                        .takeWhile(r -> r.charAt(startX) == k)
                        .count();

                sizes.put(k, List.of(width, height));
            }
        }));

        return sizes;

    }

    /**
     * Method that can tell wether a ladder is present at the given
     * coordinates.
     * 
     * @param x   coordinate
     * @param map String containing the elements below
     * @return True if at these coordinates is present a Ladder False otherwise
     */
    private boolean hasLadder(final String map, final int x) {
        return map.charAt(x) == 'L';
    }
}
