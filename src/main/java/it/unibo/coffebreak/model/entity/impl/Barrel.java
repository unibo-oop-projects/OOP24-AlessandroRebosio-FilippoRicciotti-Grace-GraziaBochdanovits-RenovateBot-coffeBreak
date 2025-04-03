package it.unibo.coffebreak.model.entity.impl;

import it.unibo.coffebreak.model.entity.EnemyType;
import it.unibo.coffebreak.model.utility.Dimension;
import it.unibo.coffebreak.model.utility.Position;
import it.unibo.coffebreak.model.utility.Vector2D;

/**
 * Represents a barrel enemy in the game.
 */
public class Barrel extends Enemy {

    /**
     * Constructs a new Barrel with the specified position, dimension, state, and velocity.
     *
     * @param position the position of the barrel.
     * @param dimension the dimension of the barrel.
     * @param state the initial state of the barrel.
     * @param velocity the initial velocity of the barrel.
     */
    public Barrel(final Position position, final Dimension dimension, final EnemyType state, final Vector2D velocity) {
        super(position, dimension, state, velocity);
    }

    /**
     * Moves the barrel in the specified direction.
     *
     * @param direction the vector representing the direction and magnitude of the movement.
     */
    @Override
    public void move(final Vector2D direction) {
        applyMovement(direction);
    }

    /**
     * Updates the barrel's state based on the elapsed time.
     *
     * @param deltaTime the time elapsed since the last update.
     */
    @Override
    public void update(final long deltaTime) {
        applyMovement(this.getVelocity().multiply(deltaTime));
    }

    /**
     * Applies the movement to the barrel.
     *
     * @param displacement the displacement vector.
     */
    private void applyMovement(final Vector2D displacement) {
        this.setPosition(new Position(this.getPosition().x() + displacement.getX(), 
                                        this.getPosition().y() + displacement.getY()));
    }

}
