package it.unibo.coffebreak.model.score.impl;

import it.unibo.coffebreak.model.score.api.Score;

/**
 * Basic implementation of {@link Score} without thread safety guarantees.
 * This implementation is suitable for single-threaded environments or
 * when thread safety is managed at a higher level.
 * 
 * <p>
 * Example:
 * 
 * <pre>{@code
 * Score score = new GameScore();
 * score.increase(50);
 * score.reset();
 * }</pre>
 * 
 * <p>
 * <b>Implementation Notes:</b> This implementation is not thread-safe.
 * For concurrent usage, consider using thread-safe alternatives like:
 * <ul>
 * <li>{@link java.util.concurrent.atomic.AtomicInteger}</li>
 * <li>External synchronization</li>
 * </ul>
 */
public class GameScore implements Score {
    /**
     * The current score value.
     * 
     * @implNote Access to this field is not synchronized
     */
    private int score;

    /**
     * Creates a new GameScore initialized to zero.
     */
    public GameScore() {
        this.score = 0;
    }

    /**
     * {@inheritDoc}
     * 
     * @implNote Returns the current score without synchronization
     */
    @Override
    public int getScore() {
        return this.score;
    }

    /**
     * {@inheritDoc}
     * 
     * @implNote This implementation:
     *           <ul>
     *           <li>Performs basic parameter validation</li>
     *           <li>Modifies score without synchronization</li>
     *           </ul>
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
     * 
     * @implNote Resets score without synchronization
     */
    @Override
    public void reset() {
        this.score = 0;
    }
}
