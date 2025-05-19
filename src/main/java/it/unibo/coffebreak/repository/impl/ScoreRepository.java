package it.unibo.coffebreak.repository.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unibo.coffebreak.model.api.score.entry.Entry;
import it.unibo.coffebreak.repository.api.Repository;
import it.unibo.coffebreak.repository.api.manager.FileManager;
import it.unibo.coffebreak.repository.impl.manager.GameFileManager;

/**
 * A file-based repository implementation using Java serialization
 * for storing and loading Entry objects. Supports backup and recovery.
 */
public class ScoreRepository implements Repository<Entry> {

    private static final String FOLDER = ".coffeBreak";
    private static final String FILE_NAME = "dk_leaderboard.ser";

    private final FileManager fileManager;

    /**
     * Constructs a new ScoreRepository with default file manager.
     */
    public ScoreRepository() {
        this.fileManager = new GameFileManager(FOLDER, FILE_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(final List<Entry> list) {
        Objects.requireNonNull(list, "List cannot be null");
        if (list.isEmpty()) {
            return true;
        }

        fileManager.createBackup();

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(fileManager.getDataFile()))) {
            oos.writeObject(list);
            return true;
        } catch (final IOException e) {
            throw new RepositoryException("Error while saving data", e);
        }
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public boolean deleteAllFiles() {
        return fileManager.deleteAll();
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
