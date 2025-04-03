package it.unibo.coffebreak.model.entity.impl;

import it.unibo.coffebreak.model.entity.EnemyType;
import it.unibo.coffebreak.model.entity.api.Movable;
import it.unibo.coffebreak.model.utility.Dimension;
import it.unibo.coffebreak.model.utility.Position;
import it.unibo.coffebreak.model.utility.Vector2D;

/**
 * Represents an enemy in the game, which is a type of game entity that can move.
 */
public abstract class Enemy extends GameEntity implements Movable {

    /**
     * The current state of the enemy.
     */
    private EnemyType state;

    /**
     * The velocity of the enemy.
     */
    private Vector2D velocity;

    /**
     * Constructs a new Enemy with the specified position, dimension, state, and velocity.
     *
     * @param position the position of the enemy.
     * @param dimension the dimension of the enemy.
     * @param state the initial state of the enemy.
     * @param velocity the initial velocity of the enemy.
     */
    public Enemy(final Position position, final Dimension dimension, final EnemyType state, final Vector2D velocity) {
        super(position, dimension);
        this.state = state;
        this.velocity = new Vector2D(velocity.getX(), velocity.getY());
    }

    /**
     * Retrieves the current state of the enemy.
     *
     * @return the current state.
     */
    public EnemyType getState() {
        return this.state;
    }

    /**
     * Sets the state of the enemy.
     *
     * @param state the new state.
     */
    public void setState(final EnemyType state) {
        this.state = state;
    }

    /**
     * Retrieves the velocity of the enemy.
     *
     * @return the velocity.
     */
    public Vector2D getVelocity() {
        return new Vector2D(this.velocity.getX(), this.velocity.getY());
    }

    /**
     * Sets the velocity of the enemy.
     *
     * @param velocity the new velocity.
     */
    public void setVelocity(final Vector2D velocity) {
        this.velocity = new Vector2D(velocity.getX(), velocity.getY());
    }

    /**
     * Updates the enemy's state based on the elapsed time.
     *
     * @param deltaTime the time elapsed since the last update.
     */
    @Override
    public abstract void update(long deltaTime);
}
