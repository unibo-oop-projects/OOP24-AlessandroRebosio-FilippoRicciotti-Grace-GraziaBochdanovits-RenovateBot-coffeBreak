package it.unibo.coffeBreak.model.score.impl;

import it.unibo.coffeBreak.model.score.api.Score;

/**
 * Implementation of the {@link Score} interface that manages a game score.
 * This class provides thread-safe operations for score management.
 */
public class GameScore implements Score {

    /**
     * The current score value.
     */
    private int score;

    /**
     * Constructs a new GameScore with initial value set to zero.
     */
    public GameScore() {
        this.score = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized int getScore() {
        return this.score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void increase(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("The amount must be positive");
        }
        this.score += amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void reset() {
        this.score = 0;
    }

}
