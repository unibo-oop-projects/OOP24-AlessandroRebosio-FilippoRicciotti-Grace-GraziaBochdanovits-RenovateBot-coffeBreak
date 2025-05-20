package it.unibo.coffebreak.model.impl.entities.mario;

import java.util.Objects;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.Movable;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.character.states.CharacterState;
import it.unibo.coffebreak.model.api.entities.collectible.Collectible;
import it.unibo.coffebreak.model.api.entities.structure.Platform;
import it.unibo.coffebreak.model.api.physics.Physics;
import it.unibo.coffebreak.model.api.score.ScoreManager;
import it.unibo.coffebreak.model.impl.common.Dimension2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.AbstractEntity;
import it.unibo.coffebreak.model.impl.entities.GameLivesManager;
import it.unibo.coffebreak.model.impl.entities.mario.states.normal.NormalState;
import it.unibo.coffebreak.model.impl.entities.mario.states.withhammer.WithHammerState;
import it.unibo.coffebreak.model.impl.score.GameScoreManager;

/**
 * Represents the main player character (Mario) in the game.
 * This class implements the Character interface and extends GameEntity,
 * managing Mario's state, physics, and interactions with the game world.
 *
 * <p>
 * Key features:
 * <ul>
 * <li>State management using {@link CharacterState} pattern</li>
 * <li>Physics-controlled movement and collisions</li>
 * <li>Item collection and power-up handling</li>
 * <li>Life and score management</li>
 * </ul>
 *
 * <p>
 * States supported:
 * <ul>
 * <li>{@link NormalState} - Default ground movement</li>
 * <li>{@link WithHammerState} - Hammer power-up mode</li>
 * </ul>
 * 
 * @see AbstractEntity
 * @see Character
 * @author Grazia Bochdanovits de Kavna
 */
public class Mario extends AbstractEntity implements Character, Movable {

    private final GameLivesManager livesManager;
    private final GameScoreManager scoreManager;
    private final Physics physics;
    private CharacterState currentState;
    private boolean isOnGround;
    private boolean isClimbing;

    /**
     * Creates a new Mario instance.
     *
     * @param position     the initial position of Mario
     * @param dimension    the dimensions of Mario's hitbox
     * @param scoreManager the score manager to track points
     * @param physics      the physics component of Mario
     * @throws NullPointerException if scoreManager or playerName are null
     */
    public Mario(final Position2D position, final Dimension2D dimension,
            final GameScoreManager scoreManager, final Physics physics) {
        super(position, dimension);
        this.livesManager = new GameLivesManager();
        this.scoreManager = Objects.requireNonNull(scoreManager);
        this.physics = Objects.requireNonNull(physics);
        this.currentState = new NormalState();
        this.isOnGround = true;
        this.isClimbing = false;
    }

    /**
     * Changes Mario's current state, properly handling transitions.
     *
     * @param newState the state to transition to (cannot be null)
     * @throws NullPointerException if newState is null
     */
    @Override
    public void changeState(final CharacterState newState) {
        currentState.onExit(this);
        this.currentState = Objects.requireNonNull(newState, "New state cannot be null");
        currentState.onEnter(this);
    }

    /**
     * Updates Mario's state and physics.
     *
     * @param deltaTime time elapsed since last update (in seconds)
     */
    @Override
    public void update(final float deltaTime) {
        if (!isOnGround && !isClimbing) {
            setVelocity(getVelocity().sum(physics.calculateY(deltaTime, Command.NONE)));
        }
        setPosition(getPosition().sum(getVelocity().multiply(deltaTime)));
        currentState.update(this, deltaTime);
    }

    /**
     * Handles collision with another entity by delegating to current state.
     *
     * @param other the entity colliding with Mario
     */
    @Override
    public void onCollision(final Entity other) {
        if (other instanceof Platform) {
            isOnGround = true;
            isClimbing = false;
        }
        if (other instanceof final Collectible collectible) {
            collectible.collect(this);
        }
        currentState.handleCollision(this, other);
    }

    /**
     * Makes Mario lose one life and handles death/respawn.
     */
    @Override
    public void loseLife() {
        this.livesManager.loseLife();
    }

    /**
     * @return true if Mario is standing on solid ground
     */
    @Override
    public boolean isOnGround() {
        return this.isOnGround;
    }

    /**
     * @return true if Mario has zero remaining lives
     */
    @Override
    public boolean isGameOver() {
        return !livesManager.isAlive();
    }

    /**
     * @return the current number of lives remaining
     */
    @Override
    public int getLives() {
        return livesManager.getLives();
    }

    /**
     * Moves Mario according to his current velocity and state.
     * This method should be called every frame to update Mario's position.
     * 
     * @param deltaTime the time elapsed since the last frame (in seconds)
     * @throws IllegalArgumentException if deltaTime is negative
     */
    @Override
    public void move(final float deltaTime) {
        // TODO: Auto-generated method stub
    }

    /**
     * Gets Mario's current score.
     * 
     * @return the current score value as integer
     */
    @Override
    public int getScore() {
        return this.scoreManager.getCurrentScore();
    }

    /**
     * Gets the score manager instance associated with Mario.
     * This allows external systems to modify/query score-related operations.
     * 
     * @return the ScoreManager instance handling Mario's score
     */
    @Override
    public ScoreManager getScoreManager() {
        return this.scoreManager;
    }
}
