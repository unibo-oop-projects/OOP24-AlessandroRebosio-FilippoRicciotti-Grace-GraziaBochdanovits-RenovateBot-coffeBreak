package it.unibo.coffebreak.impl.model.entities.npc.donkeykong;

import java.util.Optional;

import it.unibo.coffebreak.api.model.entities.enemy.barrel.Barrel;
import it.unibo.coffebreak.api.model.entities.npc.Antagonist;
import it.unibo.coffebreak.impl.common.Dimension;
import it.unibo.coffebreak.impl.common.Position;
import it.unibo.coffebreak.impl.model.entities.AbstractEntity;
import it.unibo.coffebreak.impl.model.entities.npc.AbstractNpc;

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
    private static final float BARREL_THROW_INTERVAL = 2.0f;

    private final boolean canThrowBarrel;
    private float lastThrowTime;

    private boolean isTrowing;

    /**
     * Constructs a new Donkey Kong entity with specified position, dimensions, and
     * barrel-throwing capability.
     *
     * @param position       the initial position of Donkey Kong (cannot be null)
     * @param dimension      the dimension of the pauline in the game world
     * @param canThrowBarrel true if Donkey Kong is allowed to throw barrels, false
     *                       otherwise
     * @throws NullPointerException     if position or dimension are null
     * @throws IllegalArgumentException if barrelThrowInterval is negative
     */
    public DonkeyKong(final Position position, final Dimension dimension, final boolean canThrowBarrel) {
        super(position, dimension);

        this.canThrowBarrel = canThrowBarrel;
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
        this.lastThrowTime += deltaTime;
        if (this.lastThrowTime >= BARREL_THROW_INTERVAL && this.canThrowBarrel) {
            lastThrowTime = 0;
            this.isTrowing = true;
            return Optional.empty(); //TODO: dovrà lanciare il barile obv
        }
        this.isTrowing = false;
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTrowing() {
        return this.isTrowing;
    }
}
