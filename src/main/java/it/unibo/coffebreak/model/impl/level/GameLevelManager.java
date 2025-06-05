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
     * The map file should be a text file where each character rapresent an entity.
     * 
     * @throws IllegalStateException if the map file cannot be read or is invalid
     */

    @Override
    public void loadEntities() {
        this.cleanEntities();
        SlopeState slopeState = SlopeState.NO_SLOPE;

        try {
            final List<String> lines = Files.readAllLines(Path.of(getCurrentMapPath()));
            for (int row = 0; row < lines.size(); row++) {
                slopeState = parseLine(lines.get(row), row, P_SIZE, SLOPE, slopeState);
            }
        } catch (final IOException e) {
            throw new IllegalStateException("Unable to load map: " + getCurrentMapPath(), e);
        }
    }

    /**
     * Parses a single line of a map file, creating entities based on characters,
     * and applying slope transformations to their vertical position if needed.
     *
     * <p>
     * Each character in the line represents a different type of entity or slope
     * modifier. Supported characters include:
     * </p>
     * <ul>
     * <li><b>'P'</b> - Platform</li>
     * <li><b>'L'</b> - Ladder</li>
     * <li><b>'M'</b> - Mario (player)</li>
     * <li><b>'D'</b> - Donkey Kong (enemy)</li>
     * <li><b>'R'</b> - Princess (goal)</li>
     * <li><b>'B'</b> - Barrel</li>
     * <li><b>'F'</b> - Fire</li>
     * <li><b>'C'</b> - Coin</li>
     * <li><b>'H'</b> - Hammer</li>
     * <li><b>'T'</b> - Tank</li>
     * <li><b>':'</b> - Toggles upward slope on/off and creates a platform</li>
     * <li><b>';'</b> - Toggles downward slope on/off and creates a platform</li>
     * </ul>
     *
     * <p>
     * The slope state (up, down, or none) is updated if ':' or ';' are encountered,
     * affecting the y-coordinate of entities until toggled again.
     * </p>
     *
     * @param line       the line from the map file to parse
     * @param row        the row index of the line in the map
     * @param pSize      the base size (in pixels) of each map tile
     * @param slopeStep  the amount of vertical offset to apply per column during
     *                   slope
     * @param slopeState the current slope state before parsing the line
     * @return the updated slope state after parsing the line
     * @throws NullPointerException if any of the required parameters are null
     */

    private SlopeState parseLine(final String line, final int row, final int pSize, final float slopeStep,
            final SlopeState slopeState) {
        for (int col = 0; col < line.length(); col++) {
            final char ch = line.charAt(col);
            final float x = col * pSize;
            final float y = row * pSize;

            final Position2D pos = applySlope(x, y, slopeState, slopeStep);

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
                    entities.add(factory.createPlatform(pos));
                    return (slopeState == SlopeState.SLOPE_UP) ? SlopeState.NO_SLOPE : SlopeState.SLOPE_UP;
                }
                case ';' -> {
                    entities.add(factory.createPlatform(pos));
                    return (slopeState == SlopeState.SLOPE_DOWN) ? SlopeState.NO_SLOPE : SlopeState.SLOPE_DOWN;
                }
                default -> {
                    // Ignored
                }
            }
        }
        return slopeState;
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
     * @param slope      float number to which increase or decrease the y-coordinate
     * @return Changed or unchanged Position of the Platform.
     */
    private Position2D applySlope(final float x, final float y, final SlopeState slopeState, final float slope) {
        return switch (slopeState) {
            case SLOPE_UP -> new Position2D(x, y + slope);
            case SLOPE_DOWN -> new Position2D(x, y - slope);
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
