package it.unibo.coffebreak.repository.api;

import java.util.List;

/**
 * Defines a repository interface for storing and retrieving elements of a given
 * type.
 * Implementations are responsible for the persistence mechanism.
 *
 * @param <T> the type of elements to be persisted
 * 
 * @author Alessandro Rebosio
 */
public interface Repository<T> {

    /**
     * Saves the provided list of elements, replacing existing data.
     *
     * @param list the list of elements to save
     * @return true if save was successful, false otherwise
     * @throws NullPointerException if the list is null
     */
    boolean save(List<T> list);

    /**
     * Loads all elements from persistent storage.
     *
     * @return a list of elements, or an empty list if none exist
     */
    List<T> load();

    /**
     * Deletes all data files used by the repository.
     *
     * @return true if deletion was successful, false otherwise
     */
    boolean deleteAllFiles();
}
