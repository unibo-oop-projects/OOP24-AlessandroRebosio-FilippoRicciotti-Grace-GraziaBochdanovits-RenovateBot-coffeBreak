package it.unibo.coffebreak.model.impl.physics;

import it.unibo.coffebreak.model.api.physics.Physics;

/**
 * Implementation of {@link Physics} that handles 2D game physics calculations.
 * This record provides concrete physics computations for game entities
 * including:
 * <ul>
 * <li>Movement with acceleration and friction</li>
 * <li>Gravity application</li>
 * </ul>
 *
 * <p>
 * The physics system uses frame-rate independent calculations based on delta
 * time.
 * 
 * @author Alessandro Rebosio
 */
public record GamePhysics() implements Physics {
    // TODO: Implement physics methods
}
