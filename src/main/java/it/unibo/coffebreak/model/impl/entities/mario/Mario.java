package it.unibo.coffebreak.model.impl.entities.mario;

import java.util.Objects;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.character.CharacterState;
import it.unibo.coffebreak.model.api.entities.collectible.Collectible;
import it.unibo.coffebreak.model.api.physics.Physics.Direction;
import it.unibo.coffebreak.model.impl.entities.GameEntity;
import it.unibo.coffebreak.model.impl.entities.collectible.hammer.Hammer;
import it.unibo.coffebreak.model.impl.entities.mario.states.ClimbingState;
import it.unibo.coffebreak.model.impl.entities.mario.states.DeadState;
import it.unibo.coffebreak.model.impl.entities.mario.states.JumpState;
import it.unibo.coffebreak.model.impl.entities.mario.states.NormalState;
import it.unibo.coffebreak.model.impl.entities.mario.states.WithHammerState;
import it.unibo.coffebreak.model.impl.physics.GamePhysics;
import it.unibo.coffebreak.model.impl.score.manager.GameScoreManager;
import it.unibo.coffebreak.model.impl.util.Dimension2D;
import it.unibo.coffebreak.model.impl.util.Position2D;
import it.unibo.coffebreak.model.impl.util.Vector2D;

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
 *   <li>{@link JumpState} - Jumping/falling physics</li>
 *   <li>{@link ClimbingState} - Ladder climbing behavior</li>
 *   <li>{@link WithHammerState} - Hammer power-up mode</li>
 *   <li>{@link DeadState} - Game over state</li>
 * </ul>
 * 
 * @author Grazia Bochdanovits de Kavna
 */
public class Mario extends GameEntity implements Character {

    /** Horizontal acceleration when moving (in pixels/second²). */
    private static final float MARIO_ACCELERATION = 10f;

    /** Maximum horizontal movement speed (in pixels/second). */
    private static final float MARIO_MAX_SPEED = 5f;

    /** Deceleration when stopping (in pixels/second²). */
    private static final float MARIO_DECELERATION = 8f;

    /** Gravity force applied when falling (in pixels/second²). */
    private static final float MARIO_GRAVITY = 9.8f;

    /** Initial upward velocity when jumping (in pixels/second). */
    private static final float JUMP_FORCE = -15f;

    private final GameLivesManager livesManager;
    private final GameScoreManager scoreManager;
    private final GamePhysics physics;
    private CharacterState currentState;
    private final Position2D startPosition;
    private Direction currentDirection = Direction.NONE;
    private boolean isOnGround;
    private boolean isClimbing;
    private boolean isJumping;
    private boolean facingRight = true;
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
        this.physics = new GamePhysics(MARIO_ACCELERATION, MARIO_MAX_SPEED, 
                                     MARIO_DECELERATION, MARIO_GRAVITY);
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
        if (currentState != null) {
            currentState.onExit(this);
        }
        currentState = newState;
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
        isClimbing = false;
        isJumping = false;
    }

    /**
     * Updates Mario's state and physics.
     *
     * @param deltaTime time elapsed since last update (in seconds)
     */
    @Override
    public void update(final float deltaTime) {
        if (!isAlive() && !(currentState instanceof DeadState)) {
            changeState(new DeadState());
        }
        currentState.update(this, deltaTime);
        if (!isClimbing) {
            physics.updateMovement(this, deltaTime, currentDirection);
        }
        if (getVelocity().getY() == 0 && !isOnGround) {
            isOnGround = true;
            isJumping = false;
        }
    }

    /**
     * Makes Mario jump if he's on the ground and the current state allows jumping.
     */
    @Override
    public void jump() {
        if (currentState.canJump() && isOnGround) {
            changeState(new JumpState());
            setVelocity(new Vector2D(getVelocity().getX(), JUMP_FORCE));
            isOnGround = false;
            isJumping = true;
        }
    }

    /**
     * Starts climbing behavior if Mario is touching a ladder.
     */
    @Override
    public void startClimbing() {
        if (currentState.canClimb()) {
            isClimbing = true;
            changeState(new ClimbingState());
        }
    }

    /**
     * Stops climbing behavior and returns to normal state.
     */
    @Override
    public void stopClimbing() {
        isClimbing = false;
        if (currentState instanceof ClimbingState) {
            changeState(new NormalState());
        }
    }

    /**
     * Uses Mario's current special item (e.g., hammer attack).
     */
    @Override
    public void useSpecialItem() {
        /*if (currentState.canUseSpecialItem()) {
            // Implementation would trigger hammer attack
        }*/
    }

    /**
     * Handles collision with another entity by delegating to current state.
     *
     * @param other the entity colliding with Mario
     */
    @Override
    public void onCollision(final Entity other) {
        currentState.handleCollision(this, other);
    }

    /**
     * Collects an item and applies its effects.
     *
     * @param item the collectible item
     * @throws NullPointerException if item is null
     * @throws IllegalStateException if Mario is dead
     */
    @Override
    public void collectItem(final Collectible item) {
        Objects.requireNonNull(item, "Item cannot be null");
        if (!isAlive()) {
            throw new IllegalStateException("Cannot collect items when dead");
        }
        if (!item.isCollected()) {
            item.collect(this);
            scoreManager.earnPoints(item.getPointsValue());
            if (item instanceof Hammer) {
                changeState(new WithHammerState());
            }
        }
    }

    /**
     * Makes Mario lose one life and handles death/respawn.
     */
    @Override
    public void loseLife() {
        livesManager.loseLife();
        if (isAlive()) {
            changeState(new NormalState());
            resetToInitialState();
        } else {
            changeState(new DeadState());
        }
    }

    /**
     * @return true if Mario is currently jumping
     */
    @Override
    public boolean isJumping() {
        return isJumping;
    }

    /**
     * @return true if Mario is currently climbing a ladder
     */
    @Override
    public boolean isClimbing() {
        return isClimbing;
    }

    /**
     * @return true if Mario can climb in his current state
     */
    @Override
    public boolean canClimb() {
        return currentState.canClimb();
    }

    /**
     * @return true if Mario is standing on solid ground
     */
    @Override
    public boolean isOnGround() {
        return isOnGround;
    }

    /**
     * @return true if Mario has remaining lives
     */
    @Override
    public boolean isAlive() {
        return livesManager.getLives() > 0;
    }

    /**
     * @return true if Mario is facing right
     */
    @Override
    public boolean isFacingRight() {
        return facingRight;
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
     * @return the current character state
     */
    @Override
    public CharacterState getCharacterState() {
        return currentState;
    }

    /**
     * Sets whether Mario is on the ground and handles state transitions.
     *
     * @param onGround true if Mario is on solid ground
     */
    @Override
    public void setOnGround(final boolean onGround) {
        isOnGround = onGround;
        if (onGround && currentState instanceof JumpState) {
            changeState(new NormalState());
        }
    }

    /**
     * Sets Mario's facing direction.
     *
     * @param facingRight true to face right, false to face left
     */
    @Override
    public void setFacingRight(final boolean facingRight) {
        this.facingRight = facingRight;
        if (facingRight) {
            this.currentDirection = Direction.RIGHT;
        } else {
            this.currentDirection = Direction.LEFT;
        }
    }

}
