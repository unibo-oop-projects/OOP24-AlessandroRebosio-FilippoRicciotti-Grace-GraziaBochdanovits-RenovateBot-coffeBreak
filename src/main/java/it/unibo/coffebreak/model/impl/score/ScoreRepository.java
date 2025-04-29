package it.unibo.coffebreak.model.impl.score;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unibo.coffebreak.model.api.score.Entry;
import it.unibo.coffebreak.model.api.score.Repository;

/**
 * File-based implementation of {@link Repository} for {@link Entry} objects
 * using Java serialization. Stores data in a fixed location in the user's home
 * directory.
 * 
 * This class ensures backup and recovery in case of data corruption or load
 * failure.
 * 
 * @author Alessandro Rebosio
 */
public class ScoreRepository implements Repository<Entry> {

    /** The directory within the user's home where data is stored. */
    public static final File DATA_DIR = new File(System.getProperty("user.home"), ".coffeBreak");

    /** The name of the file that stores the serialized leaderboard data. */
    private static final String FILE_NAME = "dk_leaderboard.ser";

    /** The file extension used for backup files. */
    private static final String BACKUP_EXT = ".bak";

    /** The file that holds the persistent leaderboard data. */
    private static final File DATA_FILE = new File(DATA_DIR, FILE_NAME);

    /**
     * Saves a list of {@link Entry} objects to persistent storage.
     * If the file already exists, a backup is created before overwriting it.
     * 
     * @param list the list of entries to save
     * @return true if the save was successful, false otherwise
     * @throws RepositoryException if an I/O error occurs
     */
    @Override
    public boolean save(final List<Entry> list) {
        Objects.requireNonNull(list, "The list cannot be null");

        if (list.isEmpty()) {
            return true;
        }

        if (DATA_FILE.exists()) {
            this.createBackup();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(list);
            return true;
        } catch (final IOException e) {
            throw new RepositoryException("Error while saving data", e);
        }
    }

    /**
     * Loads the list of {@link Entry} objects from persistent storage.
     * If an error occurs during deserialization, the system attempts to restore
     * from a backup.
     * 
     * @return the list of loaded entries, or an empty list if no data is found
     * @throws RepositoryException if the load fails and backup cannot be restored
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Entry> load() {
        if (!DATA_FILE.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (List<Entry>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            if (this.restoreFromBackup()) {
                return load();
            }
            throw new RepositoryException("Error while loading data", e);
        }
    }

    /**
     * Creates a backup of the current leaderboard file.
     * 
     * @throws RepositoryException if the backup operation fails
     */
    private void createBackup() {
        try {
            Files.copy(DATA_FILE.toPath(),
                    new File(DATA_FILE.getParent(), FILE_NAME + BACKUP_EXT).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (final IOException e) {
            throw new RepositoryException("Backup creation failed", e);
        }
    }

    /**
     * Attempts to restore the leaderboard file from a backup.
     * 
     * @return true if the restore was successful, false otherwise
     * @throws RepositoryException if the restore operation fails
     */
    private boolean restoreFromBackup() {
        final File backupFile = new File(DATA_FILE.getParent(), FILE_NAME + BACKUP_EXT);
        if (backupFile.exists()) {
            try {
                Files.copy(backupFile.toPath(), DATA_FILE.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
                return true;
            } catch (final IOException e) {
                throw new RepositoryException("Backup restore failed", e);
            }
        }
        return false;
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
