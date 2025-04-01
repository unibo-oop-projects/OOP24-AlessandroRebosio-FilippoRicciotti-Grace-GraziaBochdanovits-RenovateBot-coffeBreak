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
 * Implementation of {@link Repository} for storing and retrieving score
 * entries.
 * Uses Java serialization to persist data to a file in the user's home
 * directory.
 */
public class ScoreRepository implements Repository<Entry> {

    /**
     * The name of the file where the leaderboard data will be permanently stored.
     */
    private static final String FILE_NAME = "leaderBoard.ser";

    /**
     * The File object representing the permanent data storage location.
     * The file is located in the user's home directory.
     */
    private final File dataFile;

    /**
     * Constructs a new ScoreRepository.
     * The data file will be created in the user's home directory.
     */
    public ScoreRepository() {
        this.dataFile = new File(System.getProperty("user.home"), FILE_NAME);
    }

    /**
     * Saves a list of entries to the repository.
     *
     * @param list list the list of elements to be saved (cannot be {@code null}).
     * @return {@code true} if the operation succeeded, {@code false} otherwise.
     * @throws NullPointerException if {@code list} is {@code null}.
     * @throws RepositoryException  if an error occurs during the save operation
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
     * Loads all entries from the repository.
     *
     * @return a list of all entries stored in the repository, or an empty list if
     *         the repository is empty
     * @throws RepositoryException if an error occurs during the load operation
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
     * Exception thrown when an error occurs in repository operations.
     */
    public static class RepositoryException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        /**
         * Constructs a new RepositoryException with the specified detail message and
         * cause.
         *
         * @param message the detail message
         * @param cause   the cause of the exception
         */
        public RepositoryException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
