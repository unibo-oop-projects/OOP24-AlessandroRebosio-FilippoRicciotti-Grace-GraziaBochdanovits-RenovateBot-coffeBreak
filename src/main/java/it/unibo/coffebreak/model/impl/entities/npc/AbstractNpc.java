package it.unibo.coffebreak.model.impl.entities.npc;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.impl.common.BoundingBox2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.AbstractEntity;

/**
 * Abstract base class for all Non-Player Characters (NPCs) in the game.
 * Provides common functionality and default behavior for NPC entities.
 * Concrete NPC implementations should extend this class.
 * 
 * @author Grazia Bochdanovit de Kavna
 */
public abstract class AbstractNpc extends AbstractEntity {

    /**
     * Constructs a new AbstractNpc with the specified position and dimensions.
     *
     * @param position the initial position of the NPC in 2D space
     * @param dimension the dimensions (width and height) of the NPC
     */
    public AbstractNpc(final Position2D position, final BoundingBox2D dimension) {
        super(position, dimension);
    }

    /**
     * {@inheritDoc}
     * Default empty implementation. Subclasses should override this method
     * to define specific collision behavior for the NPC.
     *
     * @param other the entity that collided with this NPC
     */
    @Override
    public void onCollision(final Entity other) {
        // Default empty implementation
    }

    /**
     * {@inheritDoc}
     * Default empty implementation. Subclasses should override this method
     * to implement NPC-specific update logic.
     *
     * @param deltaTime the time elapsed since the last update in seconds
     */
    @Override
    public void update(final float deltaTime) {
        // Default empty implementation
    }
}
