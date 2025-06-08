package it.unibo.coffebreak.impl.model;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import it.unibo.coffebreak.api.common.Command;
import it.unibo.coffebreak.api.model.Model;
import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.level.LevelManager;
import it.unibo.coffebreak.api.model.score.ScoreManager;
import it.unibo.coffebreak.api.model.states.GameState;
import it.unibo.coffebreak.impl.model.level.GameLevelManager;
import it.unibo.coffebreak.impl.model.score.GameScoreManager;
import it.unibo.coffebreak.impl.model.states.menu.MenuState;

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

    private final LevelManager levelManager = new GameLevelManager();
    private final ScoreManager scoreManager = new GameScoreManager();

    private GameState currentState;
    private volatile boolean running;

    /**
     * Constructs a new GameModel with default initial state.
     * Initializes empty entities list, menu state, and running status.
     */
    public GameModel() {
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
    public MainCharacter getMainCharacter() {
        return this.levelManager.getPlayar();
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
        currentState = Objects.requireNonNull(newState.get(), "New state cannot be null");
        currentState.onEnter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEntryInLeaderBoard() {
        this.scoreManager.addEntryInLeaderBoard("TODO");
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
    public void cleanEntities() {
        this.levelManager.cleanEntities();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetEntities() {
        this.levelManager.resetEntities();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void transformEntities() {
        this.levelManager.transformEntities();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void calculateBonus(final float deltaTime) {
        this.levelManager.calculateBonus(deltaTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextMap() {
        if (this.levelManager.advanceLevel()) {
            this.getMainCharacter().earnPoints(this.scoreManager.getCurrentBonus());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(final Command command) {
        this.currentState.handleCommand(this, command);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        this.levelManager.loadNextEnitites();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        this.running = false;
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
    public void update(final float deltaTime) {
        this.currentState.update(this, deltaTime);
    }
}
