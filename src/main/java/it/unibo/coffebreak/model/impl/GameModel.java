package it.unibo.coffebreak.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.phases.Phases;
import it.unibo.coffebreak.model.impl.phases.menu.MenuPhase;

/**
 * A concrete implementation of the {@link Model}.
 * This class maintains the game state including entities, player, and scores.
 * 
 * @author Alessandro Rebosio
 */
public class GameModel implements Model {

    private final List<Entity> entities;
    private final Character player;
    private Phases currentPhase;

    /**
     * Constructs a new GameModel with empty entities list,
     * no player character, and a new GameScoreManager instance.
     */
    public GameModel() {
        this.entities = new ArrayList<>();
        this.player = null;

        currentPhase = new MenuPhase();
        currentPhase.enterPhase();
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
     * 
     * @throws NullPointerException if newPhase is null
     */
    @Override
    public void setState(final Phases newPhase) {
        currentPhase.exitPhase();
        currentPhase = Objects.requireNonNull(newPhase, "The newPhase can not be null");
        currentPhase.enterPhase();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(final Command command) { // TODO: fix and add another method specified in Model.java
        this.currentPhase.handleAction(command, this);
    }
}
