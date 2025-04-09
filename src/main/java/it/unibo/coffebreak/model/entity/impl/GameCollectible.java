package it.unibo.coffebreak.model.entity.impl;

import java.util.Objects;

import it.unibo.coffebreak.model.entity.ItemType;
import it.unibo.coffebreak.model.entity.api.Collectible;
import it.unibo.coffebreak.model.entity.api.Entity;
import it.unibo.coffebreak.model.utility.Dimension;
import it.unibo.coffebreak.model.utility.Position;

/**
 * Abstract base class for all collectible items in the game.
 * Provides common functionality for collection state management and value
 * retrieval.
 * 
 * @author Alessandro Rebosio
 */
public abstract class GameCollectible extends GameEntity implements Collectible {

    private final ItemType type;
    private boolean collected;

    /**
     * Constructs a new GameCollectible with specified position, dimensions and
     * type.
     * 
     * @param position  the initial position of the collectible
     * @param dimension the physical dimensions of the collectible
     * @param type      the type of collectible (determines base value and behavior)
     * @throws IllegalArgumentException if any parameter is null
     */
    public GameCollectible(final Position position, final Dimension dimension, final ItemType type) {
        super(position, dimension);
        this.type = Objects.requireNonNull(type, "ItemType cannot be null");
        this.collected = false;
    }

    /**
     * {@inheritDoc}
     * 
     * @return the base value associated with this collectible's type
     */
    @Override
    public int getValue() {
        return this.type.getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCollected() {
        return this.collected;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Marks the collectible as collected. Subsequent calls will have no effect.
     * Subclasses should override to implement specific collection behavior but
     * must call {@code super.collect()} to maintain proper state.
     * </p>
     */
    @Override
    public void collect(final Entity collector) {
        if (!this.collected) {
            this.collected = true;
            this.onCollected(collector);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Resets the collectible to its uncollected state, making it available
     * for collection again.
     * </p>
     */
    @Override
    public void reset() {
        this.collected = false;
    }

    /**
     * Gets the type of this collectible.
     * 
     * @return the ItemType of this collectible
     */
    public ItemType getType() {
        return this.type;
    }

    /**
     * Template method for custom collection effects.
     * Called automatically when the item is collected.
     * 
     * @param collector the entity that collected this item
     */
    protected abstract void onCollected(Entity collector);
}
