package it.unibo.coffebreak.model.impl.entities.collectible;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.character.Character;
import it.unibo.coffebreak.model.api.entities.collectible.Collectible;
import it.unibo.coffebreak.model.impl.common.Dimension2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.AbstractEntity;

/**
 * An abstract base class representing a collectible game entity.
 * Implements basic functionality for collectible objects, such as
 * collision handling, collection status, and point value.
 * 
 * @author Alessandro Rebosio
 */
public abstract class AbstractCollectible extends AbstractEntity implements Collectible {

    private boolean collected;
    private final int value;

    /**
     * Constructs a new {@code GameCollectible} with the specified position,
     * dimensions, and point value.
     *
     * @param position  the initial position of the collectible
     * @param dimension the size of the collectible
     * @param value     the number of points the collectible is worth
     */
    public AbstractCollectible(final Position2D position, final Dimension2D dimension, final int value) {
        super(position, dimension);
        this.collected = false;
        this.value = value;
    }

    /**
     * {@inheritDoc}
     * 
     * Ladders do not perform any specific action on collision.
     */
    @Override
    public void onCollision(final Entity other) {
    }

    /**
     * {@inheritDoc}
     * 
     * Marks this collectible as collected and applies its effect to the player.
     *
     * @param player the character who collected the item
     */
    @Override
    public void collect(final Character player) {
        if (!this.collected) {
            this.collected = true;
            this.applyEffect(player);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return true if this collectible has been collected, false otherwise
     */
    @Override
    public boolean isCollected() {
        return this.collected;
    }

    /**
     * {@inheritDoc}
     *
     * @return the number of points this collectible is worth
     */
    @Override
    public int getPointsValue() {
        return this.value;
    }

    /**
     * Applies the effect of the collectible to the given player.
     * This method must be implemented by subclasses to define
     * the specific behavior of the collectible.
     *
     * @param character the character who collected the item
     */
    protected void applyEffect(final Character character) {
        character.getScoreManager().earnPoints(this.value);
    }
}
