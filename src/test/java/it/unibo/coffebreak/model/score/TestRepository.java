package it.unibo.coffebreak.model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.api.score.Entry;
import it.unibo.coffebreak.model.api.score.Repository;
import it.unibo.coffebreak.model.impl.score.ScoreEntry;
import it.unibo.coffebreak.model.impl.score.ScoreRepository;

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
 * 
 * @author Alessandro Rebosio
 */
class TestRepository {

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
     */
    @BeforeEach
    void setUp() {
        tearDown();
        this.repository = new ScoreRepository();
    }

    /**
     * Final cleanup after all tests complete.
     * 
     */
    @AfterAll
    static void tearDown() {
        Optional.of(ScoreRepository.deleteAllFiles());
    }

    /**
     * Tests successful save operation with valid entries.
     */
    @Test
    void shouldSaveEntriesAndCreateFile() {
        final List<Entry> entries = createTestEntries();

        assertTrue(repository.save(entries));
        assertTrue(ScoreRepository.DATA_FILE.exists());
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
        assertFalse(ScoreRepository.DATA_FILE.exists());
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
        Files.write(ScoreRepository.DATA_FILE.toPath(), "invalid data".getBytes(StandardCharsets.UTF_8));

        assertThrows(RuntimeException.class,
                repository::load);
    }

    /**
     * Creates standard test entries with varied scores.
     * 
     * @return a List containing three test entries with:
     *         - PLAYER_1 and SCORE_SMALL
     *         - PLAYER_2 and SCORE_MEDIUM
     *         - PLAYER_3 and SCORE_LARGE
     */
    private List<Entry> createTestEntries() {
        return List.of(
                new ScoreEntry(PLAYER_1, SCORE_SMALL),
                new ScoreEntry(PLAYER_2, SCORE_MEDIUM),
                new ScoreEntry(PLAYER_3, SCORE_LARGE));
    }
}
