package it.unibo.coffebreak.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.score.Entry;
import it.unibo.coffebreak.model.api.score.ScoreManager;
import it.unibo.coffebreak.model.impl.score.GameScoreManager;

public class GameModel implements Model {

    private final List<Entity> entities;
    private final Character player;
    private final ScoreManager<Entry> scoreManager;

    public GameModel() {
        this.entities = new ArrayList<>();
        this.scoreManager = new GameScoreManager();
        this.player = null;
    }

    @Override
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(this.entities);
    }

    @Override
    public Character getPlayer() {
        return this.player;
    }

    @Override
    public ScoreManager<Entry> getScoreManager() {
        return this.scoreManager;
    }

}
