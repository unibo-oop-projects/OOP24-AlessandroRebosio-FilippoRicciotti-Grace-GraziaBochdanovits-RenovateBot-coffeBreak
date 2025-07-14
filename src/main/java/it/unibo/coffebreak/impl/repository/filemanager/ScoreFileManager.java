package it.unibo.coffebreak.impl.repository.filemanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import it.unibo.coffebreak.api.repository.filemanager.FileManager;

/**
 * Basic file manager implementation handling data file and backup operations.
 * 
 * @author Alessandro Rebosio
 */
public class ScoreFileManager implements FileManager {

    private final Path dataFile;
    private final Path backupFile;
    private final Path dataDir;

    /**
     * Constructs a file manager using given folder and file name.
     *
     * @param folderName the folder to store files in
     * @param fileName   the name of the main data file
     */
    public ScoreFileManager(final String folderName, final String fileName) {
        this.dataDir = Path.of(System.getProperty("user.home"), folderName);
        this.dataFile = dataDir.resolve(fileName);
        this.backupFile = dataDir.resolve(fileName + ".bak");

        try {
            Files.createDirectories(dataDir);
        } catch (final IOException e) {
            throw new IllegalStateException("Unable to create data directory", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Path getDataFile() {
        return this.dataFile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createBackup() {
        try {
            if (Files.exists(dataFile)) {
                Files.copy(dataFile, backupFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (final IOException e) {
            throw new IllegalStateException("Backup creation failed", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean restoreFromBackup() {
        try {
            if (Files.exists(backupFile)) {
                Files.copy(backupFile, dataFile, StandardCopyOption.REPLACE_EXISTING);
                return true;
            }
        } catch (final IOException e) {
            throw new IllegalStateException("Backup restore failed", e);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteAll() {
        try {
            Files.deleteIfExists(dataFile);
            Files.deleteIfExists(backupFile);
            Files.deleteIfExists(dataDir);
            return true;
        } catch (final IOException e) {
            return false;
        }
    }
}
