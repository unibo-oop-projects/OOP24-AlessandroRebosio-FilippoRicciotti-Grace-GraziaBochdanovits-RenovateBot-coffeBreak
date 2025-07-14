package it.unibo.coffebreak.impl.model.entities.enemy;

import it.unibo.coffebreak.api.model.entities.PhysicsEntity;
import it.unibo.coffebreak.api.model.entities.enemy.Enemy;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;

/**
 * An abstract implementation of the {@link Enemy} interface that extends
 * {@link AbstractEntity}.
 * This class serves as a base class for all enemy entities in the game,
 * providing common functionality
 * and properties that all enemies share.
 * <p>
 * The class implements basic enemy lifecycle management including destruction
 * state tracking.
 * </p>
 * 
 * <h3>Core Functionality:</h3>
 * <ul>
 * <li>Maintains destruction state for all enemy types</li>
 * <li>Provides base implementation for enemy lifecycle methods</li>
 * <li>Serves as foundation for specialized enemy implementations</li>
 * </ul>
 *
 * @see Enemy
 * @see AbstractEntity
 * @author Grazia Bochdanovits de Kavna
 */
public abstract class AbstractEnemy extends AbstractEntity implements Enemy, PhysicsEntity {

    private boolean isDestroyed;
    private boolean onPlatform = true;
    private boolean movingRight = true;

    private final int value;

    /**
     * Constructs a new AbstractEnemy with the specified position, dimension, and
     * score value.
     * 
     * @param position  the initial position of the enemy in 2D space (cannot be
     *                  null)
     * @param dimension the dimension of the enemy in the game world
     * @param value     the score value awarded for destroying this enemy
     * @throws NullPointerException if position or dimension are null
     */
    public AbstractEnemy(final Position position, final BoundigBox dimension, final int value) {
        super(position, dimension);
        this.value = value;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Marks the enemy as destroyed, removing it from gameplay.
     * </p>
     */
    @Override
    public void destroy() {
        if (!this.isDestroyed) {
            this.isDestroyed = true;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Checks the destruction state of the enemy. The enemy should be considered
     * inactive and removed from gameplay when this returns true.
     * </p>
     *
     * @return true if the enemy has been destroyed, false otherwise
     */
    @Override
    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    /**
     * Inverts the current movement direction of the enemy.
     */
    protected void invertDirection() {
        this.movingRight = !this.movingRight;
    }

    /**
     * Calculates the horizontal movement speed based on the current direction.
     * <p>
     * The actual speed value depends on both the base speed parameter and the
     * current movement direction:
     * </p>
     * <ul>
     * <li>Positive speed when moving right</li>
     * <li>Negative speed when moving left</li>
     * </ul>
     *
     * @param baseSpeed the absolute speed value (always positive)
     * @return the signed speed value based on current direction
     */
    protected float getHorizontalSpeed(final float baseSpeed) {
        return this.movingRight ? baseSpeed : -baseSpeed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPlatformLand() {
        this.onPlatform = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPlatformLeave() {
        this.onPlatform = false;
    }

    /**
     * @return if the enemy is on platform
     */
    protected boolean isOnPlatform() {
        return this.onPlatform;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int killValue() {
        return this.value;
    }
}
