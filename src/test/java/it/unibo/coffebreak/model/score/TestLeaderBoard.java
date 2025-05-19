package it.unibo.coffebreak.model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffebreak.model.api.score.entry.Entry;
import it.unibo.coffebreak.model.api.score.leaderboard.Leaderboard;
import it.unibo.coffebreak.model.impl.score.entry.ScoreEntry;
import it.unibo.coffebreak.model.impl.score.leaderboard.GameLeaderboard;

/**
 * Comprehensive test suite for {@link Leaderboard} interface and
 * {@link GameLeaderboard} implementation.
 * 
 * <p>
 * Tests verify:
 * <ul>
 * <li>Entry addition and ordering</li>
 * <li>Capacity management</li>
 * <li>Modification tracking</li>
 * <li>Error handling</li>
 * <li>Edge cases</li>
 * </ul>
 * 
 * @author Alessandro Rebosio
 */
class TestLeaderBoard {

    private static final String PLAYER_1 = "REB";
    private static final String PLAYER_2 = "RIC";
    private static final String PLAYER_3 = "GRA";
    private static final int SCORE_1 = 1000;
    private static final int SCORE_2 = 2000;
    private static final int SCORE_3 = 3000;
    private static final String EMPTY_NAME = "";

    private Leaderboard leaderBoard;

    /**
     * Initializes a fresh GameLeaderBoard instance before each test.
     */
    @BeforeEach
    void init() {
        this.leaderBoard = new GameLeaderboard(new ArrayList<>());
    }

    /**
     * Tests that constructor throws NullPointerException with null input.
     */
    @Test
    void testConstructorWithNullListThrowsException() {
        assertThrows(NullPointerException.class, () -> new GameLeaderboard(null));
    }

    /**
     * Tests that entries are added in correct descending score order.
     */
    @Test
    void shouldMaintainDescendingOrder() {
        leaderBoard.addEntry(new ScoreEntry(PLAYER_1, SCORE_1));
        leaderBoard.addEntry(new ScoreEntry(PLAYER_3, SCORE_3));
        leaderBoard.addEntry(new ScoreEntry(PLAYER_2, SCORE_2));

        final List<Entry> expected = List.of(
                new ScoreEntry(PLAYER_3, SCORE_3),
                new ScoreEntry(PLAYER_2, SCORE_2),
                new ScoreEntry(PLAYER_1, SCORE_1));

        assertEquals(expected, leaderBoard.getTopScores());
    }

    /**
     * Tests that adding null entries throws NullPointerException.
     */
    @Test
    void shouldRejectNullEntries() {
        assertThrows(NullPointerException.class,
                () -> leaderBoard.addEntry(null));
    }

    /**
     * Tests that leaderboard respects maximum capacity.
     */
    @Test
    void shouldEnforceMaximumCapacity() {
        fillLeaderBoardToCapacity();

        final Entry lowScoreEntry = new ScoreEntry("NewPlayer", 50);
        leaderBoard.addEntry(lowScoreEntry);

        assertEquals(GameLeaderboard.MAX_ENTRIES, leaderBoard.getTopScores().size());
        assertFalse(leaderBoard.getTopScores().contains(lowScoreEntry));
    }

    /**
     * Tests that higher scores replace lower scores when at capacity.
     */
    @Test
    void shouldReplaceLowestScoreWhenFull() {
        fillLeaderBoardToCapacity();

        final Entry highScoreEntry = new ScoreEntry("Champion", Integer.MAX_VALUE);
        leaderBoard.addEntry(highScoreEntry);

        assertTrue(leaderBoard.getTopScores().contains(highScoreEntry));
        assertEquals(highScoreEntry, leaderBoard.getTopScores().get(0));
    }

    /**
     * Tests handling of entries with empty names.
     */
    @Test
    void shouldAcceptEmptyPlayerNames() {
        final Entry entry = new ScoreEntry(EMPTY_NAME, SCORE_1);
        leaderBoard.addEntry(entry);
        assertEquals(EMPTY_NAME, leaderBoard.getTopScores().getFirst().getName());
    }

    /**
     * Helper method to fill leaderboard to maximum capacity.
     */
    private void fillLeaderBoardToCapacity() {
        for (int i = 1; i <= GameLeaderboard.MAX_ENTRIES; i++) {
            leaderBoard.addEntry(new ScoreEntry("Player" + i, i * 100));
        }
    }
}
