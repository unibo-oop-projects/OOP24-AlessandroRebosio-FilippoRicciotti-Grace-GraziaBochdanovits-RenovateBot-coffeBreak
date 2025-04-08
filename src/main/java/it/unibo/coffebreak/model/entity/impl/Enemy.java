package it.unibo.coffebreak.model.entity.impl;

import java.util.Objects;

import it.unibo.coffebreak.model.entity.EnemyType;
import it.unibo.coffebreak.model.entity.api.Movable;
import it.unibo.coffebreak.model.utility.Dimension;
import it.unibo.coffebreak.model.utility.Position;
import it.unibo.coffebreak.model.utility.Vector2D;

/**
 * Represents an enemy in the game, which is a type of game entity that can move.
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
    private Movable movementStrategy;

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
        this.movementStrategy = createMovementStrategy(state);
    }

    /**
     * Factory method for movement strategies.
     */
    protected Movable createMovementStrategy(EnemyType type) {
        return switch (type) {
            case BARREL -> new BarrelMovementStrategy();
            case FIRE -> new FireMovementStrategy();
            default -> throw new IllegalArgumentException("Unknown enemy type: " + type);
        };
    }

     /**
     * Updates the enemy's state based on elapsed time.
     * Implements frame-rate independent movement.
     * 
     * @param deltaTime time elapsed in milliseconds
     */
    @Override
    public void update(final long deltaTime) {
        Objects.requireNonNull(deltaTime, "DeltaTime cannot be null");
        final Vector2D updateVelocity = new Vector2D(velocity.getX() * deltaTime/1000.0f,
                                            velocity.getY() * deltaTime/1000.0f);
        move(updateVelocity);
        specificUpdate(deltaTime);
    }

    /**
     * Applies immediate movement using the current strategy.
     * @param direction movement vector (non-null)
     */
    public void move(final Vector2D direction) {
        final Position newPosition = movementStrategy.move(getPosition(), Objects.requireNonNull(direction));
        setPosition(newPosition);
    }
    
    protected void specificUpdate(final long deltaTime) {
    }

    public Movable getMovementStrategy() {
        return this.movementStrategy;
    }

    public EnemyType getState() {
        return state;
    }

    public void setState(final EnemyType state) {
        this.state = Objects.requireNonNull(state);
    }

    public Vector2D getVelocity() {
        return new Vector2D(velocity.getX(), velocity.getY());
    }

    public void setVelocity(final Vector2D velocity) {
        this.velocity = new Vector2D(
            Objects.requireNonNull(velocity).getX(),
            velocity.getY()
        );
    }
}
