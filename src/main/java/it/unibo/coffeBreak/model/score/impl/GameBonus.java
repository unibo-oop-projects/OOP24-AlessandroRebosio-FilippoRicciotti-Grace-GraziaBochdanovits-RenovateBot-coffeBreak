package it.unibo.coffebreak.model.score.impl;

import it.unibo.coffebreak.model.score.api.Bonus;

/**
 * Implementation of {@link Bonus} interface that manages a game bonus system.
 * The bonus is reduced by a fixed amount (AMOUNT) when calculated.
 */
public class GameBonus implements Bonus {

    /**
     * The fixed amount by which the bonus is reduced during calculation.
     */
    public static final int AMOUNT = 100;

    /**
     * The current bonus value.
     */
    private int bonus;

    /**
     * Constructs a new GameBonus with initial value set to 0.
     */
    public GameBonus() {
        this.bonus = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBonus() {
        return this.bonus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBonus(final int value) {
        if (value < 0) {
            throw new IllegalArgumentException("The value must be positive.");
        }
        this.bonus = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void calculate() {
        if (this.bonus > 0) {
            this.bonus -= AMOUNT;
        }
    }
}
