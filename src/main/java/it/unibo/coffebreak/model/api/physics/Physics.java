package it.unibo.coffebreak.model.api.physics;

import it.unibo.coffebreak.model.api.entities.Entity;

public interface Physics {

    enum Direction {
        LEFT,
        RIGHT,
        NONE
    }

    void updateMovement(Entity entity, float deltaTime, Direction direction);

}
