package it.unibo.coffeBreak.model.score.api;

import java.util.List;

public interface ScoreManager<X> {
    int getCurrentScore();

    int getCurrentBonus();

    List<X> getLeaderBoard();

    int getHighestScore();

    void calculateBonus();

    void earnPoints(int amount);

    void endMap();

    void endGame(Entry entry);
}
