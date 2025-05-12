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
     * Constructs a new {@code GameLadder} with the specified position, dimension,
     * and climbable state.
     *
     * @param position  the position of the ladder in the game world
     * @param dimension the size of the ladder
     */
    public GameLadder(final Position2D position, final Dimension2D dimension) {
        super(position, dimension);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollision(final Entity other) {
        // TODO: set player canClimb true
    }

    /**
     * {@inheritDoc}
     * 
     * Ladders are static entities and do not need to update over time.
     */
    @Override
    public void update(final float deltaTime) {
        // TODO: remove this method after empty implementation in GameEntity
    }
}
