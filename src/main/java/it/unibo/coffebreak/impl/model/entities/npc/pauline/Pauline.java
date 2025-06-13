package it.unibo.coffebreak.impl.model.entities.npc.pauline;

import it.unibo.coffebreak.api.model.entities.npc.Princess;
import it.unibo.coffebreak.impl.common.Dimension;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.npc.AbstractNpc;

/**
 * Concrete implementation of the {@link Princess} interface.
 * Represents a princess entity in the game that can be rescued by the player.
 * Extends {@link GameEntity} to inherit common game entity functionality.
 * 
 * @author Grazia Bochdanovits de kavna
 */
public class Pauline extends AbstractNpc implements Princess {

    private boolean rescued;

    /**
     * Constructs a new Princess entity with the specified position and dimensions.
     * The princess is initially not rescued.
     *
     * @param position  the initial position of the princess in 2D space (cannot be
     *                  null)
     * @param dimension the dimension of the pauline in the game world
     * @throws NullPointerException if either position or dimension are null
     */
    public Pauline(final Position position, final Dimension dimension) {
        super(position, dimension);
    }

    /**
     * {@inheritDoc}
     * Changes the state of the princess to "rescued".
     */
    @Override
    public void rescue() {
        this.rescued = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRescued() {
        return this.rescued;
    }
}
