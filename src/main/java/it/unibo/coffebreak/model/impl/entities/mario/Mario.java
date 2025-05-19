package it.unibo.coffebreak.model.impl.entities.mario;

import java.util.Objects;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.character.CharacterState;
import it.unibo.coffebreak.model.api.entities.collectible.Collectible;
import it.unibo.coffebreak.model.api.entities.platform.Platform;
import it.unibo.coffebreak.model.api.physics.Physics;
import it.unibo.coffebreak.model.impl.common.Dimension2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.common.Vector2D;
import it.unibo.coffebreak.model.impl.entities.AbstractEntity;
import it.unibo.coffebreak.model.impl.entities.collectible.hammer.Hammer;
import it.unibo.coffebreak.model.impl.entities.mario.states.NormalState;
import it.unibo.coffebreak.model.impl.entities.mario.states.WithHammerState;
import it.unibo.coffebreak.model.impl.score.GameScoreManager;

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
 * @see AbstractEntity
 * @see Character
 * @author Grazia Bochdanovits de Kavna
 */
public class Mario extends AbstractEntity implements Character {

    private final GameLivesManager livesManager;
    private final GameScoreManager scoreManager;
    private final Physics physics;
    private CharacterState currentState;
    private final Position2D startPosition;
    private boolean isOnGround;
    private boolean isClimbing;

    /**
     * Creates a new Mario instance.
     *
     * @param position the initial position of Mario
     * @param dimension the dimensions of Mario's hitbox
     * @param scoreManager the score manager to track points
     * @param physics the physics component of Mario
     * @throws NullPointerException if scoreManager or playerName are null
     */
    public Mario(final Position2D position, final Dimension2D dimension,
                final GameScoreManager scoreManager, final Physics physics) {
        super(position, dimension);
        this.startPosition = new Position2D(position.x(), position.y());
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
        Objects.requireNonNull(newState, "New state cannot be null");
        currentState.onExit(this);
        this.currentState = newState;
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
        isOnGround = true;
        setFacingRight(true); 
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
        if (other instanceof Collectible) {
            ((Collectible) other).collect(this);
            scoreManager.earnPoints(((Collectible) other).getPointsValue());
        }
        if (other instanceof Hammer) {
            isClimbing = false;
            changeState(new WithHammerState());
        }
        currentState.handleCollision(this, other);
    }

    /**
     * Makes Mario jump if he's on the ground.
     */
    @Override
    public void jump(final float deltaTime) {
        if (isOnGround) {
            setVelocity(physics.calculateY(deltaTime, Command.JUMP));
            isOnGround = false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveLeft(final float deltaTime) {
        if (!isClimbing) {
            setFacingRight(false);
            final Vector2D movement = physics.calculateX(deltaTime, Command.MOVE_LEFT);
            setVelocity(new Vector2D(movement.x(), getVelocity().y()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveRight(final float deltaTime) {
        if (!isClimbing) {
            setFacingRight(true);
            final Vector2D movement = physics.calculateX(deltaTime, Command.MOVE_RIGHT);
            setVelocity(new Vector2D(movement.x(), getVelocity().y()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void climbUp(final float deltaTime) {
        if (currentState.canClimb()) {
            setVelocity(physics.calculateY(deltaTime, Command.MOVE_UP));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void climbDown(final float deltaTime) {
        if (currentState.canClimb()) {
            setVelocity(physics.calculateY(deltaTime, Command.MOVE_DOWN));
        }
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
     * @return the lives manager instance
     */
    @Override
    public GameLivesManager getLivesManager() {
        return this.livesManager;
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
        return this.scoreManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Physics getPlayerPhysics() {
        return this.physics;
    }

    /**
     * @return the current Mario state
     */
    @Override
    public CharacterState getCurrentState() {
        return this.currentState;
    }
}
