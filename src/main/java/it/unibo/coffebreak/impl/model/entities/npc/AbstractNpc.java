package it.unibo.coffebreak.impl.model.entities.npc;

import it.unibo.coffebreak.api.model.entities.Entity;
import it.unibo.coffebreak.impl.common.BoundigBox;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;

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
     * @param position  the initial position of the NPC in 2D space
     * @param dimension the dimension of the npc in the game world
     * 
     */
    public AbstractNpc(final Position position, final BoundigBox dimension) {
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
}
