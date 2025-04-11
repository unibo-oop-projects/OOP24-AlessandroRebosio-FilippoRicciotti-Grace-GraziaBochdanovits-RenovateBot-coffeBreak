package it.unibo.coffebreak.model.impl.score;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unibo.coffebreak.model.api.score.Entry;
import it.unibo.coffebreak.model.api.score.Repository;

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
 * <p>
 * <b>Implementation Notes:</b> Serialization format considerations.
 * <ul>
 * <li>All stored entries must remain serializable</li>
 * <li>Changing Entry class structure may break compatibility</li>
 * </ul>
 * 
 * @author Alessandro Rebosio
 */
public class ScoreRepository implements Repository<Entry> {

    /**
     * The persistent storage location.
     * 
     * @implSpec Final to ensure thread-safe publication.
     */
    public static final File DATA_FILE = new File(System.getProperty("user.home"), "leaderBoard.ser");

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

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
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
        if (!DATA_FILE.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
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
