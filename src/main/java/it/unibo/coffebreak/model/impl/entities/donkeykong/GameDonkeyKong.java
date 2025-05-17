package it.unibo.coffebreak.model.impl.entities.donkeykong;

import java.util.Optional;

import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.donkeykong.DonkeyKong;
import it.unibo.coffebreak.model.api.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.model.api.entities.enemy.barrel.BarrelFactory;
import it.unibo.coffebreak.model.impl.common.Dimension2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.AbstractEntity;
import it.unibo.coffebreak.model.impl.entities.enemy.barrel.GameBarrelFactory;

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
     * The factory used to create new barrel instances.
     */
    private final BarrelFactory barrelFactory;

    /**
     * Constructs a new Donkey Kong entity with specified position and dimensions.
     * 
     * @param position the initial position of Donkey Kong (cannot be null)
     * @param dimension the physical dimensions of the character (cannot be null)
     * @throws NullPointerException if position or dimension are null
     */
    public GameDonkeyKong(final Position2D position, final Dimension2D dimension) {
        super(position, dimension);
        this.barrelFactory = new GameBarrelFactory();
    }

    /**
     * Updates Donkey Kong's state based on game time.
     * 
     * @param deltaTime the time elapsed since last update in milliseconds
     */
    @Override
    public void update(final float deltaTime) {
        // Intentionally empty
    }

     /**
     * Creates and throws a new barrel from Donkey Kong's position.
     * <p>
     * Implementation details:
     * <ul>
     *   <li>Creates a basic barrel (non-fire variant)</li>
     *   <li>Uses Donkey Kong's current position as starting point</li>
     *   <li>Barrel velocity is set by the {@link GameBarrelFactory}</li>
     * </ul>
     * 
     * @return the newly created {@link Barrel} instance
     */
    @Override
    public Barrel throwBarrel() {
        //TODO: calcola posizione che non sia proprio quella di DK
        return barrelFactory.createBarrel(getPosition(), false);
    }

    /**
     * Attempts to throw a barrel if the throw interval has elapsed.
     * <p>
     * This method checks if {@link #BARREL_THROW_INTERVAL} has passed since the last throw.
     * If so, it updates the throw timestamp and returns a new barrel wrapped in an Optional.
     * Otherwise returns an empty Optional.
     * 
     * @return {@link Optional} containing a new {@link Barrel} if thrown, otherwise empty
     */
    @Override
    public Optional<Barrel> tryThrowBarrel() {
        final long currentTime = System.currentTimeMillis();
        if (currentTime - lastThrowTime >= BARREL_THROW_INTERVAL) {
            lastThrowTime = currentTime;
            return Optional.of(throwBarrel());
        }
        return Optional.empty();
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
