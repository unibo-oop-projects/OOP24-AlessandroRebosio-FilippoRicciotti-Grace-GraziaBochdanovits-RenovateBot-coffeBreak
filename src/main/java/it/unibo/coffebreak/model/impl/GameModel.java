package it.unibo.coffebreak.model.impl;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.Model;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.npc.Antagonist;
import it.unibo.coffebreak.model.api.level.LevelManager;
import it.unibo.coffebreak.model.api.score.ScoreManager;
import it.unibo.coffebreak.model.api.states.GameState;
import it.unibo.coffebreak.model.impl.level.GameLevelManager;
import it.unibo.coffebreak.model.impl.score.GameScoreManager;
import it.unibo.coffebreak.model.impl.states.menu.MenuState;

/**
 * Concrete implementation of the game model.
 * <p>
 * Maintains the game state including entities, player, and state management.
 * Provides thread-safe access to model state.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public class GameModel implements Model {

    private final LevelManager levelManager;
    private final ScoreManager scoreManager;

    private final List<Entity> entities;
    private GameState currentState;

    private String playerName;

    private volatile boolean running;

    /**
     * Constructs a new GameModel with empty entities list,
     * no player character, and a new GameScoreManager instance.
     */
    public GameModel() {
        this.levelManager = new GameLevelManager();
        this.scoreManager = new GameScoreManager();

        this.entities = new ArrayList<>();
        this.setState(MenuState::new);

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
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayerName(final String newPlayerName) {
        Objects.requireNonNull(newPlayerName, "The new player name cannot be null");
        this.playerName = newPlayerName;
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
    public Optional<Antagonist> getAntagonist() {
        return this.findEntityOfType(Antagonist.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Target> getTarget() {
        return this.findEntityOfType(Target.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEntity(final Entity entity) {
        return this.levelManager.addEntity(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState() {
        return this.currentState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setState(final Supplier<GameState> newState) {
        if (currentState != null) {
            currentState.onExit(this);
        }
        currentState = Objects.requireNonNull(newState.get(), "The newSate cannot be null");
        currentState.onEnter(this);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeCommand(final Command command) {
        this.currentState.handleCommand(this, command);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final float deltaTime) {
        this.currentState.update(this, deltaTime);
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
    public void start() {
        // TODO: model.start()
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        this.scoreManager.saveScores();
        this.running = false;
    }

    private <T> Optional<T> findEntityOfType(final Class<T> type) {
        return this.entities.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .findFirst();
    }
}
