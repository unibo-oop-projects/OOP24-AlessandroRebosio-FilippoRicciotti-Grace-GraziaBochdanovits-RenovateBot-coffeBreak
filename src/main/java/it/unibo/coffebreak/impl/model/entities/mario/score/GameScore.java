package it.unibo.coffebreak.impl.model.entities.mario.score;

import it.unibo.coffebreak.api.model.entities.character.score.Score;

/**
 * Implementation of {@link Score} interface that manages a game score system.
 * The score start at 0, each time {@link #increase(int)} is called, the value
 * of the score increases based on the value of the parameter passed in the
 * function.
 * 
 * @author Alessandro Rebosio
 */
public class GameScore implements Score {

    /** The current score value. */
    private int score;

    /** Creates a new GameScore initialized to zero. */
    public GameScore() {
        this.score = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScore() {
        return this.score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increase(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.score += amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        this.score = 0;
    }
}
