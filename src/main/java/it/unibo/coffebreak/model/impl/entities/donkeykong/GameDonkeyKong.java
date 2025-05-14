package it.unibo.coffebreak.model.impl.entities.donkeykong;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.donkeykong.DonkeyKong;
import it.unibo.coffebreak.model.impl.common.Dimension2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.AbstractEntity;

/**
 * Implementation of the Donkey Kong enemy character.
 * <p>
 * This class represents the main antagonist in the game that throws barrels
 * at regular intervals. The character follows the classic Donkey Kong behavior
 * from the arcade game.
 * </p>
 * 
 * @see DonkeyKong
 * @see AbstractEntity
 */
public class GameDonkeyKong extends AbstractEntity implements DonkeyKong {

    /**
     * The fixed interval between barrel throws in milliseconds.
     */
    private static final long BARREL_THROW_INTERVAL = 3000;

    /**
     * The timestamp of the last barrel throw.
     */
    private long lastThrowTime;

    /**
     * Constructs a new Donkey Kong entity with specified position and dimensions.
     * 
     * @param position the initial position of Donkey Kong (cannot be null)
     * @param dimension the physical dimensions of the character (cannot be null)
     * @throws NullPointerException if position or dimension are null
     */
    public GameDonkeyKong(final Position2D position, final Dimension2D dimension) {
        super(position, dimension);
    }

    /**
     * Updates Donkey Kong's state based on game time.
     * <p>
     * This implementation handles the automatic barrel throwing at fixed intervals.
     * </p>
     * 
     * @param deltaTime the time elapsed since last update in milliseconds
     */
    @Override
    public void update(final float deltaTime) {
        final long currentTime = System.currentTimeMillis();
        if (currentTime - lastThrowTime >= BARREL_THROW_INTERVAL) {
            throwBarrel();
            lastThrowTime = currentTime;
        }
    }

    /**
     * Performs the barrel throwing action.
     * <p>
     * This method should create a new barrel entity and add it to the game world.
     * </p>
     */
    @Override
    public void throwBarrel() {
        // TODO: Create barrel instance using a factory (?)
        // TODO: Add barrel to game world through observer pattern (?)
    }

    /**
     * Handles collision with other game entities.
     * <p>
     * Current implementation does nothing. Override to implement specific
     * collision behavior (e.g., reacting to player attacks).
     * </p>
     * 
     * @param other the entity that collided with Donkey Kong
     */
    @Override
    public void onCollision(final Entity other) {
        // Intentionally left blank - implement collision logic in subclasses
    }
}
