package it.unibo.coffebreak.model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.score.api.Entry;
import it.unibo.coffebreak.model.score.api.Repository;
import it.unibo.coffebreak.model.score.impl.ScoreEntry;
import it.unibo.coffebreak.model.score.impl.ScoreRepository;

/**
 * Test class for {@link Repository} interface and {@link ScoreRepository}
 * implementation.
 */
class TestRepository {

    /** Path to the test data file in user's home directory. */
    public static final String FILE_PATH = System.getProperty("user.home") + File.separator + "leaderBoard.ser";

    /** Test score values used for testing. */
    private static final int TEST_SCORE_SMALL = 1000;
    private static final int TEST_SCORE_MEDIUM = 2000;
    private static final int TEST_SCORE_LARGE = 3000;

    /** The repository instance under test. */
    private Repository<Entry> repo;

    /**
     * Initializes the test environment before each test.
     * Cleans up any existing test data file.
     * 
     * @throws IOException
     */
    @BeforeEach
    void init() throws IOException {
        this.repo = new ScoreRepository();
        Files.deleteIfExists(Paths.get(FILE_PATH));
    }

    /**
     * Cleans up test resources after all tests have executed.
     * This method ensures the test environment is left in a clean state by:
     * - Deleting the test data file if it exists
     * 
     * @throws IOException if an I/O error occurs while deleting the test file
     * @see BeforeEach#init() for the corresponding setup method
     * @see ScoreRepository for the repository being tested
     */
    @AfterAll
    static void end() throws IOException {
        Files.deleteIfExists(Paths.get(FILE_PATH));
    }

    /**
     * Tests the save method functionality.
     * Verifies:
     * - Successful save operation returns true
     * - Data file is created after save
     */
    @Test
    void testSave() {
        final List<Entry> entries = List.of(
                new ScoreEntry("Player1", TEST_SCORE_SMALL),
                new ScoreEntry("Player2", TEST_SCORE_MEDIUM),
                new ScoreEntry("Player3", TEST_SCORE_LARGE));

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
        final List<Entry> entries = List.of(
                new ScoreEntry("Player1", TEST_SCORE_SMALL),
                new ScoreEntry("Player2", TEST_SCORE_MEDIUM),
                new ScoreEntry("Player3", TEST_SCORE_LARGE));
        repo.save(entries);

        final List<Entry> loadedEntries = repo.load();
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
        final List<Entry> loadedEntries = repo.load();
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
