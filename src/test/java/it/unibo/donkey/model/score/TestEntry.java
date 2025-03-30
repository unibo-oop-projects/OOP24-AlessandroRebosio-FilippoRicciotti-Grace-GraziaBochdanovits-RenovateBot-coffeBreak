package it.unibo.donkey.model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffeBreak.model.score.api.Entry;
import it.unibo.coffeBreak.model.score.impl.ScoreEntry;

public class TestEntry {

    final static String NAME = "REB";
    final static int SCORE = 1000;
    private Entry entry;

    @BeforeEach
    void init() {
        this.entry = new ScoreEntry(NAME, SCORE);
    }

    @Test
    void testNullName() {
        assertThrows(NullPointerException.class, () -> new ScoreEntry(null, SCORE));
    }

    @Test
    void testGetName() {
        assertEquals(this.entry.getName(), NAME);
    }

    @Test
    void testGetScore() {
        assertEquals(this.entry.getScore(), SCORE);
    }
}
