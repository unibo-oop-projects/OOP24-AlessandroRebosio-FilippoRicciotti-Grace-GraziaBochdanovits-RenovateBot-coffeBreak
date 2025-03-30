package it.unibo.coffeBreak.model.score.impl;

import it.unibo.coffeBreak.model.score.api.Score;

public class GameScore implements Score {

    private int score;

    public GameScore() {
        this.score = 0;
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public void increase(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("The amount must be positive");
        }
        this.score = this.score + amount;
    }

    @Override
    public void reset() {
        this.score = 0;
    }

}
