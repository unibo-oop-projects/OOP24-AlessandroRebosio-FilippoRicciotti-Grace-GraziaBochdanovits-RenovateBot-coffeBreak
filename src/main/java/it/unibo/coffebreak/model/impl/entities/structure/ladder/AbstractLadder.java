package it.unibo.coffebreak.model.impl.entities.structure.ladder;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.structure.Ladder;
import it.unibo.coffebreak.model.impl.common.BoundingBox2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.AbstractEntity;

/**
 * An abstract base implementation for ladder entities within the game.
 * A ladder allows certain entities (e.g., the player or enemies) to move vertically 
 * between platforms. This class provides the basic positional and dimensional 
 * properties of a ladder, while requiring subclasses to define specific collision behavior.
 * 
 * This class extends {@link AbstractEntity} and implements the {@link Ladder} interface.
 */
public abstract class AbstractLadder extends AbstractEntity implements Ladder {

    /**
     * Constructs a new AbstractLadder with the given position and dimensions.
     * 
     * @param position  the top-left position of the ladder in the game world
     * @param dimension the width and height of the ladder
     */
    public AbstractLadder(final Position2D position, final BoundingBox2D dimension) {
        super(position, dimension);
    }

    /**
     * Handles the collision behavior when another entity interacts with the ladder.
     * Implementing classes must define how different entities respond to colliding
     * with the ladder (e.g., allowing climbing or triggering specific interactions).
     *
     * @param other the entity that has collided with the ladder
     */
    @Override
    public abstract void onCollision(Entity other);
}
