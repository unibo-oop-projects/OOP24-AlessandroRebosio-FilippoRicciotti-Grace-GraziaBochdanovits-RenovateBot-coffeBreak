package it.unibo.donkey.model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffeBreak.model.score.api.Entry;
import it.unibo.coffeBreak.model.score.api.LeaderBoard;
import it.unibo.coffeBreak.model.score.impl.GameLeaderBoard;
import it.unibo.coffeBreak.model.score.impl.ScoreEntry;

public class TestLeaderBoard {

    private LeaderBoard<Entry> leaderBoard;

    @BeforeEach
    void init() {
        this.leaderBoard = new GameLeaderBoard();
    }

    @Test
    void testAddEntry() {
        this.leaderBoard.addEntry(new ScoreEntry("REB", 1000));
        this.leaderBoard.addEntry(new ScoreEntry("RIC", 2000));
        this.leaderBoard.addEntry(new ScoreEntry("GRA", 3000));
        assertEquals(this.leaderBoard.getLeaderBoard(),
                List.of(new ScoreEntry("GRA", 3000),
                        new ScoreEntry("RIC", 2000),
                        new ScoreEntry("REB", 1000)));
    }

}
