package it.unibo.donkey.model.score;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffeBreak.model.score.api.Entry;
import it.unibo.coffeBreak.model.score.api.ScoreManager;
import it.unibo.coffeBreak.model.score.impl.GameScoreManager;
import it.unibo.coffeBreak.model.score.impl.ScoreEntry;

import java.util.List;

public class TestScoreManager {

    private ScoreManager<Entry> scoreManager;

    @BeforeEach
    void init() {
        this.scoreManager = new GameScoreManager();
    }

    @Test
    void testGetCurrentScore() {
        assertEquals(0, scoreManager.getCurrentScore());
    }

    @Test
    void testGetCurrentBonus() {
        assertEquals(0, scoreManager.getCurrentBonus());
    }

    @Test
    void testEarnPoints() {
        scoreManager.earnPoints(500);
        assertEquals(500, scoreManager.getCurrentScore());
    }

    @Test
    void testCalculateBonus() {
        scoreManager.calculateBonus();
        assertEquals(0, scoreManager.getCurrentBonus());
    }

    @Test
    void testEndMap() {
        scoreManager.earnPoints(100);
        scoreManager.calculateBonus();
        int scoreBeforeEnd = scoreManager.getCurrentScore();
        scoreManager.endMap();
        assertEquals(scoreBeforeEnd + scoreManager.getCurrentBonus(), scoreManager.getCurrentScore());
    }

    @Test
    void testEndGame() {
        Entry entry = new ScoreEntry("Player1", 1000);
        scoreManager.endGame(entry);

        List<Entry> leaderBoard = scoreManager.getLeaderBoard();
        assertTrue(leaderBoard.contains(entry));

        assertEquals(1000, entry.getScore());
    }

    @Test
    void testGetHighestScore() {
        Entry entry1 = new ScoreEntry("Player1", 1000);
        Entry entry2 = new ScoreEntry("Player2", 2000);
        scoreManager.endGame(entry1);
        scoreManager.endGame(entry2);

        assertEquals(2000, scoreManager.getHighestScore());
    }
}
