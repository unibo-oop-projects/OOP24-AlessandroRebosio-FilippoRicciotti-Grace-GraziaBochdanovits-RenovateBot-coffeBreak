package it.unibo.coffebreak.model.api;

import java.util.List;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.score.Entry;
import it.unibo.coffebreak.model.api.score.ScoreManager;

public interface Model {

    List<Entity> getEntities();

    Character getPlayer();

    ScoreManager<Entry> getScoreManager();
}
