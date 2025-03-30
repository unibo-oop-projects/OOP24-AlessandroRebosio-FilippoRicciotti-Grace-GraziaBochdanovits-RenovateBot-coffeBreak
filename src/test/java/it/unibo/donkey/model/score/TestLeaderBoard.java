package it.unibo.donkey.model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffeBreak.model.score.api.Entry;
import it.unibo.coffeBreak.model.score.api.LeaderBoard;
import it.unibo.coffeBreak.model.score.impl.GameLeaderBoard;
import it.unibo.coffeBreak.model.score.impl.ScoreEntry;

/**
 * Test class for {@link LeaderBoard} interface and {@link GameLeaderBoard}
 * implementation.
 */
public class TestLeaderBoard {

    /** The leaderboard instance under test. */
    private LeaderBoard<Entry> leaderBoard;

    /** Test player name 1. */
    private static final String PLAYER_1 = "REB";

    /** Test player name 2. */
    private static final String PLAYER_2 = "RIC";

    /** Test player name 3. */
    private static final String PLAYER_3 = "GRA";

    /** Test score value 1. */
    private static final int SCORE_1 = 1000;

    /** Test score value 2. */
    private static final int SCORE_2 = 2000;

    /** Test score value 3. */
    private static final int SCORE_3 = 3000;

    /**
     * Initializes the test environment before each test.
     */
    @BeforeEach
    void init() {
        this.leaderBoard = new GameLeaderBoard();
    }

    /**
     * Tests the addEntry method functionality.
     * Verifies:
     * - Entries are correctly added to the leaderboard
     * - Leaderboard maintains entries in descending score order
     */
    @Test
    void testAddEntry() {
        this.leaderBoard.addEntry(new ScoreEntry(PLAYER_1, SCORE_1));
        this.leaderBoard.addEntry(new ScoreEntry(PLAYER_2, SCORE_2));
        this.leaderBoard.addEntry(new ScoreEntry(PLAYER_3, SCORE_3));

        List<Entry> expected = List.of(
                new ScoreEntry(PLAYER_3, SCORE_3),
                new ScoreEntry(PLAYER_2, SCORE_2),
                new ScoreEntry(PLAYER_1, SCORE_1));

        assertEquals(expected, this.leaderBoard.getLeaderBoard(),
                "Leaderboard should maintain entries in descending score order");
    }

    /**
     * Tests the behavior when adding null entries.
     * Verifies:
     * - NullPointerException is thrown when adding null entry
     */
    @Test
    void testAddNullEntry() {
        assertThrows(NullPointerException.class,
                () -> this.leaderBoard.addEntry(null),
                "Adding null entry should throw NullPointerException");
    }

    /**
     * Tests the leaderboard capacity management.
     * Verifies:
     * - Leaderboard doesn't exceed maximum capacity
     * - Lower scores don't make it into full leaderboard
     */
    @Test
    void testLeaderBoardCapacity() {
        // Fill the leaderboard
        for (int i = 1; i <= 5; i++) {
            this.leaderBoard.addEntry(new ScoreEntry("Player" + i, i * 100));
        }

        // Try to add a score that's too low
        this.leaderBoard.addEntry(new ScoreEntry("NewPlayer", 50));

        assertEquals(5, this.leaderBoard.getLeaderBoard().size(),
                "Leaderboard should not exceed maximum capacity");
    }

}
