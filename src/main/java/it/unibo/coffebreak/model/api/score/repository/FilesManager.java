package it.unibo.coffebreak.model.api.score.repository;

import java.nio.file.Path;

public interface FilesManager {
    Path getDataFile();

    void createBackup();

    boolean restoreFromBackup();

    boolean deleteAll();
}
