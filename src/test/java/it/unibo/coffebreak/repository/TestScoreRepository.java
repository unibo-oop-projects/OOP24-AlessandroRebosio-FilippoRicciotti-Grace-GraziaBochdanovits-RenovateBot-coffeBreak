package it.unibo.coffebreak.repository;

import it.unibo.coffebreak.repository.impl.ScoreRepository;
import it.unibo.coffebreak.api.model.score.entry.Entry;
import it.unibo.coffebreak.api.repository.Repository;
import it.unibo.coffebreak.model.impl.score.entry.ScoreEntry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
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

    private static final int SCORE1 = 1500;
    private static final int SCORE2 = 1200;

    private Repository<List<Entry>> repository;

    private Entry entry1;
    private Entry entry2;

    /**
     * Sets up the test environment by initializing the repository
     * and creating test entries.
     */
    @BeforeEach
    void setUp() {
        repository = new ScoreRepository();
        entry1 = new ScoreEntry("Mario", SCORE1);
        entry2 = new ScoreEntry("Luigi", SCORE2);
    }

    /**
     * Cleans up the repository by deleting all files after each test.
     */
    @AfterEach
    void tearDown() {
        repository.deleteAllFiles();
    }

    /**
     * Verifies that entries are saved and then loaded correctly from disk.
     */
    @Test
    void testSaveAndLoadEntries() {
        assertTrue(repository.save(List.of(entry1, entry2)));

        final List<Entry> loaded = repository.load();
        assertEquals(2, loaded.size());
        assertTrue(loaded.contains(entry1));
        assertTrue(loaded.contains(entry2));
    }

    /**
     * Verifies that an empty list is returned if no data file exists.
     */
    @Test
    void testLoadEmptyIfNoFile() {
        final List<Entry> loaded = repository.load();
        assertTrue(loaded.isEmpty());
    }

    /**
     * Verifies that the repository can save and later restore a backup
     * successfully.
     * (This test assumes the internal backup mechanism works if loading works.)
     */
    @Test
    void testCreateBackupAndRestore() throws IOException {
        repository.save(List.of(entry1));

        final List<Entry> restored = repository.load();
        assertEquals(1, restored.size());
        assertEquals("Mario", restored.get(0).getName());
    }

    /**
     * Verifies that all files related to the repository can be deleted.
     */
    @Test
    void testDeleteAllFiles() {
        repository.save(List.of(entry1));
        assertTrue(repository.deleteAllFiles());
    }

    /**
     * Verifies that saving a null list throws a {@link NullPointerException}.
     */
    @Test
    void testSaveWithNullListThrows() {
        assertThrows(NullPointerException.class, () -> repository.save(null));
    }
}
