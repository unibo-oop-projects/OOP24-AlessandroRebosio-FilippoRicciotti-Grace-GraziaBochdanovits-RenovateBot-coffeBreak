package it.unibo.coffeBreak.model.score.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

import it.unibo.coffeBreak.model.score.api.Entry;
import it.unibo.coffeBreak.model.score.api.Repository;

public class ScoreRepository implements Repository<Entry> {

    private static final String FILE_NAME = "leaderBoard.ser";
    private final File dataFile;

    public ScoreRepository() {
        this.dataFile = new File(System.getProperty("user.home"), FILE_NAME);
    }

    @Override
    public boolean save(List<Entry> list) {
        if (list.isEmpty()) {
            return true;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            oos.writeObject(list);
            return true;
        } catch (IOException e) {
            throw new RepositoryException("Errore durante il salvataggio dei dati", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Entry> load() {
        if (!dataFile.exists()) {
            return Collections.emptyList();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
            return (List<Entry>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RepositoryException("Errore durante il caricamento dei dati", e);
        }
    }

    public static class RepositoryException extends RuntimeException {
        public RepositoryException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
