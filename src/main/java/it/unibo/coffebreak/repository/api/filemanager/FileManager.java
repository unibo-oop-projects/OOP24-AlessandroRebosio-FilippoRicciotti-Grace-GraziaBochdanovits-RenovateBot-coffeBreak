package it.unibo.coffebreak.repository.api.filemanager;

import java.nio.file.Path;

/**
 * Interface defining basic file management operations such as retrieving,
 * backing up, restoring, and deleting files.
 * 
 * @author Alessandro Rebosio
 */
public interface FileManager {
    /**
     * Retrieves the main data file path.
     *
     * @return the path to the data file
     */
    Path getDataFile();

    /**
     * Creates a backup of the current data file.
     */
    void createBackup();

    /**
     * Attempts to restore data from a backup file.
     *
     * @return true if restoration was successful, false otherwise
     */
    boolean restoreFromBackup();

    /**
     * Deletes all related files and directories.
     *
     * @return true if deletion was successful, false otherwise
     */
    boolean deleteAll();
}
