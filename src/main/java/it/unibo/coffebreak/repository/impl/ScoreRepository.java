package it.unibo.coffebreak.repository.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import it.unibo.coffebreak.repository.api.Repository;
import it.unibo.coffebreak.repository.api.filemanager.FileManager;
import it.unibo.coffebreak.repository.impl.filemanager.GameFileManager;

/**
 * A file-based repository implementation using Java serialization
 * for storing and loading Entry objects. Supports backup and recovery.
 * 
 * @param <T> the type of elements to be persisted
 * 
 * @author Alessandro Rebosio
 */
public class ScoreRepository<T> implements Repository<T> {

    private static final String FOLDER = ".coffeBreak";
    private static final String FILE_NAME = "dk_leaderboard.ser";

    private final FileManager fileManager;
    private final Supplier<T> defaultSupplier;

    /**
     * Constructs a new {@code ScoreRepository} instance using a default file
     * manager and a supplier to provide default values when no data is available.
     *
     * @param defaultSupplier a {@link Supplier} that provides a default instance
     *                        of {@code T} when no data file exists or recovery
     *                        fails; must not be {@code null}
     * @throws NullPointerException if {@code defaultSupplier} is {@code null}
     */
    public ScoreRepository(final Supplier<T> defaultSupplier) {
        this.fileManager = new GameFileManager(FOLDER, FILE_NAME);
        this.defaultSupplier = Objects.requireNonNull(defaultSupplier, "Default supplier cannot be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(final T data) {
        Objects.requireNonNull(data, "List cannot be null");
        if (data instanceof final List list && list.isEmpty()) {
            return true;
        }

        fileManager.createBackup();

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(fileManager.getDataFile()))) {
            oos.writeObject(data);
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
    public T load() {
        if (!Files.exists(fileManager.getDataFile())) {
            return defaultSupplier.get();
        }

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(fileManager.getDataFile()))) {
            return (T) ois.readObject();
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
