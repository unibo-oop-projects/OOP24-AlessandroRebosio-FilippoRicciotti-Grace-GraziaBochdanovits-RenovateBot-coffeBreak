package it.unibo.coffebreak.model.api.score.repository;

import java.nio.file.Path;

public interface FileManager {
    Path getDataFile();

    void createBackup();

    boolean restoreFromBackup();

    boolean deleteAll();
}
