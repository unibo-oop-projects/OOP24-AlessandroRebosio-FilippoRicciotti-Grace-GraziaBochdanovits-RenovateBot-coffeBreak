package it.unibo.coffebreak.model.impl.level;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.level.LevelManager;
import it.unibo.coffebreak.model.api.level.cleaner.Cleaner;
import it.unibo.coffebreak.model.impl.GameEntityFactory;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.level.cleaner.EntityCleaner;

/**
 * Implementation of the {@link LevelManager} interface.
 * Manages entities for the current game level and handles entity cleanup,
 * reset,
 * and transitions between maps and levels.
 */
public class GameLevelManager implements LevelManager {

    static final String PATH_MAP1 = "maps/Map1.txt";// TODO: implement a method GetNextMapPath()
    private static final int P_SIZE = 10;
    private static final float SLOPE = 0.3f;
    private final List<Entity> entities;
    private final Cleaner cleanup;
    private final GameEntityFactory factory;

    /**
     * Constructs a new {@code GameLevelManager} with an empty entity list
     * and a default {@link EntityCleaner} to manage entity cleanup.
     */
    public GameLevelManager() {
        this.cleanup = new EntityCleaner();
        this.entities = new ArrayList<>();
        this.factory = new GameEntityFactory();
    }

    /**
     * Loads entities from the current map file.
     * The map file should be a text file where each character represents:
     * P - Platform
     * L - Ladder
     * M - Mario (player)
     * D - Donkey Kong (enemy)
     * R - Princess (objective)
     * B - Barrel (obstacle/enemy)
     * F - Fire (enemy)
     * C - Coin (collectible)
     * H - Hammer (power-up)
     * T - Tank (enemy)
     * : - Start/end upward slope
     * ; - Start/end downward slope
     * 
     * @throws IllegalStateException if the map file cannot be read or is invalid
     */

    @Override
    public void loadEntities() {
        this.cleanEntities();
        boolean slopeDown = false;
        boolean slopeUp = false;
        Position2D pos;
        try {
            final List<String> lines = Files.readAllLines(Path.of(PATH_MAP1));
            for (int row = 0; row < lines.size(); row++) {
                final String line = lines.get(row);
                for (int col = 0; col < line.length(); col++) {
                    final char ch = line.charAt(col);
                    final float x = col * P_SIZE;
                    final float y = row * P_SIZE;

                    pos = switchSlope(x, y, slopeUp, slopeDown);

                    switch (ch) {
                        case 'P' -> entities.add(factory.createPlatform(pos));
                        case 'L' -> entities.add(factory.createLadder(pos));
                        case 'M' -> entities.add(factory.createMario(pos));
                        case 'D' -> entities.add(factory.createDonkeyKong(pos));
                        case 'R' -> entities.add(factory.createPrincess(pos));
                        case 'B' -> entities.add(factory.createBarrel(pos));
                        case 'F' -> entities.add(factory.createFire(pos));
                        case 'C' -> entities.add(factory.createCoin(pos));
                        case 'H' -> entities.add(factory.createHammer(pos));
                        case 'T' -> entities.add(factory.createTank(pos));
                        case ':' -> {
                            slopeUp = !slopeUp;
                            entities.add(factory.createPlatform(pos));
                        }
                        case ';' -> {
                            slopeDown = !slopeDown;
                            entities.add(factory.createPlatform(pos));
                        }
                        default -> {
                            /* ignorato */ }
                    }
                }
            }

        } catch (final IOException e) {
            throw new IllegalStateException("Unable find Map", e);
        }

    }

    /**
     * method that applies (if neaded) the increase, decrease of the y-coordinate of
     * a platform depending on the boolean values of slopeUp and slopeDown.
     * 
     * @param x         the x-coordinate of the position.
     * @param y         the y-coordinate of the position.
     * @param slopeUp   boolean value that indicates that the y-coordinate has to
     *                  increase.
     * @param slopeDown boolean value that indicates that the y-coordinate has to
     *                  decrease.
     * @return Changed or unchanged Position of the Platform.
     */
    private Position2D switchSlope(final float x, final float y, final boolean slopeUp, final boolean slopeDown) {
        if (slopeDown) {
            return new Position2D(x, y - SLOPE);
        } else if (slopeUp) {
            return new Position2D(x, y + SLOPE);
        } else {
            return new Position2D(x, y);
        }
    }

    /**
     * Adds an entity to the current level.
     *
     * @param entity the entity to add (must not be null)
     * @return true if the entity was added successfully, false otherwise
     * @throws NullPointerException if the entity is null
     */
    @Override
    public boolean addEntity(final Entity entity) {
        Objects.requireNonNull(entity, "The entity cannot be null");
        return this.entities.add(entity);
    }

    /**
     * Removes an entity from the current level.
     *
     * @param entity the entity to remove (must not be null)
     * @return true if the entity was removed successfully, false otherwise
     * @throws NullPointerException if the entity is null
     */
    @Override
    public boolean removeEntity(final Entity entity) {
        Objects.requireNonNull(entity, "The entity cannot be null");
        return this.entities.remove(entity);
    }

    /**
     * Cleans the current list of entities by removing destroyed enemies
     * and collected collectibles using the configured {@link Cleaner}.
     */
    @Override
    public void cleanEntities() {
        this.cleanup.clean(this.entities);
    }

    /**
     * Resets all entities in the current level by reloading them.
     * This calls {@link #loadEntities()} to reload the entity list.
     */
    @Override
    public void resetEntities() {
        this.loadEntities();
    }

    /**
     * Advances to the next map within the current level.
     * <p>
     * Currently unimplemented.
     */
    @Override
    public void nextMap() {
        // TODO: implement map switching logic
    }

    /**
     * Advances to the next level.
     * <p>
     * Currently unimplemented.
     */
    @Override
    public void nextLevel() {
        // TODO: implement level progression logic
    }
}
