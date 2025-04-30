package it.unibo.coffebreak.model.impl.entity.enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unibo.coffebreak.model.api.entity.BarrelTransformationObserver;
import it.unibo.coffebreak.model.impl.entity.EnemyType;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;
import it.unibo.coffebreak.model.impl.utility.Vector2D;

/**
 * Concrete implementation of an enemy representing a rolling barrel.
 * <p>
 * Barrels typically move in straight lines and bounce off obstacles.
 * The actual movement pattern is determined by the {@link EnemyType#BARREL} movement strategy.
 * 
 * <p>Special behavior:
 * <ul>
 *   <li>Can transform into fire when certain conditions are met</li>
 *   <li>Notifies registered observers upon transformation</li>
 *   <li>Maintains transformation state</li>
 * </ul>
 * 
 * @see Enemy
 * @see EnemyType#BARREL
 * @see BarrelTransformationObserver
 */
public class Barrel extends Enemy {

    private static final int START_COORDINATE_X = 50;
    private final List<BarrelTransformationObserver> transformationObservers = new ArrayList<>();
    private final boolean canTransformToFire;

    /**
     * Constructs a Barrel enemy with the specified parameters.
     *
     * @param position the position of the barrel enemy
     * @param dimension the dimension of the barrel enemy
     * @param velocity the velocity of the barrel enemy
     *  @param canTransformToFire whether this barrel can transform into fire
     * @throws IllegalArgumentException if any required parameter is null
     */
    public Barrel(final Position position, final Dimension dimension, final Vector2D velocity, final Boolean canTransformToFire) {
        super(Objects.requireNonNull(position), Objects.requireNonNull(dimension), 
              EnemyType.BARREL, Objects.requireNonNull(velocity));
        this.canTransformToFire = canTransformToFire;
    }

    /**
     * Registers an observer for barrel transformation events.
     *
     * @param observer the observer to register (not null)
     * @throws IllegalArgumentException if observer is null
     */
    public void addTransformationObserver(final BarrelTransformationObserver observer) {
        Objects.requireNonNull(observer, "Observer cannot be null");
        transformationObservers.add(observer);
    }

    /**
     * Performs barrel-specific updates each game tick.
     * Checks for transformation conditions if applicable.
     *
     * @param deltaTime the time elapsed since last update in milliseconds
     */
    @Override
    protected void specificUpdate(final long deltaTime) {
        if (canTransformToFire) {
            checkTransformationToFire();
        }
    }

    /**
     * Checks if the barrel should transform into fire and triggers the transformation.
     */
    private void checkTransformationToFire() {
        if (shouldTransformToFire()) {
            final Vector2D reversedVelocity = new Vector2D(-getVelocity().getX(), getVelocity().getY());
            notifyTransformationToFire(reversedVelocity);
        }
    }

    /**
     * Determines if transformation conditions are met.
     *
     * @return true if the barrel should transform into fire
     */
    private boolean shouldTransformToFire() {
        return getPosition().x() >= START_COORDINATE_X;
    }

    /**
     * Notifies all registered observers about the transformation to fire.
     *
     * @param newVelocity the velocity for the new fire entity
     */
    private void notifyTransformationToFire(final Vector2D newVelocity) {
        for (final BarrelTransformationObserver observer : transformationObservers) {
            observer.onBarrelTransformedToFire(this, newVelocity);
        }
    }

    /**
     * Checks if this barrel can transform into fire.
     *
     * @return true if transformation is enabled
     */
    public boolean canTransformToFire() {
        return this.canTransformToFire;
    }
}
