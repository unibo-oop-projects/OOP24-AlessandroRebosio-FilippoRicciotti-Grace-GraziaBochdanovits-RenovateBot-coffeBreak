package it.unibo.coffebreak.model.impl.physics;

import it.unibo.coffebreak.model.api.physics.Physics;

public record GamePhysics(float acceleration, float maxSpeed, float deceleration, float gravity) implements Physics {

}
