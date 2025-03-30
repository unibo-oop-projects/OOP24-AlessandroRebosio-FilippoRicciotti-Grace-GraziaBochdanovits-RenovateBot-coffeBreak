package it.unibo.coffeBreak.model.score.api;

public interface Entry extends Comparable<Entry> {

    String getName();

    int getScore();
}
