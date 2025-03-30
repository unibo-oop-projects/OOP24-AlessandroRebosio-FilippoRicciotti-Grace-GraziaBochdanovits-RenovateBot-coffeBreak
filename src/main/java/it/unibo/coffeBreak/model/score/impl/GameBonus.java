package it.unibo.coffeBreak.model.score.impl;

import it.unibo.coffeBreak.model.score.api.Bonus;

public class GameBonus implements Bonus {

    public static final int AMOUNT = 100;
    private int bonus;

    public GameBonus() {
        this.bonus = 0;
    }

    @Override
    public int getBonus() {
        return this.bonus;
    }

    @Override
    public void setBonus(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("The value must be positive.");
        }
        this.bonus = value;
    }

    @Override
    public void calculate() {
        if (this.bonus > 0) {
            this.bonus = this.bonus - AMOUNT;
        }
    }

}
