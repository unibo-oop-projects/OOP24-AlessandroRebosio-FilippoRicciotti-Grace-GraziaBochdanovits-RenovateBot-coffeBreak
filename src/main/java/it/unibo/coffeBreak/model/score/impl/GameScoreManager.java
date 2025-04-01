package it.unibo.coffebreak.model.score.impl;

import java.util.List;
import java.util.Objects;

import it.unibo.coffebreak.model.score.api.Bonus;
import it.unibo.coffebreak.model.score.api.Entry;
import it.unibo.coffebreak.model.score.api.LeaderBoard;
import it.unibo.coffebreak.model.score.api.Repository;
import it.unibo.coffebreak.model.score.api.Score;
import it.unibo.coffebreak.model.score.api.ScoreManager;

/**
 * Default implementation of {@link ScoreManager} using:
 * <ul>
 * <li>{@link GameScore} for score tracking</li>
 * <li>{@link GameBonus} for bonus management</li>
 * <li>{@link GameLeaderBoard} for ranking</li>
 * <li>{@link ScoreRepository} for persistence</li>
 * </ul>
 * 
 * <p>
 * Lifecycle operations:
 * <ol>
 * <li>{@code startMap()} initializes bonus</li>
 * <li>{@code earnPoints()} accumulates score</li>
 * <li>{@code endMap()} converts bonus</li>
 * <li>{@code endGame()} saves results</li>
 * </ol>
 */
public class GameScoreManager implements ScoreManager<Entry> {

    /** Manages the player's core score value */
    private final Score score;

    /** Handles bonus calculation and conversion */
    private final Bonus bonus;

    /** Persists leaderboard data between sessions */
    private final Repository<Entry> repository;

    /** Maintains ranked player entries */
    private final LeaderBoard<Entry> leaderBoard;

    /**
     * Creates a new manager with default component implementations.
     * Initializes leaderboard with persisted data if available.
     */
    public GameScoreManager() {
        this.score = new GameScore();
        this.bonus = new GameBonus();
        this.repository = new ScoreRepository();
        this.leaderBoard = new GameLeaderBoard(this.repository.load());
    }

    /**
     * {@inheritDoc}
     * 
     * @implNote Delegates to {@link Score#getScore()}
     */
    @Override
    public int getCurrentScore() {
        return this.score.getScore();
    }

    /**
     * {@inheritDoc}
     * 
     * @implNote Delegates to {@link Bonus#getBonus()}
     */
    @Override
    public int getCurrentBonus() {
        return this.bonus.getBonus();
    }

    /**
     * {@inheritDoc}
     * 
     * @implNote Returns leaderboard snapshot
     */
    @Override
    public List<Entry> getLeaderBoard() {
        return this.leaderBoard.getLeaderBoard();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NoSuchElementException if leaderboard is empty
     */
    @Override
    public int getHighestScore() {
        return this.getLeaderBoard().getFirst().getScore();
    }

    /**
     * {@inheritDoc}
     * 
     * @implNote Delegates to {@link Bonus#calculate()}
     */
    @Override
    public void calculateBonus() {
        this.bonus.calculate();
    }

    /**
     * {@inheritDoc}
     * 
     * @implSpec Validates input before delegation
     */
    @Override
    public void earnPoints(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Points amount cannot be negative");
        }
        this.score.increase(amount);
    }

    /**
     * {@inheritDoc}
     * 
     * @implNote Delegates to {@link Bonus#setBonus(int)}
     */
    @Override
    public void startMap(final int value) {
        this.bonus.setBonus(value);
    }

    /**
     * {@inheritDoc}
     * 
     * @implNote Converts entire bonus to points
     */
    @Override
    public void endMap() {
        this.earnPoints(this.getCurrentBonus());
    }

    /**
     * {@inheritDoc}
     * 
     * @implSpec Performs:
     *           <ol>
     *           <li>Name validation</li>
     *           <li>Leaderboard update</li>
     *           <li>Score reset</li>
     *           <li>Conditional persistence</li>
     *           </ol>
     */
    @Override
    public void endGame(final String name) {
        Objects.requireNonNull(name, "Name cannot be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Player name cannot be blank");
        }

        this.leaderBoard.addEntry(new ScoreEntry(name, this.score.getScore()));
        this.score.reset();

        if (this.leaderBoard.isWritten()) {
            this.repository.save(this.getLeaderBoard());
        }
    }
}
