package it.unibo.donkey.model.score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.coffeBreak.model.score.api.Bonus;
import it.unibo.coffeBreak.model.score.impl.GameBonus;

public class TestBonus {

    final static int AMOUNT = 1000;

    private Bonus bonus;

    @BeforeEach
    void init() {
        this.bonus = new GameBonus();
    }

    @Test
    void testSetBonus() {
        assertEquals(this.bonus.getBonus(), 0);
        this.bonus.setBonus(AMOUNT);
        assertEquals(this.bonus.getBonus(), AMOUNT);
        assertThrows(IllegalArgumentException.class, () -> this.bonus.setBonus(-AMOUNT));
    }

    @Test
    void testCalculate() {
        this.bonus.setBonus(GameBonus.AMOUNT);
        assertEquals(this.bonus.getBonus(), GameBonus.AMOUNT);
        this.bonus.calculate();
        assertEquals(this.bonus.getBonus(), 0);
        this.bonus.calculate();
        assertEquals(this.bonus.getBonus(), 0);
    }
}
