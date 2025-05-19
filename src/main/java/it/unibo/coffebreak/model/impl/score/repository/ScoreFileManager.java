package it.unibo.coffebreak.model.impl.score.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import it.unibo.coffebreak.model.api.score.repository.FileManager;

public class ScoreFileManager implements FileManager {

    private final Path dataFile;
    private final Path backupFile;
    private final Path dataDir;

    public ScoreFileManager(final String folderName, final String fileName) {
        this.dataDir = Path.of(System.getProperty("user.home"), folderName);
        this.dataFile = dataDir.resolve(fileName);
        this.backupFile = dataDir.resolve(fileName + ".bak");

        try {
            Files.createDirectories(dataDir);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create data directory", e);
        }
    }

    @Override
    public Path getDataFile() {
        return this.dataFile;
    }

    @Override
    public void createBackup() {
        try {
            if (Files.exists(dataFile)) {
                Files.copy(dataFile, backupFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Backup creation failed", e);
        }
    }

    @Override
    public boolean restoreFromBackup() {
        try {
            if (Files.exists(backupFile)) {
                Files.copy(backupFile, dataFile, StandardCopyOption.REPLACE_EXISTING);
                return true;
            }
        } catch (IOException e) {
            throw new IllegalStateException("Backup restore failed", e);
        }
        return false;
    }

    @Override
    public boolean deleteAll() {
        try {
            Files.deleteIfExists(dataFile);
            Files.deleteIfExists(backupFile);
            Files.deleteIfExists(dataDir);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
