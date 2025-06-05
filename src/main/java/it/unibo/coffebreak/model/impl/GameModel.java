package it.unibo.coffebreak.model.impl;

import java.lang.annotation.Target;
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
 * Maintains the game state including entities, player information, and state
 * management.
 * Provides thread-safe access to model components through proper
 * synchronization.
 * </p>
 * 
 * @author Alessandro Rebosio
 */
public class GameModel implements Model {

    private final LevelManager levelManager;
    private final ScoreManager scoreManager;

    private GameState currentState;
    private String playerName;
    private volatile boolean running;

    /**
     * Constructs a new GameModel with default initial state.
     * Initializes empty entities list, menu state, and running status.
     */
    public GameModel() {
        this.levelManager = new GameLevelManager();
        this.scoreManager = new GameScoreManager();

        this.setState(MenuState::new);

        this.running = true;
    }

    /**
     * {@inheritDoc}
     * The returned list is unmodifiable to maintain encapsulation.
     */
    @Override
    public List<Entity> getEntities() {
        return this.levelManager.getEntities();
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
    public Optional<Character> getPlayer() {
        return findEntityOfType(Character.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Antagonist> getAntagonist() {
        return findEntityOfType(Antagonist.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Target> getTarget() {
        return findEntityOfType(Target.class);
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
    public boolean isRunning() {
        return this.running;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayerName(final String newPlayerName) {
        Objects.requireNonNull(newPlayerName, "Player name cannot be null");
        this.playerName = newPlayerName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setState(final Supplier<GameState> newState) {
        if (currentState != null) {
            currentState.onExit(this);
        }
        currentState = Objects.requireNonNull(newState.get(), "New state cannot be null");
        currentState.onEnter(this);
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
    public void addEntryInLeaderBoard() {
        this.scoreManager.addEntryInLeaderBoard(this.playerName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cleanEntities() {
        this.levelManager.cleanEntities();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void calculateBonus(final float deltaTime) {
        this.scoreManager.calculateBonus(deltaTime);
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
    public void start() {
        this.getPlayer().ifPresent(p -> p.setScoreManager(scoreManager));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        this.scoreManager.saveScores();
        this.running = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final float deltaTime) {
        this.currentState.update(this, deltaTime);
    }

    /**
     * Finds the first entity of the specified type in the entities list.
     * 
     * @param <T>  the type of entity to find
     * @param type the class object representing the entity type
     * @return an Optional containing the found entity, or empty if not found
     */
    private <T> Optional<T> findEntityOfType(final Class<T> type) {
        return this.getEntities().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .findFirst();
    }
}
