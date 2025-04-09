package it.unibo.coffebreak.model.api.score;

import java.util.List;

import it.unibo.coffebreak.model.impl.score.ScoreRepository.RepositoryException;

/**
 * Defines a persistence mechanism for storing and retrieving collections of
 * elements.
 * Implementations may use various storage backends (files, databases, etc.) and
 * should
 * handle serialization/deserialization of elements.
 *
 * @param <X> the type of elements to be stored, must be
 *            {@link java.io.Serializable}
 *            if binary persistence is used
 * 
 * @author Alessandro Rebosio
 */
public interface Repository<X> {
    /**
     * Persists the complete collection of elements, replacing any existing data.
     *
     * @param list the elements to store (cannot contain null elements)
     * @return true if persistence succeeded, false otherwise
     * @throws NullPointerException if list is null
     * @throws RepositoryException  if serialization or storage fails
     */
    boolean save(List<X> list);

    /**
     * Retrieves all persisted elements.
     *
     * @return a new list containing all stored elements, empty list if none exist
     * @throws RepositoryException if deserialization or read operation fails
     */
    List<X> load();
}
