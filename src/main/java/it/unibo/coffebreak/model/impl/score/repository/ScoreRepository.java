package it.unibo.coffebreak.model.impl.score.repository;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unibo.coffebreak.model.api.score.entry.Entry;
import it.unibo.coffebreak.model.api.score.repository.FileManager;
import it.unibo.coffebreak.model.api.score.repository.Repository;

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

    /** The Folder name used for files. */
    private static final String FOLDER = ".coffeBreak";

    /** The name of the file that stores the serialized leaderboard data. */
    private static final String FILE_NAME = "dk_leaderboard.ser";

    private final FileManager fileManager;

    /**
     * Constructor that ensures the data directory exists.
     */
    public ScoreRepository() {
        this.fileManager = new ScoreFileManager(FOLDER, FILE_NAME);
    }

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
        if (Objects.requireNonNull(list, "The list cannot be null").isEmpty()) {
            return true;
        }

        fileManager.createBackup();

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(fileManager.getDataFile()))) {
            oos.writeObject(list);
            return true;
        } catch (IOException e) {
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
        if (!Files.exists(fileManager.getDataFile())) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(fileManager.getDataFile()))) {
            return (List<Entry>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            if (fileManager.restoreFromBackup()) {
                return load();
            }
            throw new RepositoryException("Error while loading data", e);
        }
    }

    /**
     * Deletes all repository files including the data directory.
     * 
     * @return true if all files were successfully deleted or didn't exist, false
     *         otherwise
     */
    @Override
    public boolean deleteAllFiles() {
        return this.fileManager.deleteAll();
    }

    /**
     * Indicates repository-specific errors during persistence operations.
     * Wraps lower-level IO and serialization exceptions.
     */
    public static class RepositoryException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        /**
         * Creates an exception with detailed context.
         * 
         * @param message the operational context
         */
        public RepositoryException(final String message) {
            super(message);
        }

        /**
         * Creates an exception with detailed context and cause.
         * 
         * @param message the operational context
         * @param cause   the root failure cause
         */
        public RepositoryException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
