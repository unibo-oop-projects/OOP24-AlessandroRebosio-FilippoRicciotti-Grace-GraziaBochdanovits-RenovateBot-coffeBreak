package it.unibo.coffebreak.model.impl.entity.item;

import it.unibo.coffebreak.model.api.entity.Entity;
import it.unibo.coffebreak.model.api.entity.item.Collectible;
import it.unibo.coffebreak.model.api.entity.item.CollectibleFactory;
import it.unibo.coffebreak.model.impl.entity.MarioState;
import it.unibo.coffebreak.model.impl.entity.mario.Mario;
import it.unibo.coffebreak.model.impl.utility.Dimension;
import it.unibo.coffebreak.model.impl.utility.Position;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Concrete implementation of {@link CollectibleFactory} that produces
 * various types of {@link Collectible} items for the game.
 * <p>
 * Each method returns a new instance of a {@link GameCollectible}
 * configured with the appropriate {@link ItemType}, position, and dimension.
 * Specific behavior upon collection is defined via a {@link Consumer} passed
 * to the creation method, allowing custom effects per item type.
 * </p>
 *
 * @see GameCollectible
 * @see Collectible
 * @see ItemType
 * @see Entity
 * 
 * @author Alessandro Rebosio
 */
public class GameCollectibleFactory implements CollectibleFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Collectible createHammer(final Position position, final Dimension dimension) {
        return buildCollectible(ItemType.HAMMER, position, dimension, collector -> {
            if (collector instanceof Mario) {
                if (((Mario) collector).isAlive()) {
                    ((Mario) collector).changeState(MarioState.WITH_HAMMER);
                }
            }
            // activate power-up)
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collectible createCoin(final Position position, final Dimension dimension) {
        return buildCollectible(ItemType.COIN, position, dimension, collector -> {
            // Points are already handled via getValue; no extra effect
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collectible createBag(final Position position, final Dimension dimension) {
        return buildCollectible(ItemType.BAG, position, dimension, collector -> {
            // Additional bag-specific logic can go here
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collectible createHat(final Position position, final Dimension dimension) {
        return buildCollectible(ItemType.HAT, position, dimension, collector -> {
            // Add any hat-specific effect logic here
        });
    }

    /**
     * Builds a generic collectible with specified type, position, dimension, and
     * collection behavior.
     *
     * @param type        the type of collectible
     * @param position    the spawn position
     * @param dimension   the size of the collectible
     * @param onCollected the action to perform when collected
     * @return a configured {@link Collectible} instance
     * @throws NullPointerException if any parameter is null
     */
    private Collectible buildCollectible(final ItemType type, final Position position, final Dimension dimension,
            final Consumer<Entity> onCollected) {
        Objects.requireNonNull(type, "ItemType must not be null");
        Objects.requireNonNull(position, "Position must not be null");
        Objects.requireNonNull(dimension, "Dimension must not be null");
        Objects.requireNonNull(onCollected, "Collection action must not be null");

        return new GameCollectible(position, dimension, type) {

            @Override
            public void update(final long deltaTime) {
                // Optional per-frame behavior for the collectible, currently unused
            }

            @Override
            protected void onCollected(final Entity collector) {
                onCollected.accept(collector);
            }
        };
    }
}
