package it.unibo.coffebreak.model.impl.entities.ladder;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.ladder.Ladder;
import it.unibo.coffebreak.model.impl.entities.GameEntity;
import it.unibo.coffebreak.model.impl.util.Dimension2D;
import it.unibo.coffebreak.model.impl.util.Position2D;

public class GameLadder extends GameEntity implements Ladder {

    private final boolean climb;

    public GameLadder(final Position2D position, final Dimension2D dimension, final boolean climb) {
        super(position, dimension);
        this.climb = climb;
    }

    @Override
    public void onCollision(final Entity other) {
        // No behavior on collision
    }

    @Override
    public void update(final float deltaTime) {
    }

    @Override
    public boolean isClimbable() {
        return this.climb;
    }
}
