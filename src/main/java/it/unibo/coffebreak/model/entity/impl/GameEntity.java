package it.unibo.coffebreak.model.entity.impl;

import it.unibo.coffebreak.model.entity.api.Entity;
import it.unibo.coffebreak.model.utility.Dimension;
import it.unibo.coffebreak.model.utility.Position;

/**
 * Represents a base class for game entities that have a position and a dimension.
 * This class implements the {@link Entity} interface and provides common functionality
 * for managing the position and dimension of entities in the game.
 */
public abstract class GameEntity implements Entity {

    /*
     * The position and dimention of the entities in the game.
     */
    private final Position position;
    private final Dimension dimension;

     /**
     * Constructs a new {@link GameEntity} with the specified position and dimension.
     *
     * @param p the position of the entity.
     * @param d the dimension of the entity.
     */
    public GameEntity(final Position p, final Dimension d) {
        this.position = p;
        this.dimension = d;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Position getPosition() {
        return new Position(this.position.getX(), this.position.getY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Dimension getDimension() {
        return this.dimension;
    }
}
