package it.unibo.coffebreak.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.donkeykong.DonkeyKong;
import it.unibo.coffebreak.model.api.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.model.api.entities.princess.Princess;
import it.unibo.coffebreak.model.api.phases.Phases;
import it.unibo.coffebreak.model.api.physics.Collision;
import it.unibo.coffebreak.model.impl.phases.menu.MenuPhase;
import it.unibo.coffebreak.model.impl.physics.GameCollision;

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
    private final Collision gameCollision;

    private Phases currentPhase;

    private boolean running;

    /**
     * Constructs a new GameModel with empty entities list,
     * no player character, and a new GameScoreManager instance.
     */
    public GameModel() {
        this.entities = new ArrayList<>();
        this.gameCollision = new GameCollision();

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
    public Optional<Character> getPlayer() {
        return this.findEntityOfType(Character.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DonkeyKong> getDonkeyKong() {
        return this.findEntityOfType(DonkeyKong.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Princess> getPrincess() {
        return this.findEntityOfType(Princess.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addBarrel(final Barrel barrel) {
        return this.entities.add(Objects.requireNonNull(barrel, "Barrel cannot be null"));
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
    public void checkCollision() {
        this.gameCollision.checkCollision(this);
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

    private <T> Optional<T> findEntityOfType(final Class<T> type) {
        return this.entities.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .findFirst();
    }
}
