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
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.level.cleaner.EntityCleaner;

/**
 * Implementation of the {@link LevelManager} interface.
 * Manages entities for the current game level and handles entity cleanup,
 * reset,
 * and transitions between maps and levels.
 */
public class GameLevelManager implements LevelManager {

    private final List<Entity> entities;
    private final Cleaner cleanup;
    private static final String PATH_MAP1 = "maps/Map1.txt";
    private static final int P_SIZE = 10;

    /**
     * Constructs a new {@code GameLevelManager} with an empty entity list
     * and a default {@link EntityCleaner} to manage entity cleanup.
     */
    public GameLevelManager() {
        this.cleanup = new EntityCleaner();
        this.entities = new ArrayList<>();
    }

    /**
     * Loads the entities for the current level.
     * This method is intended to populate the entity list from an external source,
     * such as a file or a level descriptor.
     * <p>
     * Currently unimplemented.
     */
    @Override
    public void loadEntities() {
        // TODO: load entities from a file or level descriptor
        try {
            List<String> lines = Files.readAllLines(Path.of(PATH_MAP1));
            for (int row = 0; row < lines.size(); row++) {
                String line = lines.get(row);
                for (int col = 0; col < line.length(); col++) {
                    char ch = line.charAt(col);
                    float x = col * P_SIZE;
                    float y = row * P_SIZE;
                    Position2D pos = new Position2D(x, y);

                    switch (ch) {// TODO: use Factory
                        case 'P' -> entities.add(null);
                        case 'L' -> entities.add(null);
                        case 'M' -> entities.add(null);
                        case 'D' -> entities.add(null);
                        case 'Q' -> entities.add(null);
                        case 'B' -> entities.add(null);
                        case 'F' -> entities.add(null);
                        case 'C' -> entities.add(null);
                        case 'H' -> entities.add(null);
                        case 'T' -> entities.add(null);

                        default -> {
                            /* ignorato */ }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
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
