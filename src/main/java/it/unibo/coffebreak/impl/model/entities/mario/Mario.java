package it.unibo.coffebreak.impl.model.entities.mario;

import java.util.Objects;
import java.util.function.Supplier;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.Movable;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.character.lives.LivesManager;
import it.unibo.coffebreak.api.model.entities.character.score.Score;
import it.unibo.coffebreak.api.model.entities.character.states.CharacterState;
import it.unibo.coffebreak.api.model.entities.collectible.Collectible;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.model.entities.structure.Tank;
import it.unibo.coffebreak.api.model.physics.Physics;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.common.Vector;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;
import it.unibo.coffebreak.impl.model.entities.mario.states.normal.NormalState;
import it.unibo.coffebreak.impl.model.entities.mario.states.withhammer.WithHammerState;
import it.unibo.coffebreak.impl.model.physics.GamePhysics;

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
 * @see MainCharacter
 * @author Grazia Bochdanovits de Kavna
 */
public class Mario extends AbstractEntity implements MainCharacter, Movable {

    private final LivesManager livesManager;
    private final Physics physics;
    private final Score score;

    private CharacterState currentState;

    /**
     * Creates a new Mario instance.
     *
     * @param position     the initial position of Mario
     * @param score        the score of Mario
     * @param livesManager the lives manager for Mario
     */
    public Mario(final Position position, final Score score, final LivesManager livesManager) {
        super(position);
        super.setDimension(super.getDimension().mulHeight(2));

        this.livesManager = livesManager;
        this.physics = new GamePhysics();
        this.score = score;

        changeState(NormalState::new);
    }

    /**
     * Moves Mario according to his current command, physic and state.
     * This method should be called every frame to update Mario's position.
     * 
     * @param deltaTime the time elapsed since the last frame (in seconds)
     * @throws IllegalArgumentException if deltaTime is negative
     */
    @Override
    public void update(final float deltaTime) {
        if (deltaTime < 0) {
            throw new IllegalArgumentException("DeltaTime cannot be negative");
        }
        this.currentState.update(this, deltaTime);
    }

    /**
     * Changes Mario's current state, properly handling transitions.
     *
     * @param stateSupplier the state to transition to (cannot be null)
     * @throws NullPointerException if newState is null
     */
    @Override
    public final void changeState(final Supplier<CharacterState> stateSupplier) {
        if (this.currentState != null) {
            currentState.onExit(this);
        }
        this.currentState = Objects.requireNonNull(stateSupplier.get(), "NewState cannot be null");
        currentState.onEnter(this);
    }

    /**
     * Handles collision with another entity by delegating to current state.
     *
     * @param other the entity colliding with Mario
     */
    @Override
    public void onCollision(final Entity other) {
        switch (other) {
            case Collectible collectible -> collectible.collect(this);
            case Platform platform -> platform.destroy();
            case Tank tank -> super.setVelocity(new Vector());
            default -> {
            }
        }

        this.currentState.handleCollision(this, other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void earnPoints(final int amount) {
        this.score.increase(amount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterState getCurrentState() {
        return this.currentState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScoreValue() {
        return this.score.getScore();
    }

    /**
     * Makes Mario lose one life and handles death/respawn.
     */
    @Override
    public void loseLife() {
        this.livesManager.loseLife();
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
}
