package it.unibo.coffebreak.model.impl.score.bonus;

import it.unibo.coffebreak.model.api.score.bonus.Bonus;

/**
 * Implementation of {@link Bonus} interface that manages a game bonus system.
 * The bonus starts at 0 and can be set to any positive value. Each time
 * {@link #calculate()} is called, the bonus is reduced by {@link #AMOUNT},
 * but never goes below 0.
 * 
 * @author Alessandro Rebosio
 */
public class GameBonus implements Bonus {

    /**
     * The fixed amount by which the bonus is reduced during calculation.
     * When {@link #calculate()} is called, the current bonus is decreased
     * by this value, but will not go below 0.
     */
    public static final int AMOUNT = 100;

    /** The current bonus value. */
    private int bonus;

    /**
     * Constructs a new GameBonus with an initial value of 0.
     * The bonus must be set explicitly using {@link #setBonus(int)}.
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
     * 
     */
    @Override
    public void setBonus(final int value) {
        if (value < 0) {
            throw new IllegalArgumentException("The value must be positive.");
        }
        this.bonus = value;
    }

    /**
     * Reduces the current bonus by {@link #AMOUNT}, ensuring it doesn't go below
     * zero.
     * Example: if current bonus is 150, after calling this method it will be 50.
     * If current bonus is less than AMOUNT, it will be set to 0.
     */
    @Override
    public void calculate() {
        this.bonus = Math.max(0, this.bonus - AMOUNT);
    }
}
