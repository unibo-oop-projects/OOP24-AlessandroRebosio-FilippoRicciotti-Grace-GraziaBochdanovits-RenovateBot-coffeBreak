package it.unibo.coffebreak.impl.model.entities.structure.ladder;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.api.model.entities.structure.Ladder;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;

/**
 * An abstract base implementation for ladder entities within the game.
 * A ladder allows certain entities (e.g., the player or enemies) to move
 * vertically
 * between platforms. This class provides the basic positional and dimensional
 * properties of a ladder, while requiring subclasses to define specific
 * collision behavior.
 * 
 * This class extends {@link AbstractEntity} and implements the {@link Ladder}
 * interface.
 * 
 * @author Alessandro Rebosio
 */
public abstract class AbstractLadder extends AbstractEntity implements Ladder {

    /**
     * Constructs a new AbstractLadder with the given position and dimensions.
     * 
     * @param position the top-left position of the ladder in the game world
     */
    public AbstractLadder(final Position position) {
        super(position);
    }

    /**
     * Handles collision logic when another entity interacts with this ladder.
     * Currently, this method has no implemented logic.
     * 
     * @param other the entity that collided with the ladder
     */
    @Override
    public void onCollision(final Entity other) {
        // Default empty implementation
    }
}
