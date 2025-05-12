package it.unibo.coffebreak.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;

/**
 * A concrete implementation of the {@link Model}.
 * This class maintains the game state including entities, player, and scores.
 * 
 * @author Alessandro Rebosio
 */
public class GameModel implements Model {

    private final List<Entity> entities;
    private final Character player;

    /**
     * Constructs a new GameModel with empty entities list,
     * no player character, and a new GameScoreManager instance.
     */
    public GameModel() {
        this.entities = new ArrayList<>();
        this.player = null;
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

}
