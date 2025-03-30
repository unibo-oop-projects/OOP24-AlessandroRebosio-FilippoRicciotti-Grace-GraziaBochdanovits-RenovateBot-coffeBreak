package it.unibo.coffeBreak.model.score.api;

import java.util.List;

public interface LeaderBoard<X> {
    List<X> getLeaderBoard();

    void addEntry(X entry);
}
