package it.unibo.coffebreak.repository;

import it.unibo.coffebreak.api.model.leaderboard.entry.Entry;
import it.unibo.coffebreak.api.repository.Repository;
import it.unibo.coffebreak.impl.model.leaderboard.entry.ScoreEntry;
import it.unibo.coffebreak.impl.repository.ScoreRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ScoreRepository}, verifying its persistence and
 * recovery behaviors for Entry objects.
 * 
 * These tests check correct serialization, deserialization, backup creation,
 * restoration, and proper exception handling.
 * 
 * @author Alessandro Rebosio
 */
class TestScoreRepository {
    private Repository<List<Entry>> repository;

    @BeforeEach
    void setUp() {
        repository = new ScoreRepository();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAllFiles();
    }

    /**
     * Tests that saving and loading a list of entries works correctly.
     */
    @Test
    void testSaveAndLoad() {
        final Entry entry1 = new ScoreEntry("Alice", 100);
        final Entry entry2 = new ScoreEntry("Bob", 200);
        final List<Entry> entries = List.of(entry1, entry2);

        assertTrue(repository.save(entries));

        final List<Entry> loaded = repository.load();
        assertEquals(entries.size(), loaded.size());
        assertEquals(entries.get(0).name(), loaded.get(0).name());
        assertEquals(entries.get(1).score(), loaded.get(1).score());
    }

    /**
     * Tests that loading when no file exists returns an empty list.
     */
    @Test
    void testLoadNoFile() {
        repository.deleteAllFiles();
        final List<Entry> loaded = repository.load();
        assertTrue(loaded.isEmpty());
    }

    /**
     * Tests that saving a null list throws NullPointerException.
     */
    @Test
    void testSaveNullThrows() {
        assertThrows(NullPointerException.class, () -> repository.save(null));
    }

    /**
     * Tests that saving an empty list returns true and does not throw.
     */
    @Test
    void testSaveEmptyList() {
        assertTrue(repository.save(List.of()));
    }

    /**
     * Tests that deleteAllFiles returns true (even if files do not exist).
     */
    @Test
    void testDeleteAllFiles() {
        assertTrue(repository.deleteAllFiles());
    }
}
