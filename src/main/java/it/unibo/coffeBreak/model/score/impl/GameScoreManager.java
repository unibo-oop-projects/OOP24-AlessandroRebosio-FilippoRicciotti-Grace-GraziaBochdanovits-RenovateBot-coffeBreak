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
 * Concrete implementation of {@link ScoreManager} for game score management.
 * Manages score, bonus calculations, and leaderboard operations.
 */
public class GameScoreManager implements ScoreManager<Entry> {

    /** The current score of the player. */
    private final Score score;

    /** The current bonus that can be converted to points. */
    private final Bonus bonus;

    /** The repository for loading and saving score data. */
    private final Repository<Entry> repository;

    /** The leaderboard containing high scores and player entries. */
    private final LeaderBoard<Entry> leaderBoard;

    /**
     * Constructs a new GameScoreManager with default implementations.
     */
    public GameScoreManager() {
        this.score = new GameScore();
        this.bonus = new GameBonus();
        this.repository = new ScoreRepository();
        this.leaderBoard = new GameLeaderBoard(this.repository.load());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentScore() {
        return this.score.getScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentBonus() {
        return this.bonus.getBonus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entry> getLeaderBoard() {
        return this.leaderBoard.getLeaderBoard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHighestScore() {
        return this.getLeaderBoard().getFirst().getScore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void calculateBonus() {
        this.bonus.calculate();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalArgumentException if amount is negative
     */
    @Override
    public void earnPoints(final int amount) {
        this.score.increase(amount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startMap(final int value) {
        this.bonus.setBonus(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endMap() {
        this.earnPoints(this.getCurrentBonus());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endGame(final String name) {
        Objects.requireNonNull(name, "Name cannot be null");

        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }

        this.leaderBoard
                .addEntry(new ScoreEntry(name, this.score.getScore()));
        this.score.reset();
        if (this.leaderBoard.isWritten()) {
            this.repository.save(this.getLeaderBoard());
        }
    }
}
