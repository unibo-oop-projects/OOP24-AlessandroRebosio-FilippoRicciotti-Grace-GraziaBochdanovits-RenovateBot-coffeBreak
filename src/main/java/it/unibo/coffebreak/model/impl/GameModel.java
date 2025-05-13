package it.unibo.coffebreak.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.phases.Phases;
import it.unibo.coffebreak.model.api.score.Entry;
import it.unibo.coffebreak.model.api.score.ScoreManager;
import it.unibo.coffebreak.model.impl.phases.MenuPhase;
import it.unibo.coffebreak.model.impl.score.GameScoreManager;

/**
 * A concrete implementation of the {@link Model}.
 * This class maintains the game state including entities, player, and scores.
 * 
 * @author Alessandro Rebosio
 */
public class GameModel implements Model {

    private final List<Entity> entities;
    private final Character player;
    private final ScoreManager<Entry> scoreManager;
    private Phases currentPhase;

    /**
     * Constructs a new GameModel with empty entities list,
     * no player character, and a new GameScoreManager instance.
     */
    public GameModel() {
        this.entities = new ArrayList<>();
        this.scoreManager = new GameScoreManager();
        this.player = null;

        currentPhase = new MenuPhase();
        currentPhase.enterState();
    }

    /**
     * {@inheritDoc}
     * The returned list is unmodifiable to maintain encapsulation.
     */
    @Override
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(this.entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Character getPlayer() {
        return this.player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScoreManager<Entry> getScoreManager() {
        return this.scoreManager;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws NullPointerException if newPhase is null
     */
    @Override
    public void setState(final Phases newPhase) {
        currentPhase.exitState();
        currentPhase = Objects.requireNonNull(newPhase, "The newPhase can not be null");
        currentPhase.enterState();

    }

}
