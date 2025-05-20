package it.unibo.coffebreak.model.impl.entities.npc.donkeykong;

import java.util.Optional;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.entities.Entity;
import it.unibo.coffebreak.model.api.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.model.api.entities.enemy.barrel.BarrelFactory;
import it.unibo.coffebreak.model.api.entities.npc.Antagonist;
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
 * @see Antagonist
 * @see AbstractEntity
 * @author Grazia Bochdanovits de Kavna
 */
public class DonkeyKong extends AbstractEntity implements Antagonist {

    /**
     * The interval between barrel throws in milliseconds.
     */
    private static final long BARREL_THROW_INTERVAL = 3000;

    private long lastThrowTime;
    private final BarrelFactory barrelFactory;

    /**
     * Constructs a new Donkey Kong entity with specified position and dimensions.
     * 
     * @param position  the initial position of Donkey Kong (cannot be null)
     * @param dimension the physical dimensions of the character (cannot be null)
     * @throws NullPointerException     if position or dimension are null
     * @throws IllegalArgumentException if barrelThrowInterval is negative
     */
    public DonkeyKong(final Position2D position, final Dimension2D dimension) {
        super(position, dimension);
        this.barrelFactory = new GameBarrelFactory();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Current implementation does nothing as Donkey Kong doesn't require
     * periodic updates beyond barrel throwing.
     * </p>
     */
    @Override
    public void update(final float deltaTime) {
        // Intentionally empty
    }

    /**
     * Creates and throws a new barrel from Donkey Kong's position.
     * <p>
     * The barrel's starting position is slightly offset from
     * Donkey Kong's current position.
     * </p>
     * 
     * @return the newly created {@link Barrel} instance
     */
    @Override
    public Barrel throwBarrel() {
        return barrelFactory.createBarrel(getPosition(), Command.MOVE_RIGHT);
    }

    /**
     * Attempts to throw a barrel if the throw interval has elapsed.
     * <p>
     * This method checks if {@link #BARREL_THROW_INTERVAL} has passed since the
     * last throw.
     * If so, it updates the throw timestamp and returns a new barrel wrapped in an
     * Optional.
     * Otherwise returns an empty Optional.
     * 
     * @return {@link Optional} containing a new {@link Barrel} if thrown, otherwise
     *         empty
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
     * {@inheritDoc}
     */
    @Override
    public void onCollision(final Entity other) {
        // Intentionally left blank
    }
}
