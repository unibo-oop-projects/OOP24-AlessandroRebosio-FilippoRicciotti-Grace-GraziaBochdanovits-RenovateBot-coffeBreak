package it.unibo.coffebreak.model.impl.entities.npc.donkeykong;

import java.util.Optional;

import it.unibo.coffebreak.controller.api.command.Command;
import it.unibo.coffebreak.model.api.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.model.api.entities.enemy.barrel.BarrelFactory;
import it.unibo.coffebreak.model.api.entities.npc.Antagonist;
import it.unibo.coffebreak.model.impl.common.BoundingBox2D;
import it.unibo.coffebreak.model.impl.common.Position2D;
import it.unibo.coffebreak.model.impl.entities.AbstractEntity;
import it.unibo.coffebreak.model.impl.entities.enemy.barrel.GameBarrelFactory;
import it.unibo.coffebreak.model.impl.entities.npc.AbstractNpc;

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
public class DonkeyKong extends AbstractNpc implements Antagonist {

    /**
     * The interval between barrel throws in milliseconds.
     */
    private static final long BARREL_THROW_INTERVAL = 3000;

    private float lastThrowTime;
    private final BarrelFactory barrelFactory;

    /**
     * Constructs a new Donkey Kong entity with specified position and dimensions.
     * 
     * @param position  the initial position of Donkey Kong (cannot be null)
     * @param dimension the physical dimensions of the character (cannot be null)
     * @throws NullPointerException     if position or dimension are null
     * @throws IllegalArgumentException if barrelThrowInterval is negative
     */
    public DonkeyKong(final Position2D position, final BoundingBox2D dimension) {
        super(position, dimension);
        this.barrelFactory = new GameBarrelFactory();
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
     * {@inheritDoc}
     * 
     * @param deltaTime the time elapsed
     * @return {@link Optional} containing a new {@link Barrel} if thrown, otherwise
     *         empty
     */
    @Override
    public Optional<Barrel> tryThrowBarrel(final float deltaTime) {
        if (deltaTime - lastThrowTime >= BARREL_THROW_INTERVAL) {
            lastThrowTime = deltaTime;
            return Optional.of(throwBarrel());
        }
        return Optional.empty();
    }
}
