package it.unibo.coffebreak.model.entity.impl;

import java.util.Objects;

import it.unibo.coffebreak.model.entity.EnemyType;
import it.unibo.coffebreak.model.entity.api.Movable;
import it.unibo.coffebreak.model.utility.Dimension;
import it.unibo.coffebreak.model.utility.Position;
import it.unibo.coffebreak.model.utility.Vector2D;

/**
 * Abstract base class for all enemy entities in the game. Implements core movement
 * functionality using the Strategy pattern to allow different movement behaviors.
 * 
 * @see Movable
 * @see EnemyType
 */
public abstract class Enemy extends GameEntity {

    /**
     * The current state of the enemy.
     */
    private EnemyType state;

    /**
     * The velocity of the enemy.
     */
    private Vector2D velocity;

    /**
     * The movement strategy of the enemy.
     */
    private final Movable movementStrategy;

    /**
     * Constructs a new Enemy with the specified attributes.
     * 
     * @param position the initial position (non-null)
     * @param dimension the entity dimensions (non-null)
     * @param state the initial enemy type (non-null)
     * @param velocity the initial velocity (non-null)
     * @throws NullPointerException if any parameter is null
     */
    public Enemy(final Position position, final Dimension dimension, final EnemyType state, final Vector2D velocity) {
        super(Objects.requireNonNull(position), Objects.requireNonNull(dimension));
        this.state = Objects.requireNonNull(state);
        this.velocity = new Vector2D(Objects.requireNonNull(velocity).getX(), velocity.getY());
        this.movementStrategy = createMovementStrategy();
    }

    /**
     * Creates an appropriate movement strategy based on the enemy's type.
     * This implementation uses the enemy's current state to determine which
     * concrete strategy to instantiate.
     *
     * @return the configured movement strategy (never {@code null})
     */
    private Movable createMovementStrategy() {
        return switch (state) {
            case BARREL -> new BarrelMovementStrategy();
            case FIRE -> new FireMovementStrategy();
        };
    }

    /**
     * Updates the enemy's state based on elapsed time. Automatically handles
     * frame-rate independent movement calculations.
     *
     * @param deltaTime the time elapsed since last update in milliseconds (must be positive)
     * @throws NullPointerException if deltaTime is null
     */
    @Override
    public void update(final long deltaTime) {
        Objects.requireNonNull(deltaTime, "DeltaTime cannot be null");
        final Vector2D updateVelocity = new Vector2D(velocity.getX() * deltaTime / 1000.0f,
                                            velocity.getY() * deltaTime / 1000.0f);
        move(updateVelocity);
        specificUpdate(deltaTime);
    }

    /**
     * Applies movement using the current movement strategy.
     * 
     * @param direction the movement vector (non-null)
     * @throws NullPointerException if direction is null
     */
    public void move(final Vector2D direction) {
        final Position newPosition = movementStrategy.move(getPosition(), Objects.requireNonNull(direction));
        setPosition(newPosition);
    }

    /**
     * Hook method for enemy-specific update logic. Called after standard movement updates.
     * 
     * @param deltaTime the time elapsed since last update in milliseconds
     */
    protected void specificUpdate(final long deltaTime) {
        // Default implementation does nothing
    }

    /**
     * Gets the current movement strategy.
     * 
     * @return the active movement strategy (never null)
     */
    public Movable getMovementStrategy() {
        return this.movementStrategy;
    }

    /**
     * Gets the current enemy type.
     * 
     * @return the enemy type (never null)
     */
    public EnemyType getState() {
        return state;
    }

    /**
     * Changes the enemy type. Note: Does not automatically change movement strategy.
     * 
     * @param state the new enemy type (non-null)
     * @throws NullPointerException if state is null
     */
    public void setState(final EnemyType state) {
        this.state = Objects.requireNonNull(state, "State cannot be null");
    }

    /**
     * Gets a defensive copy of the current velocity vector.
     * 
     * @return the velocity vector in units/second (never null)
     */
    public Vector2D getVelocity() {
        return new Vector2D(velocity.getX(), velocity.getY());
    }

    /**
     * Updates the velocity vector.
     * 
     * @param velocity the new velocity vector (non-null)
     * @throws NullPointerException if velocity is null
     */
    public void setVelocity(final Vector2D velocity) {
        this.velocity = new Vector2D(Objects.requireNonNull(velocity).getX(),
                                        velocity.getY());
    }
}
