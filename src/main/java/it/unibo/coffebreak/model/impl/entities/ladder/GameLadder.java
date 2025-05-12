package it.unibo.coffebreak.model.impl.entities.ladder;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.ladder.Ladder;
import it.unibo.coffebreak.model.impl.entities.GameEntity;
import it.unibo.coffebreak.model.impl.util.Dimension2D;
import it.unibo.coffebreak.model.impl.util.Position2D;

/**
 * Concrete implementation of a {@link Ladder} entity.
 * Represents a ladder in the game world that may or may not be climbable
 * depending on the game state (e.g., blocked by an enemy).
 * Inherits position and dimension properties from {@link GameEntity}.
 * 
 * @author Alessandro Rebosio
 */
public class GameLadder extends GameEntity implements Ladder {

    /**
     * Whether the ladder is currently climbable.
     */
    private final boolean climb;

    /**
     * Constructs a new {@code GameLadder} with the specified position, dimension,
     * and climbable state.
     *
     * @param position  the position of the ladder in the game world
     * @param dimension the size of the ladder
     * @param climb     whether the ladder is currently climbable
     */
    public GameLadder(final Position2D position, final Dimension2D dimension, final boolean climb) {
        super(position, dimension);
        this.climb = climb;
    }

    /**
     * {@inheritDoc}
     * 
     * Ladders do not perform any specific action on collision.
     */
    @Override
    public void onCollision(final Entity other) {
        // No behavior on collision
    }

    /**
     * {@inheritDoc}
     * 
     * Ladders are static entities and do not need to update over time.
     */
    @Override
    public void update(final float deltaTime) {
        // No update needed
    }

    /**
     * {@inheritDoc}
     * 
     * @return true if the ladder can be climbed; false otherwise
     */
    @Override
    public boolean isClimbable() {
        return this.climb;
    }
}
