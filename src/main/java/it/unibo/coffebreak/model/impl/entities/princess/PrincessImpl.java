package it.unibo.coffebreak.model.impl.entities.princess;

import java.util.Objects;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.princess.Princess;
import it.unibo.coffebreak.model.impl.common.Dimension2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.GameEntity;

/**
 * Concrete implementation of the {@link Princess} interface.
 * Represents a princess entity in the game that can be rescued by the player.
 * Extends {@link GameEntity} to inherit common game entity functionality.
 * 
 * @author Grazia Bochdanovits de kavna
 */
public class PrincessImpl extends GameEntity implements Princess {

    private boolean rescued;

    /**
     * Constructs a new Princess entity with the specified position and dimensions.
     * The princess is initially not rescued.
     *
     * @param position the initial position of the princess in 2D space (cannot be null)
     * @param dimension the dimensions of the princess entity (cannot be null)
     * @throws NullPointerException if either position or dimension are null
     */
    public PrincessImpl(final Position2D position, final Dimension2D dimension) {
        super(Objects.requireNonNull(position), Objects.requireNonNull(dimension));
        this.rescued = false;
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

    /**
     * {@inheritDoc}
     * Currently this implementation does nothing when a collision occurs.
     *
     * @param other the entity that collided with this princess
     */
    @Override
    public void onCollision(final Entity other) {
        // Intentionally left blank
    }
}
