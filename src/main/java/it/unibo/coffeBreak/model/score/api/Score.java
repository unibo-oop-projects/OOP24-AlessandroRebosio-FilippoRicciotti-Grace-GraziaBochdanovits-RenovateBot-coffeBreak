package it.unibo.coffeBreak.model.score.api;

public interface Score {

    int getScore();

    void increase(int amount);

    void reset();
}
