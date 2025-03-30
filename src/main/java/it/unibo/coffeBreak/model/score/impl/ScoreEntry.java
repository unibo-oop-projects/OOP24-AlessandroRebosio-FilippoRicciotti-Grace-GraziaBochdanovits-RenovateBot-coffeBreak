package it.unibo.coffeBreak.model.score.impl;

import java.util.Objects;

import it.unibo.coffeBreak.model.score.api.Entry;

public class ScoreEntry implements Entry {

    private final String name;
    private final int score;

    public ScoreEntry(final String name, final int score) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.score = score;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public int compareTo(Entry o) {
        return Integer.compare(Objects.requireNonNull(o).getScore(), this.score);
    }

    @Override
    public String toString() {
        return "ScoreEntry[name= " + this.name + ", " + "score= " + this.score + "]";
    }
}
