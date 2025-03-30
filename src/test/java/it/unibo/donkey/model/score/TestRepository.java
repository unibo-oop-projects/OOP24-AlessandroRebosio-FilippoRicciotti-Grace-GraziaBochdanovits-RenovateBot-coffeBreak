package it.unibo.donkey.model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffeBreak.model.score.api.Entry;
import it.unibo.coffeBreak.model.score.api.Repository;
import it.unibo.coffeBreak.model.score.impl.ScoreEntry;
import it.unibo.coffeBreak.model.score.impl.ScoreRepository;

public class TestRepository {

    private static final String FILE_PATH = System.getProperty("user.home") + File.separator + "leaderBoard.ser";
    private Repository<Entry> repo;

    @BeforeEach
    void init() {
        this.repo = new ScoreRepository();
        System.out.println(FILE_PATH);
        new File(FILE_PATH).delete();
    }

    @Test
    void testSave() {
        List<Entry> entries = List.of(
                new ScoreEntry("Player1", 1000),
                new ScoreEntry("Player2", 2000),
                new ScoreEntry("Player3", 3000));

        assertTrue(repo.save(entries));
        assertTrue(new File(FILE_PATH).exists());
    }

    @Test
    void testLoad() {
        List<Entry> entries = List.of(
                new ScoreEntry("Player1", 1000),
                new ScoreEntry("Player2", 2000),
                new ScoreEntry("Player3", 3000));
        repo.save(entries);

        List<Entry> loadedEntries = repo.load();
        assertEquals(entries.size(), loadedEntries.size());
        assertIterableEquals(entries, loadedEntries);
    }

    @Test
    void testLoadEmpty() {
        List<Entry> loadedEntries = repo.load();
        assertTrue(loadedEntries.isEmpty());
    }

    @Test
    void testSaveEmptyList() {
        assertTrue(repo.save(Collections.emptyList()));
    }
}
