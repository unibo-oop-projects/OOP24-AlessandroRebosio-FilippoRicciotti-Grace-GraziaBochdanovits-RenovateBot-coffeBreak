package it.unibo.coffebreak.model.entity.impl;

import it.unibo.coffebreak.model.entity.api.Entity;

/*
 * Implementation of {@link Entity} interface that manage the entities.
 */
public abstract class GameEntity implements Entity {

    /*
     * The position and dimention of the entities in the game.
     */
    private Position position;
    private Dimension dimension;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final Position getPosition() {
        return this.position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Dimension getDimension() {
        return this.dimension;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void update(long deltaTime);

}
