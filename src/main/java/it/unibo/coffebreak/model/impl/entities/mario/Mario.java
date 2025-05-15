package it.unibo.coffebreak.model.impl.entities.mario;

import java.util.Objects;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.character.CharacterState;
import it.unibo.coffebreak.model.api.entities.collectible.Collectible;
import it.unibo.coffebreak.model.impl.common.Dimension2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.common.Vector2D;
import it.unibo.coffebreak.model.impl.entities.AbstractEntity;
import it.unibo.coffebreak.model.impl.entities.collectible.hammer.Hammer;
import it.unibo.coffebreak.model.impl.entities.mario.states.NormalState;
import it.unibo.coffebreak.model.impl.entities.mario.states.WithHammerState;
import it.unibo.coffebreak.model.impl.score.manager.GameScoreManager;

/**
 * Represents the main player character (Mario) in the game.
 * This class implements the Character interface and extends GameEntity,
 * managing Mario's state, physics, and interactions with the game world.
 *
 * <p>Key features:
 * <ul>
 *   <li>State management using {@link CharacterState} pattern</li>
 *   <li>Physics-controlled movement and collisions</li>
 *   <li>Item collection and power-up handling</li>
 *   <li>Life and score management</li>
 * </ul>
 *
 * <p>States supported:
 * <ul>
 *   <li>{@link NormalState} - Default ground movement</li>
 *   <li>{@link WithHammerState} - Hammer power-up mode</li>
 * </ul>
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class Mario extends AbstractEntity implements Character {

    private final GameLivesManager livesManager;
    private final GameScoreManager scoreManager;
    private CharacterState currentState;
    private final Position2D startPosition;
    private boolean isOnGround;
    private final String playerName;

    /**
     * Creates a new Mario instance.
     *
     * @param position the initial position of Mario
     * @param dimension the dimensions of Mario's hitbox
     * @param scoreManager the score manager to track points
     * @param playerName the name of the player controlling Mario
     * @throws NullPointerException if scoreManager or playerName are null
     */
    public Mario(final Position2D position, final Dimension2D dimension,
                final GameScoreManager scoreManager, final String playerName) {
        super(position, dimension);
        this.startPosition = new Position2D(position.x(), position.y());
        this.livesManager = new GameLivesManager();
        this.scoreManager = Objects.requireNonNull(scoreManager);
        this.playerName = Objects.requireNonNull(playerName, "Player name cannot be null");
        this.currentState = new NormalState();
    }

    /**
     * Changes Mario's current state, properly handling transitions.
     *
     * @param newState the state to transition to (cannot be null)
     * @throws NullPointerException if newState is null
     */
    @Override
    public void changeState(final CharacterState newState) {
        Objects.requireNonNull(newState, "New state cannot be null");
        currentState.onExit(this);
        this.currentState = Objects.requireNonNull(newState);
        currentState.onEnter(this);
    }

    /**
     * Resets Mario to his initial state and position.
     * Used when respawning after losing a life.
     */
    @Override
    public void resetToInitialState() {
        changeState(new NormalState());
        setVelocity(new Vector2D(0, 0));
        setPosition(startPosition);
        setOnGround(true);
        setFacingRight(true); 
    }

    /**
     * Updates Mario's state and physics.
     *
     * @param deltaTime time elapsed since last update (in seconds)
     */
    @Override
    public void update(final float deltaTime) {
        currentState.update(this, deltaTime);
    }

    /**
     * Makes Mario jump if he's on the ground.
     */
    @Override
    public void jump() {
        if (currentState.canJump() && isOnGround) {
            setOnGround(false);
        }
    }

    /**
     * Handles collision with another entity by delegating to current state.
     *
     * @param other the entity colliding with Mario
     */
    @Override
    public void onCollision(final Entity other) {
        if (other instanceof Collectible) {
            ((Collectible) other).collect(this);
            scoreManager.earnPoints(((Collectible) other).getPointsValue());
        }
        if (other instanceof Hammer) {
            changeState(new WithHammerState());
        }
        currentState.handleCollision(this, other);
    }

    /**
     * Makes Mario lose one life and handles death/respawn.
     */
    @Override
    public void loseLife() {
        livesManager.loseLife();
        if (!isGameOver()) {
            changeState(new NormalState());
            resetToInitialState();
        }
        //TODO: gameOver
    }

    /**
     * @return true if Mario is standing on solid ground
     */
    @Override
    public boolean isOnGround() {
        return isOnGround;
    }

    /**
     * @return true if Mario has zero remaining lives
     */
    @Override
    public boolean isGameOver() {
        return !livesManager.isAlive();
    }

    /**
     * @return the lives manager instance
     */
    @Override
    public GameLivesManager getLivesManager() {
        return livesManager;
    }

    /**
     * @return the current number of lives remaining
     */
    @Override
    public int getLives() {
        return livesManager.getLives();
    }

    /**
     * @return the score manager instance
     */
    @Override
    public GameScoreManager getScoreManager() {
        return scoreManager;
    }

    /**
     * @return the player's name
     */
    @Override
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets whether Mario is on the ground and handles state transitions.
     *
     * @param onGround true if Mario is on solid ground
     */
    @Override
    public void setOnGround(final boolean onGround) {
        isOnGround = onGround;
    }

}
