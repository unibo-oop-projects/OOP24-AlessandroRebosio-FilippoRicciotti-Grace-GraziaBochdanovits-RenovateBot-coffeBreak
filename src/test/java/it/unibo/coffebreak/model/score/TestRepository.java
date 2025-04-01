package it.unibo.coffebreak.model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
 * Comprehensive test suite for {@link Repository} interface and
 * {@link ScoreRepository} implementation.
 * 
 * <p>
 * Tests verify:
 * <ul>
 * <li>Data persistence operations</li>
 * <li>File handling behavior</li>
 * <li>Edge cases and error conditions</li>
 * <li>Data integrity during save/load cycles</li>
 * </ul>
 * 
 * <p>
 * Test files are created in the user's home directory and automatically cleaned
 * up.
 */
class TestRepository {

    public static final Path FILE_PATH = Path.of(System.getProperty("user.home"), "leaderBoard.ser");
    private static final String PLAYER_1 = "Player1";
    private static final String PLAYER_2 = "Player2";
    private static final String PLAYER_3 = "Player3";
    private static final int SCORE_SMALL = 1000;
    private static final int SCORE_MEDIUM = 2000;
    private static final int SCORE_LARGE = 3000;
    private static final String EMPTY_NAME = "";
    private static final int ZERO_SCORE = 0;

    private Repository<Entry> repository;

    /**
     * Initializes a fresh repository instance and cleans up any existing test file.
     * 
     * @throws IOException if cleanup fails
     */
    @BeforeEach
    void setUp() throws IOException {
        this.repository = new ScoreRepository();
        Files.deleteIfExists(FILE_PATH);
    }

    /**
     * Final cleanup after all tests complete.
     * 
     * @throws IOException if cleanup fails
     */
    @AfterAll
    static void tearDown() throws IOException {
        Files.deleteIfExists(FILE_PATH);
    }

    /**
     * Tests successful save operation with valid entries.
     */
    @Test
    void shouldSaveEntriesAndCreateFile() {
        final List<Entry> entries = createTestEntries();

        assertTrue(repository.save(entries));
        assertTrue(Files.exists(FILE_PATH));
        assertEquals(entries.size(), repository.load().size());
    }

    /**
     * Tests that saved entries can be correctly reloaded.
     */
    @Test
    void shouldReloadSavedEntriesIdentically() {
        final List<Entry> originalEntries = createTestEntries();
        repository.save(originalEntries);

        final List<Entry> loadedEntries = repository.load();

        assertIterableEquals(originalEntries, loadedEntries);
    }

    /**
     * Tests loading when no data file exists.
     */
    @Test
    void shouldReturnEmptyListWhenFileNotExists() {
        assertFalse(Files.exists(FILE_PATH));
        assertTrue(repository.load().isEmpty());
    }

    /**
     * Tests handling of empty entry lists.
     */
    @Test
    void shouldHandleEmptyEntryList() {
        assertTrue(repository.save(Collections.emptyList()));
        assertTrue(repository.load().isEmpty());
    }

    /**
     * Tests that null entries are rejected.
     */
    @Test
    void shouldRejectNullEntriesList() {
        assertThrows(NullPointerException.class,
                () -> repository.save(null));
    }

    /**
     * Tests handling of entries with edge case values.
     */
    @Test
    void shouldHandleEdgeCaseEntries() {
        final List<Entry> edgeCases = List.of(
                new ScoreEntry(EMPTY_NAME, SCORE_LARGE),
                new ScoreEntry(PLAYER_1, ZERO_SCORE),
                new ScoreEntry(PLAYER_2, Integer.MAX_VALUE));

        assertTrue(repository.save(edgeCases));
        assertEquals(edgeCases.size(), repository.load().size());
    }

    /**
     * Tests file corruption handling (simulated by writing invalid data).
     */
    @Test
    void shouldHandleCorruptedDataFile() throws IOException {
        Files.write(FILE_PATH, "invalid data".getBytes(StandardCharsets.UTF_8));

        assertThrows(RuntimeException.class,
                repository::load);
    }

    /**
     * Creates standard test entries with varied scores.
     */
    private List<Entry> createTestEntries() {
        return List.of(
                new ScoreEntry(PLAYER_1, SCORE_SMALL),
                new ScoreEntry(PLAYER_2, SCORE_MEDIUM),
                new ScoreEntry(PLAYER_3, SCORE_LARGE));
    }
}
