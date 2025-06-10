package it.unibo.coffebreak.impl.model.entities.enemy;

import it.unibo.coffebreak.api.model.entities.enemy.Enemy;
import it.unibo.coffebreak.impl.common.BoundingBox2D;
import it.unibo.coffebreak.impl.common.Position2D;
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
public abstract class AbstractEnemy extends AbstractEntity implements Enemy {

    /** Tracks whether this enemy has been destroyed. */
    private boolean isDestroyed;

    /**
     * Constructs a new AbstractEnemy with the specified position and dimension.
     * 
     * @param position  the initial position of the enemy in 2D space (cannot be
     *                  null)
     * @param dimension the size/dimensions of the enemy (cannot be null)
     * @throws NullPointerException if position or dimension are null
     */
    public AbstractEnemy(final Position2D position, final BoundingBox2D dimension) {
        super(position, dimension);
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
}
