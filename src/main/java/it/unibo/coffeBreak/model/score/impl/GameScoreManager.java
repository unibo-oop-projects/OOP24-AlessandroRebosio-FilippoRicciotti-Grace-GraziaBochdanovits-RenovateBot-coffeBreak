package it.unibo.coffeBreak.model.score.impl;

import java.util.List;

import it.unibo.coffeBreak.model.score.api.Bonus;
import it.unibo.coffeBreak.model.score.api.Entry;
import it.unibo.coffeBreak.model.score.api.LeaderBoard;
import it.unibo.coffeBreak.model.score.api.Repository;
import it.unibo.coffeBreak.model.score.api.Score;
import it.unibo.coffeBreak.model.score.api.ScoreManager;

public class GameScoreManager implements ScoreManager<Entry> {

    private final Score score;
    private final Bonus bonus;
    private final Repository<Entry> repository;
    private final LeaderBoard<Entry> leaderBoard;

    public GameScoreManager() {
        this.score = new GameScore();
        this.bonus = new GameBonus();
        this.repository = new ScoreRepository();
        this.leaderBoard = new GameLeaderBoard(this.repository.load());
    }

    @Override
    public int getCurrentScore() {
        return this.score.getScore();
    }

    @Override
    public int getCurrentBonus() {
        return this.bonus.getBonus();
    }

    @Override
    public List<Entry> getLeaderBoard() {
        return this.leaderBoard.getLeaderBoard();
    }

    @Override
    public int getHighestScore() {
        return this.getLeaderBoard().getFirst().getScore();
    }

    @Override
    public void calculateBonus() {
        this.bonus.calculate();
    }

    @Override
    public void earnPoints(int amount) {
        this.score.increase(amount);
    }

    @Override
    public void endMap() {
        this.earnPoints(this.getCurrentBonus());
    }

    @Override
    public void endGame(Entry entry) {
        this.leaderBoard.addEntry(entry);
        this.repository.save(this.getLeaderBoard());
    }

}
