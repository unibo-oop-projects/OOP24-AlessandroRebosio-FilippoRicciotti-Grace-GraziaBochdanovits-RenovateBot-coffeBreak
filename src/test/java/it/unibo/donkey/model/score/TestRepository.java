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

/**
 * Test class for {@link Repository} interface and {@link ScoreRepository}
 * implementation.
 */
public class TestRepository {

    /** Path to the test data file in user's home directory. */
    private static final String FILE_PATH = System.getProperty("user.home") + File.separator + "leaderBoard.ser";

    /** The repository instance under test. */
    private Repository<Entry> repo;

    /**
     * Initializes the test environment before each test.
     * Cleans up any existing test data file.
     */
    @BeforeEach
    void init() {
        this.repo = new ScoreRepository();
        new File(FILE_PATH).delete();
    }

    /**
     * Tests the save method functionality.
     * Verifies:
     * - Successful save operation returns true
     * - Data file is created after save
     */
    @Test
    void testSave() {
        List<Entry> entries = List.of(
                new ScoreEntry("Player1", 1000),
                new ScoreEntry("Player2", 2000),
                new ScoreEntry("Player3", 3000));

        assertTrue(repo.save(entries), "Save operation should return true");
        assertTrue(new File(FILE_PATH).exists(), "Data file should be created after save");
    }

    /**
     * Tests the load method functionality.
     * Verifies:
     * - Saved entries are correctly loaded
     * - Loaded entries match the saved ones
     */
    @Test
    void testLoad() {
        List<Entry> entries = List.of(
                new ScoreEntry("Player1", 1000),
                new ScoreEntry("Player2", 2000),
                new ScoreEntry("Player3", 3000));
        repo.save(entries);

        List<Entry> loadedEntries = repo.load();
        assertEquals(entries.size(), loadedEntries.size(),
                "Loaded entries count should match saved entries");
        assertIterableEquals(entries, loadedEntries,
                "Loaded entries should match saved entries");
    }

    /**
     * Tests loading from empty repository.
     * Verifies:
     * - Loading from non-existent file returns empty list
     */
    @Test
    void testLoadEmpty() {
        List<Entry> loadedEntries = repo.load();
        assertTrue(loadedEntries.isEmpty(),
                "Loading from empty repository should return empty list");
    }

    /**
     * Tests saving empty list.
     * Verifies:
     * - Saving empty list is allowed
     * - Operation returns true
     */
    @Test
    void testSaveEmptyList() {
        assertTrue(repo.save(Collections.emptyList()),
                "Saving empty list should return true");
    }

}
