package it.unibo.coffebreak.model.api.entity.enemy;

import it.unibo.coffebreak.model.impl.entity.enemy.Barrel;
import it.unibo.coffebreak.model.impl.util.Vector2D;

/**
 * An observer interface for receiving notifications when a barrel undergoes
 * a transformation into a fire state in the game. Implementers can react to
 * this transformation event with custom behavior.
 */
public interface BarrelTransformationObserver {

    /**
     * Called when a barrel is transformed into a fire state.
     *
     * @param barrel the {@link Barrel} that has been transformed
     * @param newVelocity the new {@link Vector2D} velocity of the transformed barrel.
     *                   This represents both direction and speed of movement.
     *                   May be used to determine the fire's trajectory.
     */
    void onBarrelTransformedToFire(Barrel barrel, Vector2D newVelocity);
}
