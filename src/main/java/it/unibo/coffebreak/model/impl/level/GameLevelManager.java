package it.unibo.coffebreak.model.impl.level;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.level.LevelManager;
import it.unibo.coffebreak.model.api.level.cleaner.Cleaner;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.GameEntityFactory;
import it.unibo.coffebreak.model.impl.level.cleaner.EntityCleaner;

/**
 * Implementation of the {@link LevelManager} interface.
 * Manages entities for the current game level and handles entity cleanup,
 * reset,
 * and transitions between maps and levels.
 * 
 * @author Filippo Ricciotti
 */
public class GameLevelManager implements LevelManager {

    /**
     * Represents the current slope state for platform positioning.
     * <p>
     * This enum is used to determine how the vertical coordinate (y)
     * of a platform should be adjusted while parsing a level map.
     * </p>
     * 
     * <ul>
     * <li>{@link #NO_SLOPE} - No slope is currently active. Platforms are placed
     * next to each other.</li>
     * <li>{@link #SLOPE_UP} - Platforms are placed with a gradual upward shift (y
     * increases).</li>
     * <li>{@link #SLOPE_DOWN} - Platforms are placed with a gradual downward shift
     * (y decreases).</li>
     * </ul>
     * 
     * This state is toggled by the character <b>:</b> and <b>;</b> inside the map
     * file.
     */
    private enum SlopeState {
        NO_SLOPE,
        SLOPE_UP,
        SLOPE_DOWN
    }

    private static final String MAP_PREFIX = "maps/Map";
    private static final String MAP_SUFFIX = ".txt";

    private static final int P_SIZE = 10;
    private static final float SLOPE = 0.3f;
    private final List<Entity> entities;
    private final Cleaner cleanup;
    private final GameEntityFactory factory;
    private int currentMapNumber;

    /**
     * Constructs a new {@code GameLevelManager} with an empty entity list
     * and a default {@link EntityCleaner} to manage entity cleanup.
     */
    public GameLevelManager() {
        this.cleanup = new EntityCleaner();
        this.entities = new ArrayList<>();
        this.factory = new GameEntityFactory();
        this.currentMapNumber = 1;
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
        SlopeState slopeState = SlopeState.NO_SLOPE;
        Position2D pos;
        try {
            final List<String> lines = Files.readAllLines(Path.of(getCurrentMapPath()));
            for (int row = 0; row < lines.size(); row++) {
                final String line = lines.get(row);
                for (int col = 0; col < line.length(); col++) {
                    final char ch = line.charAt(col);
                    final float x = col * P_SIZE;
                    final float y = row * P_SIZE;

                    pos = applySlope(x, y, slopeState);

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
                            if (slopeState == SlopeState.SLOPE_UP) {
                                slopeState = SlopeState.NO_SLOPE;
                            } else {
                                slopeState = SlopeState.SLOPE_UP;
                            }
                            entities.add(factory.createPlatform(pos));
                        }
                        case ';' -> {
                            if (slopeState == SlopeState.SLOPE_DOWN) {
                                slopeState = SlopeState.NO_SLOPE;
                            } else {
                                slopeState = SlopeState.SLOPE_DOWN;
                            }
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
     * {@inheritDoc}
     */
    @Override
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(this.entities);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException filter must not be null
     */
    @Override
    public void removeAll(final Predicate<Entity> filter) {
        Objects.requireNonNull(filter, "Predicate must not be null");
        this.entities.removeIf(filter);

    }

    /**
     * method that applies (if neaded) the increase, decrease of the y-coordinate of
     * a platform depending on the boolean values of slopeUp and slopeDown.
     * 
     * @param x          the x-coordinate of the position.
     * @param y          the y-coordinate of the position.
     * @param slopeState enum of the slope the method has to consider
     * @return Changed or unchanged Position of the Platform.
     */
    private Position2D applySlope(final float x, final float y, final SlopeState slopeState) {
        return switch (slopeState) {
            case SLOPE_UP -> new Position2D(x, y + SLOPE);
            case SLOPE_DOWN -> new Position2D(x, y - SLOPE);
            case NO_SLOPE -> new Position2D(x, y);
        };
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
     * Returns the file path of the current map.
     * 
     * @return Path of the current Map
     */
    public String getCurrentMapPath() {
        return MAP_PREFIX + currentMapNumber + MAP_SUFFIX;
    }

    /**
     * Advances to the next map, loading the corresponding entity layout.
     * If the next map file doesn't exist, an exception is thrown.
     *
     * @throws IllegalStateException if the next map file does not exist
     */

    @Override
    public void nextMap() {
        final String nextMapPath = getNextMapPath();
        if (!Files.exists(Path.of(nextMapPath))) {
            throw new IllegalStateException("Next map file does not exist: " + nextMapPath);
        }
        currentMapNumber++;
        this.loadEntities();
    }

    /**
     * Returns the file path of the next map without loading it.
     * 
     * @return Path of the next Map to be played
     */
    public String getNextMapPath() {
        return MAP_PREFIX + currentMapNumber + MAP_SUFFIX;
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
