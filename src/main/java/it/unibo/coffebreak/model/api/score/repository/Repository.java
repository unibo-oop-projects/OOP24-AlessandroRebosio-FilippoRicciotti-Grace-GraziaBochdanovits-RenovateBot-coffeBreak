package it.unibo.coffebreak.model.api.score.repository;

import java.util.List;

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
     */
    boolean save(List<X> list);

    /**
     * Retrieves all persisted elements.
     *
     * @return a new list containing all stored elements, empty list if none exist
     */
    List<X> load();

    public boolean deleteAllFiles();
}
