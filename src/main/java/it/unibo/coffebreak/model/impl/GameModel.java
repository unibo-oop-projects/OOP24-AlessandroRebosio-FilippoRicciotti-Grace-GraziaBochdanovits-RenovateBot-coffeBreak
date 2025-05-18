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
 * Concrete implementation of the game model.
 * <p>
 * Maintains the game state including entities, player, and phase management.
 * Provides thread-safe access to model state.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public class GameModel implements Model {

    private final List<Entity> entities;
    private final Character player;
    private Phases currentPhase;

    private boolean running;

    /**
     * Constructs a new GameModel with empty entities list,
     * no player character, and a new GameScoreManager instance.
     */
    public GameModel() {
        this.entities = new ArrayList<>();
        this.player = null;

        currentPhase = new MenuPhase();
        currentPhase.enterPhase();

        this.running = true;
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
    public void executeCommand(final Command command) {
        this.currentPhase.handleCommand(this, command);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final float deltaTime) {
        this.currentPhase.update(this, deltaTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        return this.running;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        this.running = false;
    }
}
