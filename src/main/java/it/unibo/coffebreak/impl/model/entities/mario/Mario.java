package it.unibo.coffebreak.impl.model.entities.mario;

import java.util.Objects;
import java.util.function.Supplier;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.character.MainCharacter;
import it.unibo.coffebreak.api.model.entities.character.lives.LivesManager;
import it.unibo.coffebreak.api.model.entities.character.score.Score;
import it.unibo.coffebreak.api.model.entities.character.states.CharacterState;
import it.unibo.coffebreak.api.model.entities.collectible.Collectible;
import it.unibo.coffebreak.api.model.entities.npc.Princess;
import it.unibo.coffebreak.api.model.entities.structure.Platform;
import it.unibo.coffebreak.api.model.physics.Physics;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.common.Vector;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;
import it.unibo.coffebreak.impl.model.entities.mario.lives.GameLivesManager;
import it.unibo.coffebreak.impl.model.entities.mario.score.GameScore;
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
public class Mario extends AbstractEntity implements MainCharacter {

    private final LivesManager livesManager = new GameLivesManager();
    private final Physics physics = new GamePhysics();
    private final Score score = new GameScore();

    private CharacterState currentState;

    private boolean onPlatform;
    private final boolean isFacingRight;
    private boolean isJumping;

    /**
     * Creates a new Mario instance.
     *
     * @param position  the initial position of Mario
     * @param dimension the 2D dimension of the Mario (cannot be null)
     */
    public Mario(final Position position, final BoundigBox dimension) {
        super(position, dimension);

        this.isFacingRight = true;

        this.changeState(NormalState::new);
    }

    /**
     * Updates the state of the Mario entity for the current frame.
     * <p>
     * Applies gravity to Mario's vertical velocity unless he is on a platform,
     * in which case the vertical velocity is set to zero. Updates Mario's position
     * based on the calculated velocity and resets the platform state.
     * </p>
     *
     * @param deltaTime the time elapsed since the last update, in seconds
     */
    @Override
    public void update(final float deltaTime) {
        final float vx = this.physics.moveLeft(deltaTime).x();
        final float vy = !this.onPlatform ? this.physics.gravity(deltaTime).y() : 0f;

        super.setPosition(super.getPosition().sum(new Vector(vx, vy)));

        this.onPlatform = false;
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
        Objects.requireNonNull(other, "Colliding entity cannot be null");
        switch (other) {
            case final Platform platform -> this.handlePlatformCollision(platform);
            case final Collectible collectible -> collectible.collect(this);
            case final Princess princess -> princess.rescue();
            default -> {
            }
        }
        this.currentState.handleCollision(this, other);
    }

    private void handlePlatformCollision(final Platform platform) {
        switch (platform.getCollisionSide(this)) {
            case TOP -> {
                this.onPlatform = true;
                this.isJumping = false;
            }
            default -> {
            }
            // case LEFT -> {
            // if (getVelocity().x() > 0 && !currentState.isClimbing()) {
            // setVelocity(new Vector(0, getVelocity().y()));
            // setPosition(new Position(platform.getPosition().x() - getDimension().width()
            // - epsilon,
            // getPosition().y()));
            // }
            // }
            // case RIGHT -> {
            // if (getVelocity().x() < 0 && !currentState.isClimbing()) {
            // setVelocity(new Vector(0, getVelocity().y()));
            // setPosition(new Position(platform.getPosition().x() +
            // platform.getDimension().width() + epsilon,
            // getPosition().y()));
            // }
            // }
            // default -> {
            // this.onPlatform = false;
            // }
        }
        platform.destroy();
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

    /**
     * {@inheritDoc}
     * Resets the number of lives back to the initial value defined.
     */
    @Override
    public void resetLives() {
        this.livesManager.resetLives();
    }

    /**
     * @return true if Mario is facing right, false if facing left
     */
    @Override
    public boolean isFacingRight() {
        return this.isFacingRight;
    }

    /**
     * @return true if Mario is jumping, false otherwise
     */
    @Override
    public boolean isJumping() {
        return this.isJumping;
    }
}
