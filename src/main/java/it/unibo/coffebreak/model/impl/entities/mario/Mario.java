package it.unibo.coffebreak.model.impl.entities.mario;

import java.util.Objects;
import java.util.function.Supplier;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.Movable;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.character.states.CharacterState;
import it.unibo.coffebreak.model.api.entities.collectible.Collectible;
import it.unibo.coffebreak.model.api.entities.structure.Platform;
import it.unibo.coffebreak.model.api.physics.Physics;
import it.unibo.coffebreak.model.api.score.ScoreManager;
import it.unibo.coffebreak.model.impl.common.BoundingBox2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.common.Vector2D;
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
    private Command command = Command.NONE;
    private boolean isOnGround;
    private boolean isJumping;

    /**
     * Creates a new Mario instance.
     *
     * @param position     the initial position of Mario
     * @param dimension    the dimensions of Mario's hitbox
     * @param physics      the physics component of Mario
     * @throws NullPointerException if scoreManager or playerName are null
     */
    public Mario(final Position2D position, final BoundingBox2D dimension, final Physics physics) {
        super(position, dimension);
        this.livesManager = new GameLivesManager();
        this.scoreManager = new GameScoreManager();
        this.physics = Objects.requireNonNull(physics);
        changeState(() -> new NormalState());
        this.isOnGround = true;
        this.isJumping = false;
    }

    /**
     * Moves Mario according to his current command, physic and state.
     * This method should be called every frame to update Mario's position.
     * 
     * @param deltaTime the time elapsed since the last frame (in seconds)
     * @throws IllegalArgumentException if deltaTime is negative
     */
    @Override
    public void move(final float deltaTime) {
        if (deltaTime < 0) {
            throw new IllegalArgumentException("DeltaTime cannot be negative");
        }

        final Vector2D newVelocity;

        if (currentState.canClimb()) {
            newVelocity = physics.calculateY(deltaTime, command);
        } else if (isJumping || !isOnGround) {
            newVelocity = physics.calculateX(deltaTime, command)
                    .sum(physics.calculateY(deltaTime, command));
        } else {
            newVelocity = physics.calculateX(deltaTime, command);
        }

        setVelocity(newVelocity);
        setPosition(getPosition().sum(newVelocity.multiply(deltaTime)));
    }

    /**
     * Changes Mario's current state, properly handling transitions.
     *
     * @param stateSupplier the state to transition to (cannot be null)
     * @throws NullPointerException if newState is null
     */
    @Override
    public final void changeState(final Supplier<CharacterState> stateSupplier) {
        final CharacterState newState = Objects.requireNonNull(
            stateSupplier.get(), "NewState cannot be null");
        currentState.onExit(this);
        this.currentState = newState;
        currentState.onEnter(this);
    }

    /**
     * Handles collision with another entity by delegating to current state.
     *
     * @param other the entity colliding with Mario
     */
    @Override
    public void onCollision(final Entity other) {
        isOnGround = false;
        currentState.handleCollision(this, other);
        if (other instanceof final Collectible collectible) {
            collectible.collect(this);
        }
        if (other instanceof Platform) {
            isOnGround = true;
            isJumping = false;
        }
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
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "ScoreManager is intentionally shared and mutable")
    public final ScoreManager getScoreManager() {
        return this.scoreManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCommand(final Command command) {
        this.command = command;
    }
}
