package it.unibo.donkey.model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffeBreak.model.score.api.Score;
import it.unibo.coffeBreak.model.score.impl.GameScore;

public class TestScore {

    final static int AMOUNT = 1000;
    private Score score;

    @BeforeEach
    void init() {
        this.score = new GameScore();
    }

    @Test
    void testIncrease() {
        assertEquals(this.score.getScore(), 0);
        this.score.increase(AMOUNT);
        assertEquals(this.score.getScore(), AMOUNT);
        assertThrows(IllegalArgumentException.class, () -> this.score.increase(-AMOUNT));
    }

    @Test
    void testReset() {
        assertEquals(this.score.getScore(), 0);
        this.score.increase(AMOUNT);
        assertEquals(this.score.getScore(), AMOUNT);
        this.score.reset();
        assertEquals(this.score.getScore(), 0);
    }
}
