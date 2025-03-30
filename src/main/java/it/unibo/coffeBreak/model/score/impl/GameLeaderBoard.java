package it.unibo.coffeBreak.model.score.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import it.unibo.coffeBreak.model.score.api.Entry;
import it.unibo.coffeBreak.model.score.api.LeaderBoard;

public class GameLeaderBoard implements LeaderBoard<Entry> {

    private static final int MAX_ENTRIES = 5;
    private final List<Entry> leaderBoard;

    public GameLeaderBoard() {
        this.leaderBoard = new ArrayList<>(MAX_ENTRIES);
    }
    
    public GameLeaderBoard(final List<Entry> leaderBoard) {
        this.leaderBoard = leaderBoard;
    }

    @Override
    public List<Entry> getLeaderBoard() {
        return Collections.unmodifiableList(leaderBoard);
    }

    @Override
    public void addEntry(Entry entry) {
        Objects.requireNonNull(entry, "Entry cannot be null");

        if (isEligible(entry)) {
            leaderBoard.add(entry);
            leaderBoard.sort(Entry::compareTo);
            
            if (leaderBoard.size() > MAX_ENTRIES) {
                leaderBoard.remove(MAX_ENTRIES);
            }
        }
    }

    private boolean isEligible(Entry entry) {
        return leaderBoard.size() < MAX_ENTRIES
                || leaderBoard.isEmpty()
                || entry.getScore() > leaderBoard.get(leaderBoard.size() - 1).getScore();
    }
}