package it.unibo.coffebreak.impl.model.score;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import it.unibo.coffebreak.api.model.score.Score;
import it.unibo.coffebreak.api.model.score.ScoreManager;
import it.unibo.coffebreak.api.model.score.bonus.Bonus;
import it.unibo.coffebreak.api.model.score.entry.Entry;
import it.unibo.coffebreak.api.model.score.leaderboard.Leaderboard;
import it.unibo.coffebreak.api.repository.Repository;
import it.unibo.coffebreak.impl.model.score.bonus.GameBonus;
import it.unibo.coffebreak.impl.model.score.entry.ScoreEntry;
import it.unibo.coffebreak.impl.model.score.leaderboard.GameLeaderboard;
import it.unibo.coffebreak.impl.repository.ScoreRepository;

/**
 * Default implementation of {@link ScoreManager} using.
 * <ul>
 * <li>{@link GameScore} for score tracking</li>
 * <li>{@link GameBonus} for bonus management</li>
 * <li>{@link GameLeaderboard} for ranking</li>
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
 * 
 * @author Alessandro Rebosio
 */
public class GameScoreManager implements ScoreManager {

    private static final long BONUS_INTERVAL = 2000;

    /** Manages the player's core score value. */
    private final Score score;

    /** Handles bonus calculation and conversion. */
    private final Bonus bonus;

    /** Persists leaderboard data between sessions. */
    private final Repository<List<Entry>> repository;

    /** Maintains ranked player entries. */
    private final Leaderboard leaderBoard;

    private float bonusElapsed;

    /**
     * Creates a new manager with default component implementations.
     * Initializes leaderboard with persisted data if available.
     */
    public GameScoreManager() {
        this.score = new GameScore();
        this.bonus = new GameBonus();
        this.repository = new ScoreRepository();
        this.leaderBoard = new GameLeaderboard(this.repository.load());
        this.bonusElapsed = 0;
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
        return this.leaderBoard.getTopScores();
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
     */
    @Override
    public void calculateBonus(final float deltaTime) {
        this.bonusElapsed += deltaTime;
        if (this.bonusElapsed >= BONUS_INTERVAL) {
            this.bonus.calculate();
            this.bonusElapsed = 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void earnPoints(final int amount) {
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
     */
    @Override
    public void endMap() {
        this.earnPoints(this.getCurrentBonus());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEntryInLeaderBoard(final String name) {
        Objects.requireNonNull(name, "Name cannot be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Player name cannot be blank");
        }

        this.leaderBoard.addEntry(new ScoreEntry(name, this.getCurrentScore()));
        this.score.reset();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean saveScores() {
        return this.repository.save(this.getLeaderBoard());
    }
}
