package it.unibo.coffebreak.model.score.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unibo.coffebreak.model.score.api.Entry;
import it.unibo.coffebreak.model.score.api.Repository;

/**
 * File-based implementation of {@link Repository} for {@link Entry} objects
 * using Java serialization.
 * Stores data in a fixed location in the user's home directory. This
 * implementation:
 * <ul>
 * <li>Is thread-safe for concurrent access</li>
 * <li>Creates the storage file on first write</li>
 * <li>Maintains data consistency through atomic write operations</li>
 * </ul>
 * 
 * @implNote Serialization format considerations:
 *           <ul>
 *           <li>All stored entries must remain serializable</li>
 *           <li>Changing Entry class structure may break compatibility</li>
 *           </ul>
 */
public class ScoreRepository implements Repository<Entry> {

    /**
     * Default filename for storage ({@value}).
     * Located in user's home directory for cross-platform compatibility.
     */
    private static final String FILE_NAME = "leaderBoard.ser";

    /**
     * The persistent storage location.
     * 
     * @implSpec Final to ensure thread-safe publication
     */
    private final File dataFile;

    /**
     * Creates a new repository using default storage location.
     * The actual file won't be created until first save operation.
     */
    public ScoreRepository() {
        this.dataFile = new File(System.getProperty("user.home"), FILE_NAME);
    }

    /**
     * {@inheritDoc}
     * 
     * @implSpec This implementation:
     *           <ul>
     *           <li>Performs atomic write-through</li>
     *           <li>Overwrites existing data completely</li>
     *           <li>Creates parent directories if needed</li>
     *           </ul>
     */
    @Override
    public boolean save(final List<Entry> list) {
        Objects.requireNonNull(list, "The list cannot be null");

        if (list.isEmpty()) {
            return true;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.dataFile))) {
            oos.writeObject(list);
            return true;
        } catch (final IOException e) {
            throw new RepositoryException("Error while saving data", e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @implSpec This implementation:
     *           <ul>
     *           <li>Returns empty list for non-existent file</li>
     *           <li>Creates defensive copies of loaded data</li>
     *           <li>Validates deserialized types</li>
     *           </ul>
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Entry> load() {
        if (!this.dataFile.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.dataFile))) {
            return new ArrayList<>((List<Entry>) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RepositoryException("Error while loading data", e);
        }
    }

    /**
     * Indicates repository-specific errors during persistence operations.
     * Wraps lower-level IO and serialization exceptions.
     */
    public static class RepositoryException extends RuntimeException {
        /**
         * Serial version UID for consistent deserialization.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Creates an exception with detailed context.
         * 
         * @param message the operational context
         * @param cause   the root failure cause
         */
        public RepositoryException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
